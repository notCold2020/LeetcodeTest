package com.cxr.other.filterTest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class filterController {


    @RequestMapping("/filter/test01")
    public String test01(@RequestBody String user, HttpServletRequest request) {
        /**
         * 这里的request是拿不到在filter set进去的值的（s56）,但是放进session里面可以
         */
        System.out.println("controller开始");
        System.out.println("controller结束");
        String s1 = request.getParameter("s56");
        Object abc = request.getSession().getAttribute("abc");
        return s1 + user + abc;
    }

}
