package com.lrw.ohter.redistest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class RedisLockByString {

    @Autowired
    private RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(RedisLockByString.class);


    @GetMapping("/redislock")
    @ResponseBody
    public String test() {
        String uuid = UUID.randomUUID().toString();
        //false说明 已经被锁了
        Boolean apple = redisTemplate.opsForValue().setIfAbsent("apple", uuid, 30, TimeUnit.SECONDS);
        /**
         * 过期时间是为了防止锁超时 比如A挂了
         * UUID是为了防止释放锁 释放错了
         */
        try {
            if (!apple) {
                //被锁
                logger.info("商品被锁定");
                return null;
            }

            int stock = Integer.parseInt( String.valueOf(redisTemplate.opsForValue().get("stock")));
            if (stock > 0) {
                stock-=1;
                redisTemplate.opsForValue().set("stock", String.valueOf(stock));
                logger.info("还有库存：" + stock);
            } else {
                logger.info("库存不足");
                return null;
            }
        } finally {

            if (uuid.equals(redisTemplate.opsForValue().get("apple"))) {
                redisTemplate.delete("apple");
                logger.info("锁释放了");
            }

        }
        return null;


    }


}
