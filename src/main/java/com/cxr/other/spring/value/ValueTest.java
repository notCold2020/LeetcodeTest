package com.cxr.other.spring.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/5/17 11:41 上午
 * @Created by CiXingrui
 */
@Component
public class ValueTest {

    /**
     * 常规用法
     */
    @Value("${testValue}")
    private String value;

    /**
     * key不存在的时候指定默认值
     */
    @Value("${testValue111:我是默认值}")
    private String valueDefault;


    /**
     * 如果@value写在很多地方，不便于管理
     * 可以自定义一个注解，写在注解上
     */
    @ValueSelf
    private String interfaceValue;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueDefault() {
        return valueDefault;
    }

    public void setValueDefault(String valueDefault) {
        this.valueDefault = valueDefault;
    }

    public String getInterfaceValue() {
        return interfaceValue;
    }

    public void setInterfaceValue(String interfaceValue) {
        this.interfaceValue = interfaceValue;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Appconfig.class);
        ValueTest bean = applicationContext.getBean(ValueTest.class);
    }
}
