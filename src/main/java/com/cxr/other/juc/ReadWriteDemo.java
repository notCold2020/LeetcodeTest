package com.cxr.other.juc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteDemo {

    private static Buffer buffer = new Buffer();

}

class Buffer {

    public static ConcurrentHashMap<Integer, Object> hashMap = new ConcurrentHashMap<>();
    public static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);


    public void set(int key, Object value) {
        reentrantReadWriteLock.writeLock().lock();//写锁 锁
        /**
         * 这里一定是操作了共享变量的写操作
         * hashMap.clear()都算
         * 主要加写锁就是希望我在修改的时候，不让别的线程读
         */
        hashMap.putIfAbsent(key, value);
        reentrantReadWriteLock.writeLock().unlock();
    }

    public void get(int key) {
        /**
         * 有读锁 不能再加写锁 可以再加读锁
         * 有写锁 任何锁都不能再加 写锁是个排他锁
         *
         * 读锁的意义在于防止读到写的中间值
         */
        reentrantReadWriteLock.readLock().lock();
        hashMap.get(key);
        reentrantReadWriteLock.readLock().unlock();
    }
}
