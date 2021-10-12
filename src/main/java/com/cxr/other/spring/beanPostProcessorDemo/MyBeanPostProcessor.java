package com.cxr.other.spring.beanPostProcessorDemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 这个玩意得交给spring来管理，不交给spring管理Spring怎么调用你呢？
 *
 * 并且是BeanPostProcessor里面的这个两个方法是在IOC容器初始化的过程中完事的
 * 所以我们重写了BeanPostProcessor之后 再去IOC容器中拿bean会拿到我们增强过的bean
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor, InitializingBean {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if (aClass == BaseServiceImpl.class) {
            System.out.println("bean 对象初始化之前······");//注意是初始化
        }
        return bean;
        // return bean对象监控代理对象
    }

    @Override
    public Object postProcessAfterInitialization(final Object beanInstance, String beanName) throws BeansException {
        // 为当前 bean 对象注册监控代理对象，负责增强 bean 对象方法的能力
        Class beanClass = beanInstance.getClass();
        if (beanClass == BaseServiceImpl.class) {
            Object proxy = Proxy.newProxyInstance(beanInstance.getClass().getClassLoader(),
                    beanInstance.getClass().getInterfaces(),
                    new InvocationHandler() {
                        /**
                         * @param proxy 代理监控对象
                         * @param method doSome()方法
                         * @param args doSome()方法执行时接收的实参
                         * @return
                         * @throws Throwable
                         */
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("ISomeService 中的 doSome() 被拦截了···");
                            String result = (String) method.invoke(beanInstance, args);
                            return result.toUpperCase();
                        }
                    });
            return proxy;
        }
        return beanInstance;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("---初始化---");
    }
}
