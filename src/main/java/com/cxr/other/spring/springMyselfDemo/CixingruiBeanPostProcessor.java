package com.cxr.other.spring.springMyselfDemo;

public interface CixingruiBeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName);

    Object postProcessAfterInitialization(Object bean, String beanName);

}