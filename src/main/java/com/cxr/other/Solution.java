package com.cxr.other;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@Slf4j
@Component
class Solution {


    public static void main(String[] args) throws IllegalAccessException, ParseException {

        String req_time = "2022-09-29 09:48:04";
        long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req_time).getTime();
        long l = System.currentTimeMillis() - time;
        int length = "9acddf55905442aaa464f1b3f02c1ae6".length();
    }





}

@Data
abstract class A {

    public String getName() {
        return name;
    }

    public A(String name) {
        this.name = name;
    }

    private final String name;

}




