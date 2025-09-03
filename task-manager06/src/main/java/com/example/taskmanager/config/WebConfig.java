package com.example.taskmanager.config;

import com.example.taskmanager.interceptor.RequestTimeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，用于注册拦截器等Web相关配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册请求时间拦截器，排除Swagger相关路径
        registry.addInterceptor(new RequestTimeInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html");
    }
}