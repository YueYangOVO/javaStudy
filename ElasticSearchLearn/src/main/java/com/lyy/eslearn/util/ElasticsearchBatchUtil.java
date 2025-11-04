package com.lyy.eslearn.util;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Elasticsearch 批量操作工具类
 * 用于处理内存受限的 Elasticsearch 服务器的批量操作
 */
@Component
public class ElasticsearchBatchUtil {

    /**
     * 安全的批量保存操作
     * 
     * @param elasticsearchTemplate Elasticsearch 操作模板
     * @param dataList 要保存的数据列表
     * @param batchSize 批次大小（建议1-10）
     * @param delayMs 批次间延迟时间（毫秒）
     * @param <T> 数据类型
     * @return 成功保存的数据条数
     */
    public <T> int safeBatchSave(ElasticsearchOperations elasticsearchTemplate, 
                                List<T> dataList, 
                                int batchSize, 
                                long delayMs) {
        if (dataList == null || dataList.isEmpty()) {
            return 0;
        }
        
        int totalCount = dataList.size();
        int successCount = 0;
        int retryCount = 0;
        final int maxRetries = 3;
        
        System.out.println("开始批量保存 " + totalCount + " 条数据，批次大小: " + batchSize);
        
        for (int i = 0; i < totalCount; i += batchSize) {
            int endIndex = Math.min(i + batchSize, totalCount);
            List<T> batch = dataList.subList(i, endIndex);
            int batchNumber = i / batchSize + 1;
            
            boolean batchSuccess = false;
            int currentRetry = 0;
            
            while (!batchSuccess && currentRetry <= maxRetries) {
                try {
                    // 添加延迟以减少服务器压力
                    if (i > 0 || currentRetry > 0) {
                        Thread.sleep(delayMs);
                    }
                    
                    elasticsearchTemplate.save(batch);
                    successCount += batch.size();
                    batchSuccess = true;
                    
                    System.out.println("批次 " + batchNumber + " 保存成功，包含 " + batch.size() + " 条数据");
                    
                } catch (Exception e) {
                    currentRetry++;
                    String errorMsg = e.getMessage();
                    
                    System.err.println("批次 " + batchNumber + " 保存失败 (尝试 " + currentRetry + "/" + (maxRetries + 1) + "): " + errorMsg);
                    
                    // 根据异常类型调整等待时间
                    long waitTime = delayMs;
                    if (errorMsg != null && errorMsg.contains("circuit_breaking_exception")) {
                        waitTime = 5000; // Circuit Breaker 异常等待5秒
                        System.out.println("检测到 Circuit Breaker 异常，等待 " + waitTime + "ms 后重试...");
                    } else if (errorMsg != null && errorMsg.contains("429")) {
                        waitTime = 3000; // 429 错误等待3秒
                        System.out.println("检测到 429 错误，等待 " + waitTime + "ms 后重试...");
                    }
                    
                    if (currentRetry <= maxRetries) {
                        try {
                            Thread.sleep(waitTime);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }
            
            if (!batchSuccess) {
                retryCount++;
                System.err.println("批次 " + batchNumber + " 最终保存失败，跳过该批次");
            }
        }
        
        System.out.println("批量保存完成！成功: " + successCount + "/" + totalCount + 
                          " 条数据，失败批次: " + retryCount);
        
        return successCount;
    }
    
    /**
     * 使用默认参数的安全批量保存
     * 
     * @param elasticsearchTemplate Elasticsearch 操作模板
     * @param dataList 要保存的数据列表
     * @param <T> 数据类型
     * @return 成功保存的数据条数
     */
    public <T> int safeBatchSave(ElasticsearchOperations elasticsearchTemplate, List<T> dataList) {
        return safeBatchSave(elasticsearchTemplate, dataList, 1, 1000);
    }
}