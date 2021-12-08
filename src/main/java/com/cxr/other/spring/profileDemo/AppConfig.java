package com.cxr.other.spring.profileDemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.beans.PropertyVetoException;

/**
 * 1.突然有个疑惑 如果用@Bean注入了3个不同name的bean，那么我们根据类型getBean(.Class)的时候会拿出来哪个呢？
 * 报错了，一般我们都是用@Component,所以保证了一个类型的bean只会注入一次
 * <p>
 * Exception in thread "main" org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of
 * type 'com.cxr.other.spring.profileDemo.ProfileTest' available: expected single matching bean but
 * found 3: testDataSource,devDataSource,proDataSource
 */
@Configuration
public class AppConfig {

    // 测试环境
    @Profile("test")
    @Bean("testDataSource")
    public ProfileTest dataSourceTest() throws PropertyVetoException {
        ProfileTest profileTest = new ProfileTest();
        profileTest.setBeanName("test");
        return profileTest;
    }

    // 开发环境
    @Profile("dev")
    @Bean("devDataSource")
    public ProfileTest dataSourceDev() throws PropertyVetoException {
        ProfileTest profileTest = new ProfileTest();
        profileTest.setBeanName("dev");
        return profileTest;
    }

    // 生产环境
    @Profile("pro")
    @Bean("proDataSource")
    public ProfileTest dataSourcePro() throws PropertyVetoException {
        ProfileTest profileTest = new ProfileTest();
        profileTest.setBeanName("pro");
        return profileTest;
    }
}
