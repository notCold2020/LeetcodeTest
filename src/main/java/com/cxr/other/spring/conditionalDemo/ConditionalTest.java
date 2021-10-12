package com.cxr.other.spring.conditionalDemo;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class ConditionalTest {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfigConditional.class);

    /**
     * 这个@Test
     * 我猜原理是反射调用所在类的全部？？
     * 现象：如果没有上面这个AnnotationConfigApplicationContext代码并不会初始化ioc容器
     *
     * @Condition就是在项目启动的时候，如果发现有这个注解就执行注解里面的.class ，这个class实现了Condition接口
     * match方法如果返回true才能把bean放进ioc容器
     */
    @Test
    public void test1(){
        String osName = applicationContext.getEnvironment().getProperty("os.name");
        System.out.println("当前系统为：" + osName);
        Map<String, Person> map = applicationContext.getBeansOfType(Person.class);
        System.out.println(map);
    }
}
