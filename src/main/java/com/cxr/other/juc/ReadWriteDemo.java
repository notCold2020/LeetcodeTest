package com.cxr.other.juc;

import org.aspectj.weaver.ast.Var;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteDemo {
    private static Buffer buffer = new Buffer();
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                buffer.add(finalI,String.valueOf(finalI));
            }).start();
        }
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                buffer.get(finalI);
            }).start();
        }
    }
}

class Buffer {
    HashMap<Integer, Object> hashMap = new HashMap<>();
    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void add(int key, Object value) {
        reentrantReadWriteLock.writeLock().lock();//写锁 锁
        System.out.println(Thread.currentThread().getName() + "放入了" + key);
        hashMap.put(key, value);
        System.out.println("放入ok");
        reentrantReadWriteLock.writeLock().unlock();
    }

    public void get(int key) {
        reentrantReadWriteLock.readLock().lock();
        Object o = hashMap.get(key);
        System.out.println(Thread.currentThread().getName() + "拿到了" + o.toString());
        reentrantReadWriteLock.readLock().unlock();
    }
}
