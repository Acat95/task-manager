package com.example.taskmanager.advice;

import com.example.taskmanager.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    // 处理通用异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception e) {
        logger.error("系统异常:", e);
        return Result.error(500, "服务器内部错误: " + e.getMessage());
    }
    
    // 处理认证失败异常
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> handleBadCredentialsException(BadCredentialsException e) {
        logger.warn("认证失败:", e);
        return Result.error(401, "用户名或密码错误");
    }
    
    // 处理未认证异常
    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {
        logger.warn("未认证访问:", e);
        return Result.error(401, "请先登录");
    }
    
    // 处理权限不足异常
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<?> handleAccessDeniedException(AccessDeniedException e) {
        logger.warn("权限不足:", e);
        return Result.error(403, "权限不足，无法访问");
    }
    
    // 自定义业务异常处理（可根据需要添加）
    // @ExceptionHandler(BusinessException.class)
    // @ResponseBody
    // public Result<?> handleBusinessException(BusinessException e) {
    //     return Result.error(e.getCode(), e.getMessage());
    // }
}