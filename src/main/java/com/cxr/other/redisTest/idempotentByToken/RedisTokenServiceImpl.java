package com.cxr.other.redisTest.idempotentByToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisTokenServiceImpl implements RedisTokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //生成Token
    @Override
    public String getToken() {
        //使用UUID生成 token
        String token = UUID.randomUUID().toString();
        //存入Redis,key：token，过期时间3分钟
        stringRedisTemplate.opsForValue().set(token, token, 3, TimeUnit.MINUTES);
        return token;
    }

    //删除Token,true表示第一次提交，false表示重复提交
    @Override
    public Boolean deleteToken(String token) {
        return stringRedisTemplate.delete(token);
    }
}

