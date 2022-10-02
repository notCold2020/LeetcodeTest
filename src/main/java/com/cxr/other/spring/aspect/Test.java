package com.cxr.other.spring.aspect;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Date 2022/5/28 11:19 上午
 * @Created by CiXingrui
 */
public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AspectConfig.class);
        AspectTest bean = applicationContext.getBean(AspectTest.class);
//        bean.beforeTest();
    }

}
