package com.cxr.other.spring.beanPostProcessorDemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/6/24 11:05 上午
 * @Created by CiXingrui
 */
@Component
public class BeanDefinePostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationListener<ContextRefreshedEvent> {

    private ConfigurableListableBeanFactory beanFactory;


    /**
     * invokeBeanFactoryPostProcessors里面执行的
     * 这个时候还没抽取bdMap的key为list循环进行getBean()
     *
     * 只是提供了一个操作bdMap的入口，在所有的bean[除了部分必须实例化的系统bean]实例化之前
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClassName("com.cxr.other.spring.beanPostProcessorDemo.User");
        registry.registerBeanDefinition("user123456", bd);
    }


    /**
     * 感觉和上面的也没啥区别
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition user123456 = beanFactory.getBeanDefinition("user123456");
        this.beanFactory = beanFactory;
    }


    /**
     * 这样就可以拿类了
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Object xx = beanFactory.getBean("xx");
    }
}
