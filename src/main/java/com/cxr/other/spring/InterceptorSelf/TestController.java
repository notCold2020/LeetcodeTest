package com.cxr.other.spring.InterceptorSelf;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/admin/test1")
//    @Authority("放行")
    public String test1(@RequestParam(required = false, value = "userAuthority") String s) {
        System.out.println("controller");
        return "test1";
    }


}
