package com.cxr.other.spring.beanPostProcessorDemo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(value = "com.cxr.other.spring.beanPostProcessorDemo")
public class BaseServiceImpl implements BaseService {

    public BaseServiceImpl() {
        System.out.println("---BaseServiceImpl实例化---");

    }

    public String doSomething() {
        return "Hello AlanShelby"; // 增强效果：返回内容全部大写
    }

    public String eat() {
        return "eat food";
    }
}
