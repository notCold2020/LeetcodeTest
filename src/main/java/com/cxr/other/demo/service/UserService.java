package com.cxr.other.demo.service;

import com.cxr.other.demo.dao.userDao;
import com.cxr.other.demo.entriy.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private userDao userDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    Logger logger = LoggerFactory.getLogger(UserService.class);
    static int count = 0;

    public User getUser(String username, String password) {
        String user = "user:" + username;
        if (stringRedisTemplate.hasKey(user)) {
            logger.info("账户被锁定,还剩" + stringRedisTemplate.getExpire(user) + "长的时间解锁");
            return null;
        }
        User userByName = userDao.getUserByName(username);
        if (userByName == null) {
            logger.info("用户不存在，请注册");
            return null;
        } else {
            //用户存在 比对密码
            User user1 = null;
            if (user1 == null && ++count >= 5) {
                //次数大于5次 锁定 只要有这个key就处于锁定状态
                stringRedisTemplate.opsForValue().set(user, "锁定", 40, TimeUnit.SECONDS);
                logger.info("被锁定,还有" + stringRedisTemplate.getExpire(user) + "时间解锁");
                return null;

            } else if (user1 == null && count == 1) {
                //密码错了 并且是第一次输入错误
                stringRedisTemplate.opsForValue().set("user:error:" + username, String.valueOf(count), 20, TimeUnit.SECONDS);
                logger.info("密码输入错误，第" + count + "次,过期时间还有：" + stringRedisTemplate.getExpire("user:error:" + username));
                return null;

            } else if (user1 == null) {
                stringRedisTemplate.opsForValue().set("user:error:" + username, String.valueOf(count), 0);
                logger.info("密码输入错误，第" + count + "次,过期时间还有：" + stringRedisTemplate.getExpire("user:error:" + username));
                return null;

            } else if (stringRedisTemplate.hasKey("user:error:" + username)) {
                logger.info("登陆成功");
                count = 0;
                stringRedisTemplate.delete("user:error:" + username);
                return user1;
            }

            return user1;

        }


    }

}
