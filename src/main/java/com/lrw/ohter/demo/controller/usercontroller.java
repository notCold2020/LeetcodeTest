package com.lrw.ohter.demo.controller;

import com.lrw.ohter.demo.entriy.User;
import com.lrw.ohter.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
