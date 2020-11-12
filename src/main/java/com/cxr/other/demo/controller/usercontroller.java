package com.cxr.other.demo.controller;

import com.cxr.other.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class usercontroller {
    @Autowired
    private UserService userService;

//    @PostMapping("/test")
//    @ResponseBody
//    //接收的是个对象 就得用这个标签 但是传的参数得和实体对应
//    private User getUser(@RequestBody User user) {
//        User user1 = userService.getUser(user.getName(), user.getPassword());
//        return user1;
//    }
}
