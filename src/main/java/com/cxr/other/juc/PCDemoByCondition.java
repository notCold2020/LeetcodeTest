package com.cxr.other.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PCDemoByCondition {
    public static void main(String[] args) {
        Data2 date = new Data2();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    date.add();
                } catch (InterruptedException e) {
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    date.dcr();
                } catch (InterruptedException e) {
                }
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    date.dcr();
                } catch (InterruptedException e) {
                }
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    date.dcr();
                } catch (InterruptedException e) {
                }
            }
        }, "D").start();
    }
}

class Data2 {
    private Integer count = 10;
    private Lock lock = new ReentrantLock();
    //相当于是个锁标志位
    Condition condition = lock.newCondition();


    public void add() throws InterruptedException {
        lock.lock();
        try {
            while (count != 0) {
                condition.await();
            }
            count++;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + "生产+1，当前还有：" + count);
        } finally {
            lock.unlock();
        }
    }

    public void dcr() throws InterruptedException {
        try {
            lock.lock();
            while (count == 0) {
                condition.await();
            }
            count--;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + "消费-1，当前还有：" + count);
        } finally {
            lock.unlock();
        }
    }
}
