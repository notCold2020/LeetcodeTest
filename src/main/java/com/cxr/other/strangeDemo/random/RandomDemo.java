package com.cxr.other.strangeDemo.random;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: CiXingrui
 * @Create: 2021/11/12 10:54 上午
 *
 * 结论：ThreadLocalRandom不能作为全局变量(不能作为多个线程共享的变量，比如私有变量但是有异步方法也不行)
 * 正确使用方式是每一次都ThreadLocalRandom.current().nextInt(xxx)
 */
public class RandomDemo {
    public static void main(String[] args) {
//        testRandom();
//        testThreadLocalRandom();
        testThreadLocalRandomWrong();
    }

    /**
     * 并且线程之间的内存是隔离的，毕竟下面的是局部变量的引用，在栈帧里面是私有的
     * 也可以理解为
     * 线程来了 就把main线程的种子先加载到自己的栈帧里面
     * new的自己没有种子 就加载main的
     * 线程池复用的 每复用一次 种子就刷新一次
     * https://www.zhihu.com/question/376227700
     */
    private static void testThreadLocalRandomWrong() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        // 共享 ThreadLocalRandom
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            /**
             * 现象：线程池的方式如果是一个线程 打印出来的随机数都是一样的 比如循环10次 打印出来1234...10 那么再次循环也是1234...10
             * 原因：主线程通过current()方法生成个种子，子线程通过nextInt()方法用种子生成一个随机数，并且把自己子线程的种子修改了
             * 第二次再循环，就用改过的种子再执行一次上面的操作（每次循环都是同一个线程，在子线程自己的栈帧里面操作，自然每次都更新了种子,就是用旧种子生成的新种子）
             */
//            service.submit(() -> {
//                System.out.println(Thread.currentThread().getName() + ":" + threadLocalRandom.nextInt(10));
//            });

            /**
             * 现象：这10个线程打印出来的随机数都是一样的
             * 原因：主线程生成种子，子线程用主线程的种子生成(自己的)随机数(在子线程的工作内存中)。
             * 第二次又new了一个线程(也可以理解为第二次请求)，但是还用的主线程的种子（因为子线程是主线程
             * new出来的，可以简单理解）所以随机数都是一样的。
             *
             */
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + ":" + threadLocalRandom.nextInt(10));
            }).start();
        }

    }

    /**
     * 用同一个种子必然生成同一个随机数，Random采用CAS乐观锁保证了并发情况下多线程不会拿到同一个种子。
     * A线程来了，获取主内存中的值A比如为10叫旧种子到线程A的工作内存，然后生成了一个新种子B为11 此时主内存V是10
     * 突然B线程来了，获取内存中的值A为10 (在工作内存中)计算新种子11 加载进(主)内存 主内存V变成了11
     * A线程比较旧种子10(工作内存) 和主内存值V 11 发现不相等 A线程吧旧的工作内存值A改为11 再次计算出B
     * <p>
     * 如果是另一种情况
     * A来了，获取内存中的值A比如为10叫旧种子，然后生成了一个新种子B 此时内存V是10
     * B来了，获取内存中的值A比如为10叫旧种子，然后生成了一个新种子B 此时内存V是10
     * 但是B没改内存呢
     * A又抢到了资源，这个时候A和V是对得上的，自然没有自旋。终究到底还是unsafe是原子性的
     * 也就是compareAndSet方法是原子性的 作用是 比较再交换并且赋值给内存
     * <p>
     * 因为是CAS的原因，所以虽然Random保证了线程安全但是并不是高并发的
     * 因为CAS本身一直自旋CPU压力就比较大（拿不到锁就一直自旋）而且CAS只能保证一个变量的线程安全
     * 所以就有了高并发下的随机数ThreadLocalRandom
     */
    private static void testRandom() {
        Random random = new Random();
        // 生成 0-9 的随机整数(点进来)
        random.nextInt(10);
    }

    /**
     * 原理：
     * 第一步：用调用ThreadLocalRandom.current();的线程为基础生成个种子，
     * 第二步：第二次再用第一步的种子为基础再生成一个种子
     */
    private static void testThreadLocalRandom() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        // 生成 0-9 的随机数(点进来)
        random.nextInt(10);
    }
}
