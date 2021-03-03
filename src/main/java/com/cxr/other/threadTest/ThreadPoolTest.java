package com.cxr.other.threadTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 非线程池方式
 * 开了1w个线程  创建又销毁 重复1w次
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();
        final List<Integer> list = new ArrayList<>();
        final Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                list.add(random.nextInt());
            });
            thread.start();
            thread.join();
        }
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("size:" + list.size());
    }
}

/**
 * 一个可以复用的线程循环1w次 这里的1个 指得是newSingleThreadExecutor()
 * 也是创建了1w次 但是复用了
 * execute其实还是提交线程 下面的例子 提交了1w个线程到线程池里面 线程池帮我们做一些操作(按顺序 或者 定长可复用)
 */
class ThreadPoolMethod {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Long start = System.currentTimeMillis();
        final List<Integer> list = new ArrayList<>();
        final Random random = new Random();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10000; i++) {
            Future<String> submit = executorService.submit(() -> {
                list.add(random.nextInt());
                Thread.sleep(2000);
                return "我是submit的返回值";
            });
            System.out.println("submit:" + submit.get());
        }
        executorService.shutdown();
        /**
         * 1.等所有已提交的任务（包括正在跑的和队列中等待的）执行完
         * 2.或者等超时时间到
         * 3.或者线程被中断，抛出InterruptedException   ----> 不然当前线程就阻塞
         * 说白了这不就是个保险吗 设置的时间过了 必然结束线程池
         *
         * awaitTermination:(放在shutdown()之后  不然必然是返回false)
         * 判断线程池中的线程是否在规定时间内执行完/timeout时间后线程池是否被关闭
         */
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("size:" + list.size());

    }
}

class ThreadPoolMethod2 {
    public static void main(String[] args) {
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);//10个可复用的线程
        ExecutorService executorService3 = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService2.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + ":" + finalI);
                } catch (InterruptedException e) {
                }
            });
            System.out.println("-----");
        }
    }

}

class ThreadPoolMethod3 {
    static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        executorService.execute(() -> {
            for (int i = 0; i < 10000; i++) {
                System.out.println("我是：" + i);
            }
        });

        System.out.println("=============");
    }
}
