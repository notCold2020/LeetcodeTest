package com.cxr.other.redisTest;

import com.cxr.other.demo.entriy.User;
import com.cxr.other.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    public static int count = 0;

    static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @PostMapping("/redis/test")
    @ResponseBody
    public User test(@RequestBody User user) {
        long currentTime = System.currentTimeMillis();
        if (redisTemplate.hasKey("limit")) {
            //主要这个set会越来越大 我们把它排序只取前5个
            //也就是在下面这个时间段里面
            int count = redisTemplate.opsForZSet().rangeByScore("limit", currentTime - 10 * 1000, currentTime).size();
            if (count != 0 && count > 5) {
                logger.info("10s内最多访问5次");
                return null;
            }
        }
        //这里我们只需要分数即可 value是什么我们不在乎 给个uuid即可 别因为重复给覆盖掉了就行
        redisTemplate.opsForZSet().add("limit", UUID.randomUUID().toString(), currentTime);
//        return userService.getUser(user.getName(), user.getPassword());
        return new User();
    }

}
