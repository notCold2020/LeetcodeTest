package com.cxr.other.threadTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Thread4Method {

    static class MyThread1 extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }

    static class MyThread2 implements Runnable {
        @Override
        public void run() {

        }
    }

    static class MyThread3 implements Callable<List<Integer>> {

        @Override
        public List<Integer> call() throws Exception {
            List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
            return list;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.extends Thread类
        MyThread1 myThread1 = new MyThread1();
//        myThread1.start();

        //2.实现runnable接口
        MyThread2 myThread2 = new MyThread2();
        Thread thread2 = new Thread(myThread2);
//        thread2.start();
        //lambda写法
        Thread threadLmabda = new Thread(() -> {
            System.out.println("jdk8函数式编程");
        });

        //3.Callable接口配置FutureTask包装器
        MyThread3 myThread3 = new MyThread3();
        FutureTask<List<Integer>> futureTask = new FutureTask<>(myThread3);
        //开启线程
        new Thread(futureTask).start();
        //获取返回值 这个get方法是阻塞的
        List<Integer> list = futureTask.get();

        //4.线程池



    }


}
