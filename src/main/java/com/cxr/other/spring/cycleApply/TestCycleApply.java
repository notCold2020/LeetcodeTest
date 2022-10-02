package com.cxr.other.spring.cycleApply;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Date 2022/5/18 3:19 下午
 * @Created by CiXingrui
 */
public class TestCycleApply {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(OrderService.class);
        UserService bean = annotationConfigApplicationContext.getBean(UserService.class);
        OrderService bean2 = annotationConfigApplicationContext.getBean(OrderService.class);
    }

}
