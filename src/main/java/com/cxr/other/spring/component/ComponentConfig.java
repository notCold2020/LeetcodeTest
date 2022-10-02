package com.cxr.other.spring.component;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @Date 2022/5/17 3:48 下午
 * @Created by CiXingrui
 */
@Configuration
@ComponentScan(value = "com.cxr.other.spring.component",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Cixingrui.class))
public class ComponentConfig {


}
