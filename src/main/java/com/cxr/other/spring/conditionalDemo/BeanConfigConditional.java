package com.cxr.other.spring.conditionalDemo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application.yml")
public class BeanConfigConditional {

    //只有一个类时，大括号可以省略
    //如果WindowsCondition的实现方法返回true，则注入这个bean
    @Conditional({WindowsCondition.class})
    @Bean(name = "bill")
    public Person person1() {
        return new Person("Bill Gates", 62);
    }


    //如果LinuxCondition的实现方法返回true，则注入这个bean
    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person2() {
        return new Person("Linus", 48);
    }

    /**
     * 当前bean只有配置文件中有loginFilter=true的时候才会被加载
     */
    @Bean("Person3")
    @ConditionalOnProperty(name = "loginFilter", havingValue = "true")
    public Person person3() {
        return new Person("Person3", 22);
    }


    /**
     * 当有一个name叫Person3的bean存在的时候 当前bean 才会生效
     * 也可以用value直接指定Person
     */
    @Bean("Person4")
    @ConditionalOnBean(name = "Person3", value = Person.class)
    public Person person4() {
        return new Person("Person4", 22);
    }
}
