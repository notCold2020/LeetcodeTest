package com.cxr.other.spring.beanWrapperDemo;

import lombok.Data;

/**
 * @Author: CiXingrui
 * @Create: 2021/8/8 6:27 下午
 */
@Data
public class Employee {
    private String name = "慈星锐";
    private float salary;

    private Employee employee;

}
