package com.cxr.other.spring.importSelector;

import org.springframework.context.annotation.Configuration;

/**
 * @Author: CiXingrui
 * @Create: 2021/8/6 10:31 下午
 *
 * 自定义开启配置注解
 */
@EnableAutoConfigSelf(reference = "com.cxr")
@Configuration
public class beanConfig {
}
