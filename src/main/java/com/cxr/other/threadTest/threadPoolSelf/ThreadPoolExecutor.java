package com.cxr.other.threadTest.threadPoolSelf;

import lombok.Data;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Data
class ThreadPoolExecutorSelf {

    //线程池的容量
    private int poolSize = 0;

    //核心线程的数量
    private int coreSize = 0;

    //阻塞队列
    private BlockingQueue<MyTask> blockingQueue;

    //是否关闭线程池，用 volatile 保证可见性，确保线程可以及时关闭
    private volatile boolean shutdown = false;

    /**
     * @param: @param size 线程池容量
     * 初始化一下下队列
     */
    public ThreadPoolExecutorSelf(int poolsize1, int i, int poolsize, TimeUnit milliseconds, ArrayBlockingQueue<Runnable> runnables) {
        this.poolSize = poolsize;
        blockingQueue = new LinkedBlockingQueue<MyTask>();
    }

    /**
     * @param task 要添加的任务
     */
    public void execute(MyTask task) throws InterruptedException {
        //判断线程池是否关闭
        if (shutdown == true) {
            return;
        }
        int size = blockingQueue.size();
        //判空
        if (task == null) {
            throw new NullPointerException("ERROR：传入的task为空！");
        }
        if (size < coreSize) {
            addWorker(task);
        } else if (size > coreSize && size < poolSize) {
            //如果核心线程数小于线程池容量，将任务加入队列并新建核心线程
            blockingQueue.put(task);
        } else if (size > poolSize) {
//            addWorker();  拒绝策略
        }
    }

    /**
     * @description: 添加真正用于执行任务的线程
     */
    public void addWorker(MyTask task) {
        Thread thread = new Thread(new Worker());
        thread.start();
        //这不++了吗
        coreSize++;
    }

    /**
     * @description: 停止线程池
     */
    public void shutdown() {
        shutdown = true;
    }


    /**
     * @description:具体进行工作的线程
     * @author:JerryG Worker才是真正执行任务的地方 如果不是核心线程
     * 那就只加入到队列里面 如果是核心线程 那就也加入到worker来执行
     */
    class Worker implements Runnable {
        @Override
        public void run() {
            while (!shutdown) {
                try {
                    //循环从队列中取出任务并执行
                    MyTask task = blockingQueue.take();
                    task.run();
                    System.out.println("taskid = " + " 执行完毕");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
