package com.cxr.other.threadTest;

import cn.hutool.core.thread.NamedThreadFactory;

import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: CiXingrui
 * @Create: 2021/12/3 3:12 下午
 */
public class ThreadPoolDynamicChange {

    public static void main(String[] args) throws InterruptedException {
        testThreadPoolDynamicChange();
    }

    /**
     * 自定义线程池
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor() {
        return new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new NamedThreadFactory("自定义线程池", true));//守护线程
    }


    private static void testThreadPoolDynamicChange() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = buildThreadPoolExecutor();

        IntStream.range(0, 15).boxed().forEach(m -> threadPoolExecutor.execute(() -> {
            //打印创建的线程池
            printThreadPoolStatus(threadPoolExecutor, "创建任务");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        System.out.println("---修改参数---");
//        printThreadPoolStatus(threadPoolExecutor, "改变之前");
        threadPoolExecutor.setCorePoolSize(10);
        threadPoolExecutor.setMaximumPoolSize(10);
//        printThreadPoolStatus(threadPoolExecutor, "改变之后");

        Thread.currentThread().join();//点进去看源码就是阻塞状态wait
    }


    /**
     * 打印线程池信息信息
     */
    private static void printThreadPoolStatus(ThreadPoolExecutor executor, String name) {
        //如果这里是自定义的队列，那么就能setQueue了
        LinkedBlockingQueue queue = (LinkedBlockingQueue) executor.getQueue();
        System.out.println(LocalDateTime.now() +
                Thread.currentThread().getName() + "-" + name + "-:  " +
                " 核心线程数：" + executor.getCorePoolSize() +
                " ,活动线程数：" + executor.getActiveCount() +
                " ,最大线程数：" + executor.getMaximumPoolSize() +
                " ,任务完成数：" + executor.getCompletedTaskCount() +
                " ,队列大小：" + (queue.size() + queue.remainingCapacity()) +
                " ,当前排队线程数(队列里面的任务数)：" + queue.size() +
                " ,队列剩余大小：" + queue.remainingCapacity()
        );
    }

}
