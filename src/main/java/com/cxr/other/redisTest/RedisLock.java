package com.cxr.other.redisTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 默认等待获取锁超时时间（毫秒），这里为10000毫秒，即10秒
     * 即如果有并发 别人拿到了这把锁 最多允许当前程序等多久？ --> while循环那里
     */
    private static final long DEFAULT_WAIT_TIME = 10000;

    /**
     * 锁失效时间（毫秒），过期删除，这里为300秒
     */
    private static final int LOCK_EXPIRE_TIME = 300;

    /**
     * 尝试获取锁,不管是否获取到锁立即返回获取状态
     * 如果10个请求并发获取锁，那么剩下的没获取到的就会被丢弃
     *
     * @param lockKey
     * @return
     */
    public boolean tryLock(String lockKey) {
        try {
            // 锁失效时间300秒
            String lockExpireTimeStr = getLockExpireTimeStr();
            return doGetLock(lockKey, lockExpireTimeStr);
        } catch (Exception e) {
            LOGGER.error("RedisLock.tryLock() Exception:" + e.getMessage());
            unlock(lockKey);// 出异常时删除缓存key
            return false;
        }
    }

    /**
     * 循环获取锁，使用默认的获取锁超时时间 10秒
     *
     * @param lockKey 锁名称
     */
    public void lock(String lockKey) {
        lock(lockKey, DEFAULT_WAIT_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 循环获取锁，直到超时
     *
     * @param lockKey
     * @param waitTime 超时时间
     * @param timeUnit 超时时间单位
     */
    public void lock(String lockKey, long waitTime, TimeUnit timeUnit) {
        try {
            long currentNanoTime = System.nanoTime();
            long waitTimeoutNano = timeUnit.toNanos(waitTime);// 获取锁等待超时时间默认10秒

            // 锁失效时间60秒
            String lockExpireTimeStr = getLockExpireTimeStr();

            /**
             * 这里是精髓，value是时间戳+过期时间 随着时间的增长
             * (System.nanoTime() - currentNanoTime)会越来愈大 知道不满足 < waitTimeoutNano跳出循环
             */
            while ((System.nanoTime() - currentNanoTime) < waitTimeoutNano) {// 未获取到则重试
                if (doGetLock(lockKey, lockExpireTimeStr)) {// 获取到锁
                    return;
                } else {
                    Thread.sleep(100);//未获取到锁短暂休眠，并继续尝试获取锁
                }
            }
        } catch (Exception e) {
            LOGGER.error("RedisLock.lock() Exception:" + e.getMessage());
            unlock(lockKey);
            throw new RuntimeException(String.format("get lock %s failed", lockKey));
        }
        //已超出加锁的最长等待时间
        throw new RuntimeException(String.format("get lock %s time out", lockKey));
    }

    /**
     * 获取锁失效时间字符串，失效时间为当前时间加60秒
     *
     * @return
     */
    private String getLockExpireTimeStr() {
        return String.valueOf(System.currentTimeMillis() + (LOCK_EXPIRE_TIME * 1000));
    }

    /**
     * 执行获取锁操作
     * 这个就是单纯的获取锁⁄
     * @param lockKey
     * @param lockExpireTimeStr
     * @return
     */
    private boolean doGetLock(String lockKey, String lockExpireTimeStr) {
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(lockKey, lockKey, LOCK_EXPIRE_TIME, TimeUnit.SECONDS);

//        String currentLockExpireTimeStr = RedisOperator.get(lockKey);// 获取缓存中设置的锁的过期时间
//        // 检查锁是否过期，这里主要是防止setNx命令执行后redis崩溃未执行expire操作造成死锁
//        if (null != currentLockExpireTimeStr && Long.parseLong(currentLockExpireTimeStr) < System.currentTimeMillis()) {
//            String oldValue = RedisOperator.getSet(lockKey, lockExpireTimeStr);// 获取设置缓存之前的时间戳
//            // 防止锁过期后，多个线程并发获取到锁，通过对比获取的时间戳和设置时的时间戳是否相同判断是否当前线程获取到锁
//            if (null != oldValue && oldValue.equals(currentLockExpireTimeStr)) {
//                return true;
//            }
//        }
        return aBoolean;
    }

    /**
     * 释放锁
     *
     * @param lockKey
     */
    public void unlock(String lockKey, String uuid) {
        Object o = redisTemplate.opsForValue().get(lockKey);
        if (o.equals(uuid)) {
            redisTemplate.delete(lockKey);
        }
    }

    /**
     * 释放锁 原始版本
     *
     * @param lockKey
     */
    public void unlock(String lockKey) {
        redisTemplate.delete(lockKey);
    }

    /**
     * 在redis锁中执行业务逻辑
     *
     * @param lockKey
     */
    public <T> T doInLock(String lockKey, LockExecutor<T> executor) {
        return doInLock(lockKey, DEFAULT_WAIT_TIME, TimeUnit.MILLISECONDS, executor);
    }

    /**
     * 在redis锁中执行业务逻辑
     *
     * 这个函数式编程还挺好的
     * @param lockKey
     * @param waitTime
     * @param timeUnit
     */
    public <T> T doInLock(String lockKey, long waitTime, TimeUnit timeUnit, LockExecutor<T> executor) {
        try {
            T result;
            lock(lockKey, waitTime, timeUnit);
            try {
                result = executor.execute();
            } catch (Exception e) {
                LOGGER.error("RedisLock.doInLock() LockExecutor Exception:" + e.getMessage());
                unlock(lockKey);
                throw e;
            }
            unlock(lockKey);
            return result;
        } catch (Exception e) {
            LOGGER.error("RedisLock.doInLock() Exception:" + e.getMessage());
            throw new RuntimeException("RedisLock.doInLock() Exception" + e.getMessage());
        }
    }

    /**
     * 获取锁之后的业务逻辑执行器，用于执行代码逻辑
     *
     * 直接函数式编程写代码逻辑就可以了
     */
    public interface LockExecutor<T> {
        T execute();
    }
}
