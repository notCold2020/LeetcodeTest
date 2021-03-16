package com.cxr.other.redisTest;

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
        //uuid是为了区分不同的客户端
        String uuid = UUID.randomUUID().toString();
        /*false说明 已经被锁了
            这个apple才是真正的锁 下面的stock是商品的库存
         */
        Boolean apple = redisTemplate.opsForValue().setIfAbsent("apple", uuid, 30, TimeUnit.SECONDS);
        /**
         * 过期时间是为了防止锁超时 比如A挂了,
         * 锁过期的时间要小于程序的执行时间
         * UUID是为了防止释放锁 释放错了
         */
        try {
            if (!apple) {
                //被锁
                logger.info("商品被锁定");
                return null;
            }
            //获取商品库存
            int stock = Integer.parseInt(String.valueOf(redisTemplate.opsForValue().get("stock")));//注意这里的key和上面的不一样
            if (stock > 0) {
                stock -= 1;
                redisTemplate.opsForValue().set("stock", String.valueOf(stock));//更新缓存
                logger.info("还有库存：" + stock);
            } else {
                logger.info("库存不足");
                return null;
            }
        } finally {
            //为了防止释放错锁了
            if (uuid.equals(redisTemplate.opsForValue().get("apple"))) {
                redisTemplate.delete("apple");
                logger.info("锁释放了");
            }

        }
        return null;


    }


}
