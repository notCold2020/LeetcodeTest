package com.cxr.other.threadTest;

import java.util.concurrent.*;

public class ExceptionTest implements ThreadFactory {
    /**
     * 线程不允许抛出未捕获的checked exception（比如sleep时的InterruptedException）
     * 也就是说各个线程需要自己把自己的checked exception处理掉。
     * test01就不行 test02就可以
     */
    public static void test01() {
        try {
            new Thread(() -> {
                int i = 1 / 0;
            }).start();
        } catch (Exception e) {
            System.out.println("捕获了1/0");
        }
    }

    public static void test02() {
        new Thread(() -> {
            try {
                int i = 1 / 0;
            } catch (Exception e) {
                System.out.println("捕获了1/0");
            }
        }).start();
    }

    /**
     * 2.未捕获的异常哪去了？
     * 被JVM uncaughtException() 捕获 并打印到控制台上
     */


    public static void test03() {
        Thread thread = new Thread(() -> {
            int i = 1 / 0;//发生异常
        });
        //自定义未捕获异常处理器
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("尝试捕获线程抛出的异常！");
            }
        });
        thread.start();
    }

    /**
     * 线程池里面的线程都是ThreadFactory创建的，
     * 所以继承ThreadFactory 实现newThread方法
     * 再调用setUncaughtExceptionHandler();
     * 核心就是调用uncaughtException()方法
     */
    public static void test04() {
        ExecutorService executorService = Executors.newSingleThreadExecutor(new ExceptionTest());

        executorService.execute(() -> {
            int i = 1 / 0;
        });
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        //自定义UncaughtExceptionHandler
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("test04::尝试捕获线程抛出的异常:" + e.getMessage());
            }
        });
        return t;
    }

    public static void test05() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor(new ExceptionTest());
        Future<Integer> submit = null;

        try {
            //这样也是拿不到的
            submit = executorService.submit(() -> {
                int i = 1 / 0;
                return i;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //异常被吞了 不调用get方法不报异常
        try {
            System.out.println(submit.get() + "---test05");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("d");
        }
    }

    /**
     * 线程池exetute可以 主线程可以直接捕获
     */
    public static void test06() {
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(() -> {
                int i = 1 / 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test06();
    }
}
