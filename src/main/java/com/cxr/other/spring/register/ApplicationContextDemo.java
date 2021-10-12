package com.cxr.other.spring.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextDemo {

    private static ApplicationContext applicationContext;

    public void register(String beanName,Class<?> beanClass){
    	BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
    	try {
    		beanRegistry.getBeanDefinition(beanName);
    		return;
    	} catch (Exception e) {
    	    //核心在这里
    		GenericBeanDefinition definition =  new GenericBeanDefinition();
    		definition.setBeanClass(beanClass);
            definition.setScope(BeanDefinition.SCOPE_SINGLETON);
            beanRegistry.registerBeanDefinition(beanName, definition);
    	}
    }

    @Autowired
    public void setApplicationContext(ApplicationContext appCtx) {
        applicationContext = appCtx;
    }

}
