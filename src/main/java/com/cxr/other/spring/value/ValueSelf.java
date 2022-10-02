package com.cxr.other.spring.value;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)//这个不加不行
@Value("${testValue}")
public @interface ValueSelf {
    String reference() default "";
}