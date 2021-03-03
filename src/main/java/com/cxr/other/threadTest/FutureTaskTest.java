package com.cxr.other.threadTest;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Future<T> 接口，T是返回值类型
 * Future模式可以这样来描述：
 * 我有一个任务，提交给了Future，Future替我完成这个任务。期间我自己可以去做任何想做的事情。
 * 一段时间之后，我就便可以从Future那儿取出结果。
 * todo:可以拿到异步执行结果的返回值
 *
 * 比如现在有个很复杂的操作 比如慢查询 然后我们可以用Callable接口来查询 是有返回值的 这个返回值交给Future接口(FutureTask)
 * 这个线程就异步去执行了，而Future做完操作了 我们在主线程就能异步的拿到值
 *
 */
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // FutureTask实现了Runnable，可以看做是一个任务
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "========>正在执行");
                try {
                    Thread.sleep(3 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "========>执行结束");
                return "success";
            }
        });

        //FutureTask包装runnable接口  通过构造方法
        FutureTask<String> futureTask2 = new FutureTask<>(new Runnable() {
            @Override
            public void run() {

            }
        }, "我是返回值");

        System.out.println(Thread.currentThread().getName() + "========>启动任务");

        // 传入futureTask，启动线程执行任务
        new Thread(futureTask).start();
//        Thread.sleep(1000L);

        /**
         * 这行代码和上面的start()不一定哪个先 毕竟这是多线程 仔细想一想
         */
        System.out.println("===准备获取结果===");
        /**
         * 但它同时又实现了Future，可以获取异步结果（会阻塞3秒）
         * 这个get最好不要立刻调用 不然这种异步获取返回值的优势就没有了
         * 因为如果call()方法里面的没执行完 那么get在这阻塞 变成串行化的锁了就
         *
         * 但是咱们也不知道啥时候执行完 所以有了isdone()方法
         */
        System.out.println("继续往下执行");
        if (futureTask.isDone()) {
            String result = futureTask.get();
            System.out.println("任务执行结束，result====>" + result);
        }


    }

}
