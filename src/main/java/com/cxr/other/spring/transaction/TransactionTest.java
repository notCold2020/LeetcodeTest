package com.cxr.other.spring.transaction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/15 11:00 上午
 */

public class TransactionTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(TransactionDemo.class);

        TransactionDemo bean = annotationConfigApplicationContext.getBean(TransactionDemo.class);
        bean.testTransaction();

    }
}
