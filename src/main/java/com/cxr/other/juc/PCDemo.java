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
