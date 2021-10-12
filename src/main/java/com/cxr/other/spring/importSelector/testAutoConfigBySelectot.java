package com.cxr.other.spring.importSelector;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: CiXingrui
 * @Create: 2021/8/6 10:30 下午
 */
public class testAutoConfigBySelectot {
    public static void main(String[] args) {
        /**
         * 现象：
         * 明明UserService没有用注解配置进IOC容器但是能get到
         * 说明是我们的selectImport生效了
         */
        ApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(beanConfig.class);
        UserService bean = annotationConfigApplicationContext.getBean(UserService.class);
        System.out.println(bean);
    }
}
