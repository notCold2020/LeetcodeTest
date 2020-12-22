package com.cxr.other.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 抢车位 用于限流
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();//获取许可
                    System.out.println(finalI + "抢到了车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(finalI + "释放了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();//释放许可
                }
            }).start();
        }
    }
}
