package com.cxr.other.spring.beanLifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessorImpl implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
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
