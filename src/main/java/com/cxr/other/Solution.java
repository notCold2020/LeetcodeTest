package com.cxr.other;

import java.util.Arrays;
import java.util.List;

public class Solution {

//    public static void main(String[] args) {
//        User user = new User();
//        user.setUserName("123");
//        user.setPwdddd("pwd");
//        Object a = user;
//        ImageDO imageDO = new ImageDO();
//        BeanUtils.copyProperties(a,imageDO);
//    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("".split(","));
        list.forEach(m->{

            System.out.println(m.isEmpty());
        });
    }


}
