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
     *
     * 实现该接口，可以在spring的bean创建之前，修改bean的定义属性。也就是说，Spring允许BeanFactoryPostProcessor在容器实例化任何其它bean之前读取配置元数据，
     * 并可以根据需要进行修改，例如可以把bean的scope从singleton改为prototype，也可以把property的值给修改掉。
     * 可以同时配置多个BeanFactoryPostProcessor，并通过设置'order'属性来控制各个BeanFactoryPostProcessor的执行次序。
     * 注意：BeanFactoryPostProcessor是在spring容器加载了bean的定义文件之后，在bean实例化之前执行的。接口方法的入参是ConfigurableListableBeanFactory，使用该参数，可以获取到相关bean的定义信息，
     * 原文链接：https://blog.csdn.net/caihaijiang/article/details/35552859
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition beanConfig = (GenericBeanDefinition) beanFactory.getBeanDefinition("beanFactoryPostProcessorTest");
        beanConfig.setBeanClass(Demo.class);
        beanConfig.setScope("xxxx");
    }

}


