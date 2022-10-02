package com.cxr.other.spring.springMyselfDemo;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Date 2022/5/21 10:27 下午
 * @Created by CiXingrui
 */
@CixingruiCompomnet("BeanPostProcessor")
public class BeanPostProcessor implements CixingruiBeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前");

        if (beanName.equalsIgnoreCase("DomainService")) {
            ((DomainService) bean).setBeanPostProcessorName("okokokok");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化后");

        if (beanName.equalsIgnoreCase("DomainService")) {
            Object instance = Proxy.newProxyInstance(BeanPostProcessor.class.getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("前置AOP");
                            return method.invoke(bean, args);
                        }
                    });
            return instance;
        }

        return bean;
    }

}
