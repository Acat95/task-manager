package com.example.taskmanager.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求耗时统计拦截器
 * 记录每个API请求的处理时间
 */
public class RequestTimeInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestTimeInterceptor.class);
    private static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        startTimeThreadLocal.set(startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 此方法在处理器方法执行完毕后调用，视图渲染前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 记录请求结束时间并计算耗时
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTimeThreadLocal.get();
        startTimeThreadLocal.remove();

        // 打印请求方法、路径、耗时和状态码
        logger.info("{} {} - Status: {} - 耗时: {}ms", 
                request.getMethod(), 
                request.getRequestURI(), 
                response.getStatus(),
                duration);
        
        // 如果耗时超过500ms，打印警告日志
        if (duration > 500) {
            logger.warn("请求处理耗时较长: {} {} - {}ms", 
                    request.getMethod(), 
                    request.getRequestURI(), 
                    duration);
        }
    }
}