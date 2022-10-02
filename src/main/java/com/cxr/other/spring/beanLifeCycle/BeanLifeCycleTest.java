package com.cxr.other.spring.beanLifeCycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanLifeCycleTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(Appconfig.class);
    }

}
