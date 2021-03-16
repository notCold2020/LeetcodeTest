package com.cxr.other.redisTest.idempotentByToken;

import java.lang.annotation.*;

/**
 * 防重复提交的注解
 *          放在Controller类：表示当前类的所有接口都是幂等性
 *          放在方法上：表示当前方法是幂等性
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatLimiter {
}

