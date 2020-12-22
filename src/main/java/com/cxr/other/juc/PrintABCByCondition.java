package com.cxr.other.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABCByCondition {
    public static void main(String[] args) {
        Data3 data = new Data3();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printA();
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printB();
            }
        },"B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printC();
            }
        },"C").start();

    }

}

class Data3 {
    Lock lock = new ReentrantLock();
    //只不过类似个锁标志位  下面的condition更改了也一样
    //这就是三把锁 进来了发现有锁就await()也不是非得是固定的锁 但是唤醒和await得是同一把锁
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    int number = 1;

    public void printA() {
        lock.lock();
        try {
            while (number != 1) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + ":AAA");
            number = 2;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (number != 2) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + ":BBB");
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (number != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + ":CCC");
            number = 1;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
