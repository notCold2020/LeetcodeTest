package com.cxr.other.spring.springMyselfDemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Date 2022/5/21 4:03 下午
 * @Created by CiXingrui
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CixingruiScope {
    String value();
}
