package com.cxr.other.spring.beanLifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessorImpl implements BeanPostProcessor {

    @Override
    /**
     * 任何一个类在初始化前后都会执行下面的两个before after方法
     * 像我们这样直接实现BeanPostProcessor，会导致
     *         System.out.println("MyBeanPostProcessorImpl|postProcessBeforeInitialization:" + beanName);
     * 每个类都会打印一次
     *
     * 所以要单独判断 instance of
     */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessorImpl|postProcessBeforeInitialization:" + beanName);
        if (beanName.equals("dd")) {
            return bean;
        }
        System.out.println("5:"+bean.getClass().getSimpleName() + "对象" + beanName + "开始初始化--BeanPostProcessor");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("dd")) {
            return bean;
        }
        System.out.println("9:"+bean.getClass().getSimpleName() + "对象" + beanName + "初始化完成--BeanPostProcessor");
        return bean;
    }
}
