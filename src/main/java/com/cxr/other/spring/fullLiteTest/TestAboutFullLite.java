package com.cxr.other.spring.fullLiteTest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
//@Configuration
/**
 * 1.Full模式就是在@configuration里面使用@Bean，会被Cglib代理方法，这个@Bean所在的方法的意义就是用来创建bean放到ioc容器中，所以当前@bean所在的放啊
 * 无论是被其他代理对象调用还是像下面意义在a()中调用b()，只要ioc容器中存在方法的返回值的这个bean，那么这次调用都不会执行。
 * 所以@Configuration+@Bean的情况是a()执行一次，执行途中调用了b()，b()方法要单独执行的时候发现自己已经被执行过了，代理对象就算直接调用b()也不会执行，因为
 * 这俩方法的作用就是放进ioc容器，现在已经放进去了。
 *
 * 2.Lite模式就是不在@configuration里面使用@Bean，不会被Cglib代理方法，下面的b()会被调用2次（实例化a()的时候调用一次，单独实例化b()的时候调用一次）
 * 而且这个时候如果单独通过代理对象进行调用b()，也是会调用成功的。
 *
 * 3.那如果@Component+@bean ioc容器中还会有A B这两个类吗？
 * 也会拿到的，AB都被实例化后放进ioc容器中了
 *
 * 4.如果没有@Component呢 @Bean还会生效吗
 * 那肯定不能
 */
public class TestAboutFullLite {

    @Bean
    public A a(){
        //当类上注解为@Component时idea可能会出现提示但不会阻止代码编译运行, idea还是很nice的
        b();
        return new A();
    }

    @Bean
    public B b(){
        System.out.println("执行了B方法");
        return new B();
    }
}
