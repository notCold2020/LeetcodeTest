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
        /**
         * false说明 已经被锁了
         * 这个apple才是真正的锁 下面的stock是商品的库存
         *
         * 下面这步就相当于acquireLock()尝试获取锁
         * 如果是分布式锁 下面这个key应该是不变的 不同服务的请求过来要求都会被锁住，所以key应该对于不同的请求来说都是一样的
         * 如果是幂等性 那么就要不同的服务请求过来都是不一样的key 因为幂等性只是保证一个服务的请求过来被锁住就行 其他的服务不能被锁，要是被
         * 锁了那不和分布式锁一样了，而且幂等性就是对于一个服务来说的
         */
        Boolean apple = redisTemplate.opsForValue().setIfAbsent("apple", uuid, 30, TimeUnit.SECONDS);
        /**
         * 过期时间是为了防止锁超时 比如A挂了,
         * 锁过期的时间要大于程序的执行时间
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
