package com.example.taskmanager.common;

public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    
    // 构造方法私有，通过静态方法创建实例
    private Result() {}
    
    // 成功响应 - 带数据
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "success";
        result.data = data;
        return result;
    }
    
    // 成功响应 - 无数据
    public static <T> Result<T> success() {
        return success(null);
    }
    
    // 错误响应 - 自定义消息
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.code = 500;
        result.message = message;
        result.data = null;
        return result;
    }
    
    // 错误响应 - 自定义状态码和消息
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        result.data = null;
        return result;
    }
    
    // Getter和Setter
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}