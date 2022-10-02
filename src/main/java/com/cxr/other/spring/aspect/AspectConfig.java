package com.cxr.other.spring.aspect;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Date 2022/5/28 11:17 上午
 * @Created by CiXingrui
 */
@ComponentScan("com.cxr.other.spring.aspect")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class AspectConfig {
}
