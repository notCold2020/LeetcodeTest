package com.cxr.other.spring.beanWrapperDemo;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @Author: CiXingrui
 * @Create: 2021/8/8 6:28 下午
 */
public class TestBeanWrapper {
    /**
     * 我理解这个玩意挺鸡肋的，就是把对bean进行增强
     * setPropertyValue的key就是对应bean里面的变量
     * 就是把bean撕开一个口子，可以操作里面的全局变量
     */
    public static void main(String[] args) {
        BeanWrapper company = new BeanWrapperImpl(new Employee());
        company.setPropertyValue("employee", company.getWrappedInstance());

        String name = (String) company.getPropertyValue("employee.name");
        System.out.println(name);
    }
}
