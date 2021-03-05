package com.cxr.other.demo.controller;

import com.cxr.other.demo.dao.testInter;
import com.cxr.other.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class usercontroller {
    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(usercontroller.class);

    private testInter testInter;

    @Autowired
    public void setTestInter(@Qualifier("testInterImpl1") com.cxr.other.demo.dao.testInter testInter) {
        testInter.testInter();
        this.testInter = testInter;
    }

    @RequestMapping(value = {
            "/page",
            "page*",
            "view/*,**/msg"
    })
    //@RequestBody 用来接收json数据
    // 如果用对象来接收并且和对象中的变量对应上 那就能拿到这个对象
    //如果用String 来接收那就能拿到个json的String
    @ResponseBody
    private Integer getUser(@RequestParam(defaultValue = "15",required = false,value = "one") Integer one) {
//        User user1 = new User(user.getId(),user.getPwdddd(),user.getUserName());
        System.out.println(one.getClass().toString());
        logger.info("dddddd");
        return one;
    }
}
