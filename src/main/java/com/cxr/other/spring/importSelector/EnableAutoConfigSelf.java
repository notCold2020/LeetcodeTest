package com.cxr.other.spring.importSelector;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: CiXingrui
 * @Create: 2021/8/6 10:24 下午
 */
@Import(HelloImportSelector.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)//这个不加不行
@Documented
public @interface EnableAutoConfigSelf {
    String reference() default "";
}
