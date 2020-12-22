package com.cxr.other.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private Integer count = 40;
    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) reentrantLockDemo.save();
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) reentrantLockDemo.save();
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) reentrantLockDemo.save();
        }, "C").start();
    }
    Lock lock = new ReentrantLock();
    public void save() {
        try {
            /**
             * 1:  lock拿不到锁会一直等待。tryLock是去尝试，拿不到就返回false，拿到返回true。
             * 2:  tryLock是可以被打断的，被中断 的，lock是不可以。
             */
            lock.tryLock();
            if (count > 0) {
                count--;
                System.out.println(Thread.currentThread().getName() + "买了1张票，剩余：" + count + "张");
            }
        } finally {
            lock.unlock();
        }
    }
}
