package com.cxr.other.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class CyclicBarrierDemo {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            System.out.println("先执行我");
        });

        for (int i = 0; i < 7; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println("--" + finalI);
                    cyclicBarrier.await();
                    System.out.println("突破！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(1000);
        }
    }
}
