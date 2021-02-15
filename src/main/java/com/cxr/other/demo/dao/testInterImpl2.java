package com.cxr.other.demo.dao;

import org.springframework.stereotype.Service;

@Service(value = "testInterImpl2")
public class testInterImpl2 implements testInter {
    @Override
    public void testInter() {
        System.out.println("testInterImpl2");
    }
}
