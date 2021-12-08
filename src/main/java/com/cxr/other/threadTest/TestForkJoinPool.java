package com.cxr.other.threadTest;

import java.util.concurrent.*;

/**
 * 这是一个简单的Join/Fork计算过程，将1—1001数字相加
 */
public class TestForkJoinPool {

    private static final Integer MAX = 200;

    //如果不需要返回值，就用RecursiveAction
    static class MyForkJoinTask extends RecursiveTask<Integer> {
        // 子任务开始计算的值
        private Integer startValue;

        // 子任务结束计算的值
        private Integer endValue;

        public MyForkJoinTask(Integer startValue, Integer endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        /**
         * RecursiveTask类里面的方法
         * 拆分后的任务是并行执行，所以打印顺序不一定
         * <p>
         * 真正执行的逻辑在下面的compute方法里面
         * 大任务过来了，开始执行compute()方法，一看发现>MAX，走else,把大任务拆成了subTask1 subTask2，
         * 这俩任务分别再执行自己的compute()
         *
         * 问题：这工作窃取算法，比如1个大任务拆分成8个小任务，会有几个线程？
         * 这个默认的线程数是直接获取系统的(核心数-1)来的 -> 这个是common线程池
         * /usr/local/src/java/util/concurrent/ForkJoinPool.java:3435
         */
        @Override
        protected Integer compute() {
            // 如果条件成立，说明这个任务所需要计算的数值分为足够小了
            // 可以正式进行累加计算了
            if (endValue - startValue < MAX) {
                System.out.println("开始计算的部分：startValue = " + startValue + ";   endValue = " + endValue);
                Integer totalValue = 0;
                for (int index = this.startValue; index <= this.endValue; index++) {
                    totalValue += index;
                }
                return totalValue;
            }
            // 否则再进行任务拆分，拆分成两个任务
            else {
                MyForkJoinTask subTask1 = new MyForkJoinTask(startValue, (startValue + endValue) / 2);
                MyForkJoinTask subTask2 = new MyForkJoinTask((startValue + endValue) / 2 + 1, endValue);
                //并行执行两个小任务
                subTask1.fork();
                subTask2.fork();
                //把小任务的结果加起来
                return subTask1.join() + subTask2.join();
            }
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 这是Fork/Join框架的线程池
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> taskFuture = pool.submit(new MyForkJoinTask(1, 1001));
        try {
            Integer result = taskFuture.get();
            long end = System.currentTimeMillis();

            System.out.println("result = " + result + "，耗时：" + (end - start) + "ms");

            //阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
            pool.awaitTermination(2, TimeUnit.SECONDS);
            // 关闭线程池 这不也是个线程池吗
            pool.shutdown();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
        }
    }
}
