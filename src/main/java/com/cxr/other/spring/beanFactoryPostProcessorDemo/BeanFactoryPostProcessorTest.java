package com.cxr.other.spring.beanFactoryPostProcessorDemo;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @Author: CiXingrui
 * @Create: 2021/8/16 10:25 下午
 */
@Data
@Component
public class BeanFactoryPostProcessorTest implements BeanFactoryPostProcessor {

    private String name = "张三";

    /**
     * 这个BeanFactoryPostProcessor就是在把Map<beanName,BeanFination>填充完之后可以调用，
     * 是Spring给我们提供的一个扩展点，不是refresh的那个空方法，可以再这里面进行一些操作
     * 比如把Map里的一些beanfine修改
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition beanConfig = (GenericBeanDefinition) beanFactory.getBeanDefinition("beanFactoryPostProcessorTest");
        beanConfig.setBeanClass(Demo.class);
    }

}


