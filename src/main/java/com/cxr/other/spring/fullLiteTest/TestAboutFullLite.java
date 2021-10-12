package com.cxr.other.spring.fullLiteTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Component
@Configuration
/**
 * Full模式就是在@configuration里面使用@Bean，会被Cglib代理方法，下面的b()会被调用1次,也就是检测一下，如果容器里面有了就不再把方法的返回值放进ioc容器
 * Lite模式就是不在@configuration里面使用@Bean，不会被Cglib代理方法，下面的b()会被调用2次（实例化a的时候调用一次，实例化b的时候调用一次）
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
        return new B();
    }
}
