package com.cxr.other.spring.beanLifeCycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.cxr.other.spring.beanLifeCycle")
@Configuration
public class Appconfig {
    @Bean(initMethod = "myInit", destroyMethod = "myDestroy")
    MyLifeCyc myLifeCyc() {
        return new MyLifeCyc();
    }
}
