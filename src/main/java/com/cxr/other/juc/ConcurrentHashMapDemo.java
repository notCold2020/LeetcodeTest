package com.cxr.other.juc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: CiXingrui
 * @Create: 2021/12/8 10:55 上午
 */
public class ConcurrentHashMapDemo {

    /**
     * <name,vote>
     * 名称，点赞数
     */
    private static ConcurrentHashMap<String, AtomicInteger> xxxBuffer = new ConcurrentHashMap();

    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);

    public void vote1(String key, int delta) {
        reentrantReadWriteLock.readLock().lock();

        try {
            /**
             * 如果存在key，       就返回存在的value，不进行替换。
             * 如果不存在key       返回null          put进去
             *
             * 如果是普通的put
             * 那么当key冲突的时候 会直接把key对应的value覆盖掉
             */
            if (xxxBuffer.putIfAbsent(key, new AtomicInteger(delta)) != null) {//保证map中存在当前的键值对，保证get之后不会NPE
                xxxBuffer.get(key).addAndGet(delta);//先add再get
            }

        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }


}
