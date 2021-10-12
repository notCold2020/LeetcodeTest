package com.cxr.other.spring.beanPostProcessorDemo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: CiXingrui
 * @Create: 2021/8/8 7:16 下午
 */
public class TestBeanPostProcessor {
    public static void main(String[] args) {
        ApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(BaseServiceImpl.class);
        BaseService bean = annotationConfigApplicationContext.getBean(BaseService.class);
        String s = bean.doSomething();
    }

}
