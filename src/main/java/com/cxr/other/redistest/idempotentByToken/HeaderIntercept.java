package com.cxr.other.redistest.idempotentByToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

/**
 * 描述:
 * 拦截请求头，用来对参数做校验
 *
 * @author XueGuCheng
 * @create 2021-02-28 19:59
 */

public class HeaderIntercept implements HandlerInterceptor {

    @Autowired
    RedisTokenService redisTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            //获取方法上的参数
            RepeatLimiter repeatLimiter = AnnotationUtils.findAnnotation(((HandlerMethod) handler).getMethod(), RepeatLimiter.class);
            if (Objects.isNull(repeatLimiter)) {
                //获取Controller类上的注解
                repeatLimiter = AnnotationUtils.findAnnotation(((HandlerMethod) handler).getBean().getClass(), RepeatLimiter.class);
            }

            //repeatLimiter不为空，即使用了@RepeatLimiter注解,需要进行拦截验证
            if (Objects.nonNull(repeatLimiter)) {
                //获取请求头携带的token
                //测试所用，故为方便，直接定义参数格式为： token：xxx
                String token = request.getHeader("token");
                System.out.println(token);
                //如果没有携带token，抛异常
                if (StringUtils.isEmpty(token)) {
                    throw new RuntimeException("重复提交");
                }
                //幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回提示
                Boolean flag = redisTokenService.deleteToken(token);
                if (Boolean.FALSE.equals(flag)) {
                    //重复提交
                    throw new RuntimeException("重复提交");
                }

            }

        }
        return true;
    }
}

