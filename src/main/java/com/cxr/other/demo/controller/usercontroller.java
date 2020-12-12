package com.cxr.other.demo.controller;

import com.cxr.other.demo.entriy.User;
import com.cxr.other.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class usercontroller {
    @Autowired

    private UserService userService;

    @RequestMapping(value = {
            "",
            "/page",
            "page*",
            "view/*,**/msg"
    })
    //@RequestBody 用来接收json数据
    // 如果用对象来接收并且和对象中的变量对应上 那就能拿到这个对象
    //如果用String 来接收那就能拿到个json的String
    @ResponseBody
    private String getUser(String user,String t1) {
//        User user1 = new User(user.getId(),user.getPwdddd(),user.getUserName());
        return user + t1;
    }
}
