package com.cxr.other.redisTest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 缓存击穿解决方案
 */
public class RedisCacheBreak {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    ReentrantLock reentrantLock = new ReentrantLock();

    public String getDate(String key) throws InterruptedException {

        String s = stringRedisTemplate.opsForValue().get(key);

        if (StringUtils.isEmpty(s)) {
            //失败了 没拿到这个key 可能是缓存击穿 它扛不住了 去尝试获取锁
            if (reentrantLock.tryLock()) {
                //获取到锁了 去数据库里面拿数据
                s = getDateFromDataBase(key);
                if (s != null) {
                    //更新缓存 会被覆盖 和hashmap一样
                    stringRedisTemplate.opsForValue().set(key, s);
                }
                //释放锁
                reentrantLock.unlock();
            } else {
                //没获取到锁 睡一会再尝试获取
                Thread.sleep(3000);
                s = getDate(key);
                return s;

            }
        }
        return s;

    }

    private String getDateFromDataBase(String s) {
        return null;
    }


}
