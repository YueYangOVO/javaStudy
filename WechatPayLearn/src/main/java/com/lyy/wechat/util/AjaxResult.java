package com.lyy.wechat.util;

import java.util.HashMap;
import java.util.Objects;


public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE = "code";
    /**
     * 返回内容
     */
    public static final String MESSAGE = "message";
    /**
     * 数据对像
     */
    public static final String DATA = "data";

    /**
     * 无参构造
     */
    public AjaxResult() {
    }

    /**
     * 二参构造
     */
    public AjaxResult(String code, String message) {
        super.put(CODE, code);
        super.put(MESSAGE, message);
    }

    /**
     * 三参构造
     */
    public AjaxResult(String code, String message, Object data) {
        super.put(CODE, code);
        super.put(MESSAGE, message);
        super.put(DATA, data);
    }

    // ---------------------------------------------------------

    // 成功 - 标准版
    public static AjaxResult success(String message, Object data) {
        return new AjaxResult("200", message, data);
    }

    //成功 - message 简化版
    public static AjaxResult success(String message) {
        return new AjaxResult("200", message, null);
    }

    //成功 - data 简化版
    public static AjaxResult success(Object data) {
        return new AjaxResult("200", "操作成功", data);
    }

    //成功 - 无参 简化版
    public static AjaxResult success() {
        return new AjaxResult("200", "操作成功", null);
    }

    // ---------------------------------------------------------

    // 警告 - 标准版
    public static AjaxResult warn(String msg, Object data) {
        return new AjaxResult("601", msg, data);
    }

    // 警告 - message 简化版
    public static AjaxResult warn(String msg) {
        return new AjaxResult("601", msg, null);
    }

    // 警告 - data 简化版
    public static AjaxResult warn(Object data) {
        return new AjaxResult("601", "操作警告", data);
    }

    // 警告 - 无参 简化版
    public static AjaxResult warn() {
        return new AjaxResult("601", "操作警告", null);
    }

    // ---------------------------------------------------------

    // 错误 - 标准版
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult("500", msg, data);
    }

    // 错误 - message 简化版
    public static AjaxResult error(String msg) {
        return new AjaxResult("500", msg, null);
    }

    // 错误 - data 简化版
    public static AjaxResult error(Object data) {
        return new AjaxResult("500", "操作错误", data);
    }

    // 错误 - 无参 简化版
    public static AjaxResult error() {
        return new AjaxResult("500", "操作错误", null);
    }
}
