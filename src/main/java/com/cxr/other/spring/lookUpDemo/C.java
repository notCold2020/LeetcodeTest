package com.cxr.other.spring.lookUpDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Date 2022/5/18 8:57 下午
 * @Created by CiXingrui
 */
@ComponentScan("com.cxr.other.spring.lookUpDemo")
public class C {

    @Autowired
    private D d;

    @Lookup
    public D getPrototypeD() {
        return d;
    }


}
