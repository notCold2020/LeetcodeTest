package com.cxr.other.spring.lookUpDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Date 2022/5/18 9:03 下午
 * @Created by CiXingrui
 */
public class TestLookUp {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(C.class);
        C bean = applicationContext.getBean(C.class);
        /**
         * 这里如果不加@lookup 那么会发现D明明是原型但是却每次请求都返回了一样的地址，因为C是单例的，属性注入只注入一次
         * 加上了@lookup每次请求就会有代理对象帮我们从beanFactory重新创建对象
         *
         * 如果@Lookup(xxxx.class)
         * 那么可以从容器里面拿bean，来替换方法的返回值，如果这个注解打在抽象类的普通方法上，那么我们甚至可以把抽象类注入ioc容器。这个注入进去的"抽象类"是Cglib代理对象。
         */
        for (int i = 0; i < 4; i++) {
            D prototypeD = bean.getPrototypeD();
            System.out.println(bean + ":" + prototypeD);
            System.out.println("=============");
        }
    }

}
