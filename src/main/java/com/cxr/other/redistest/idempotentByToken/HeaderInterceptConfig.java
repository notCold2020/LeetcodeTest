package com.cxr.other.redistest.idempotentByToken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述:
 * 拦截规则
 *
 * @author XueGuCheng
 * @create 2021-02-28 22:05
 */
@Configuration
public class HeaderInterceptConfig implements WebMvcConfigurer {

    @Bean
    HeaderIntercept headerIntercept(){
        return new HeaderIntercept();
    }

    //配置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerIntercept())
                .addPathPatterns("/RepeatLimiter/add");
    }
}

