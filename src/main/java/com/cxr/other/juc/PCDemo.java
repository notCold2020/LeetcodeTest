package com.cxr.other.juc;

import java.util.Date;

import static com.alibaba.dubbo.common.utils.LogUtil.start;

public class PCDemo {

    public static void main(String[] args) {
        Data date = new Data();
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

/**
 * 问题：为什么用notifyAll()不用notify()?
 * 有可能有这种情况：
 * 消费层发现队列里面没有东西了 叫醒生产者1去生产 自己去睡觉
 * 生产者1被唤醒 生产 有货了 自己去睡觉 唤醒一个线程
 * 这个唤醒是随机唤醒的 于是唤醒了生产者2 生产者2来了一看诱惑啊 于是也去睡觉了
 * 至此 三个人都睡觉了
 */
class Data {

    private Integer count = 10;

    public synchronized void add() throws InterruptedException {
        while (count != 0) {
            wait();
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "生产+1，当前还有：" + count);
        notifyAll();
    }

    public synchronized void dcr() throws InterruptedException {
        while (count == 0) {
            wait();
        }
        count--;
        System.out.println(Thread.currentThread().getName() + "消费-1，当前还有：" + count);
        notifyAll();
    }
}
