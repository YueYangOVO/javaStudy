package com.lyy.eslearn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;


/**
 * @author YueYang
 * Created on 2025/11/3 19:14
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "goods")
@Setting(shards = 1, replicas = 1) //不指定 默认都是1
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id //标识主键
    private String id;


    /**
     * @Field是一个字段映射注解 name 映射es当中字段的名称 同名可以省略
     * type 映射字段的类型 text 会分词， keyword不会分词
     * analyzer指定分词器
     */
    @Field(name = "title", type = FieldType.Text, analyzer = "ik_smart")
    private String title;


    /**
     * index=false表示该字段不建立倒排索引
     */
    @Field(name = "image", type = FieldType.Keyword, index = false)
    private String image;


    @Field(name = "price", type = FieldType.Float)
    private Float price;

    /**
     * 库存
     */
    @Field(name = "stock", type = FieldType.Integer)
    private Integer stock;


    /**
     * FieldType.Nested:嵌套类型，自定义类型一般使用Nested，避免数据扁平化
     * FieldType.Object:对象类型
     */
    @Field(name = "attr", type = FieldType.Object)
    private Attr attr;


}
