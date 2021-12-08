package com.cxr.other.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//计数器
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(8);
        for (int i = 0; i < 8; i++) {
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

    /**
     * 场景：现在有一个list集合(一批数据),需要对其进行一个链路很长的操作 --> 长度100的list 同步要100s 100个线程要1s
     * 问题点：链路太长，同步执行太慢
     * 解决方案：线程池配合countdownLatch
     * 结果：
     */
    public static void threadPoolAndCountDownLatchDemo() {
        List<String> ids = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(ids.size());

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (String id : ids) {
            //对于集合中的每一项都处理
            executorService.execute(() -> {
                //1.模拟链路特别长的操作
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //2.计数器减减，
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await(60, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            //收集错误日志
            e.printStackTrace();
        }

    }
}
