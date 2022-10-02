package com.cxr.other.spring.factoryBeanDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/13 4:26 下午
 */
public class FactoryBeanDemoTest {
    public static void main(String[] args) {
        /**
         * 源码中getBean好像有单独的一块逻辑是给BeanFactory用的，重写getObject方法即可
         * 如果加上&，就是正常的getBean
         * 如果不加，就是get实现BeanFactory接口的类(DaoFactoryBean)的重写过的getObject()
         */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactoryBean.class);
        DaoFactoryBean daoFactoryBean = (DaoFactoryBean) applicationContext.getBean("&daoFactoryBean");//拿到的是ioc容器中的对象
        TempFactoryBean tempFactoryBean = (TempFactoryBean) applicationContext.getBean("daoFactoryBean");//是getObject返回值 `
        //这次就不会进入getObject方法了，盲猜上一次getObject方法已经把bean放进哪存起来了了，这次直接去缓存中获取就好了
        TempFactoryBean tempFactoryBean2 = (TempFactoryBean) applicationContext.getBean("&daoFactoryBean");//是getObject返回值 `
        //ioc容器的bean
        DaoFactoryBean tempFactoryBean3 = (DaoFactoryBean) applicationContext.getBean("&daoFactoryBean");//是getObject返回值 `

    }
}
