package com.cxr.other.spring.beanFactoryPostProcessorDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: CiXingrui
 * @Create: 2021/8/17 10:23 下午
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TForBeanFactoryPostProcessor.class);
//        BeanFactoryPostProcessorTest bean = annotationConfigApplicationContext.getBean(BeanFactoryPostProcessorTest.class);
        Demo bean1 = (Demo) annotationConfigApplicationContext.getBean(Demo.class);
    }
}
