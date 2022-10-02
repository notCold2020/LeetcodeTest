package com.cxr.other.spring.beanLifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

public class MyLifeCyc implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean {

    private dd dd;

    @Resource
    public void setDd(com.cxr.other.spring.beanLifeCycle.dd dd) {
        System.out.println("2:"+this.getClass().getSimpleName() + "依赖注入");
        this.dd = dd;
    }

    /**
     * BeanPostProcessor的坑：
     * 先说结论:
     * 如果在当前bean中实现BeanPostProcessor接口，那么BeanPostProcessor接口里面的两个方法不执行(本来应该在初始化前后执行的)
     *
     * 因为项目一启动，会吧所有实现了BeanPostProcessor接口的类优先创建和注册(执行构造方法)，然后放到一级缓存的map中
     * 后面再想用我们当前这个实现了BeanPostProcessor接口的bean(此时当前的bean已经在一级缓存中了)就直接去缓存中拿
     * https://ask.csdn.net/questions/679699
     *
     * 还得 todo
     * 说白了就是实现了BeanPostProcessor接口之后 这个类就会先走一次生命周期 但是这个生命周期和我们平时的不太一样
     */


//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println(bean.getClass().getSimpleName() + "对象" + beanName + "开始初始化--BeanPostProcessor");
//        return bean;
//    }
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println(bean.getClass().getSimpleName() + "对象" + beanName + "初始化完成--BeanPostProcessor");
//        return bean;
//    }
    public MyLifeCyc() {
        System.out.println("1:"+this.getClass().getSimpleName() + "调用构造方法");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("3:"+this.getClass().getSimpleName() + "调用BeanNameAware接口的setBeanName方法");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("4:"+this.getClass().getSimpleName() + "调用BeanFactoryAware接口的setBeanFactory方法");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("6:"+this.getClass().getSimpleName() + "(注解初始化)调用自定义postConstruct方法");
    }

    public void myInit() {
        System.out.println("8:"+this.getClass().getSimpleName() + "(自定义初始化)调用自定义myInit方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("7:"+this.getClass().getSimpleName() + "(接口初始化)调用InitializingBean接口的afterPropertiesSet方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(this.getClass().getSimpleName() + "调用DisposableBean接口的DisposableBean方法");
    }

    public void myDestroy() throws Exception {
        System.out.println(this.getClass().getSimpleName() + "调用自定义的myDestroy方法");
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        System.out.println(this.getClass().getSimpleName() + "调用自定义的preDestroy方法");
    }
}

@Component
class dd{

}
