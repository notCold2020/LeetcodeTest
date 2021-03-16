package com.cxr.other.redisTest.idempotentByToken;

import com.cxr.other.demo.entriy.User;
import com.cxr.other.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述:
 * 验证重复提交
 *
 */
@RestController
@RequestMapping("/RepeatLimiter")
public class RepeatLimiterController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTokenService redisTokenService;


    @PostMapping("/add")
    @RepeatLimiter
    public String add(@RequestBody User user){
        //userService.insertUser(user); //有字段唯一性，故注释
        return "添加成功";
    }

    @GetMapping("/token")
    public String getToken(){
        String token = redisTokenService.getToken();
        return token;
    }


}

