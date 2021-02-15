package com.cxr.other.juc;

import java.util.concurrent.CountDownLatch;

//计数器
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(8);
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println("---" + finalI);
                countDownLatch.countDown();
            }).start();
            Thread.sleep(1000);
        }
        countDownLatch.await();
        System.out.println("GG");
    }
}
