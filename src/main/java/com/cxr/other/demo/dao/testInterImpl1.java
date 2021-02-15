package com.cxr.other.demo.dao;

import org.springframework.stereotype.Service;

@Service(value = "testInterImpl1")
public class testInterImpl1 implements testInter {
    @Override
    public void testInter() {
        System.out.println("testInterImpl");
    }
}
