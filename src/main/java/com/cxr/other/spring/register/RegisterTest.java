package com.cxr.other.spring.register;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RegisterTest {
    public static void main(String[] args) {

//        beanDefinitionRegistryPostProcessorDemo();
        applicationContextDemo();
//        beanFactoryPostProcessorDemo();
    }

    /**
     * 通过beanFactory的后置处理器
     */
    private static void beanFactoryPostProcessorDemo() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanFactoryPostProcessorDemo.class);
        OOO beanFactoryPostProcessorDemo = applicationContext.getBean("BeanFactoryPostProcessorDemo", OOO.class);
    }

    /**
     * 通过BeanDefinitionRegistryPostProcessor接口 其实这个接口继承了beanFactoryPostProcessor
     */
    private static void beanDefinitionRegistryPostProcessorDemo() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(beanRegisterConfig.class);
        OOO bean = (OOO) applicationContext.getBean("hello");
    }

    /**
     *
     */
    private static void applicationContextDemo() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(beanRegisterConfig.class);
        ApplicationContextDemo bean = applicationContext.getBean(ApplicationContextDemo.class);
        bean.register("applicationContextDemoBean", OOO.class);

        OOO bean1 = (OOO) applicationContext.getBean("applicationContextDemoBean");
    }
}
