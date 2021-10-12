package com.cxr.other.spring.register;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class BeanFactoryPostProcessorDemo implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) beanFactory;
        //自定义逻辑，注入beandefinition
        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(OOO.class);
        definition.setScope(BeanDefinition.SCOPE_SINGLETON);

        beanRegistry.registerBeanDefinition("BeanFactoryPostProcessorDemo", definition);
    }
}
