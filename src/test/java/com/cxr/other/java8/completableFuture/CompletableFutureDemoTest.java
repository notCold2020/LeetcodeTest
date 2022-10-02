package com.cxr.other.java8.completableFuture;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 * @Author: CiXingrui
 * @Create: 2021/12/1 3:14 下午
 */
@SpringBootTest
class CompletableFutureDemoTest {

    @Test
    /**
     * join和get方法的返回值的区别
     */
    public void test1() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "test1结束";
        });

        String s1 = stringCompletableFuture.getNow("getNow");//如果当前没结束 那么就直接返回getNow的值
        String s3 = stringCompletableFuture.get();
        String s4 = stringCompletableFuture.join();
        String s2 = stringCompletableFuture.get(1, TimeUnit.SECONDS);//如果到时间没结束抛出TimeoutException
    }

    @Test
    /**
     * thenApply这些都是异步的吗还是一体的？是一个线程吗
     * 1.都是异步的，但是objectCompletableFuture相对于stringCompletableFuture来讲是阻塞的，就算两者分开写也不行，
     * 要等stringCompletableFuture执行完才可以
     * 毕竟这方法的作用就是一个线程依赖另一个线程的时候使用
     * 如果我们get阻塞stringCompletableFuture，那么就是单独阻塞了stringCompletableFuture
     * 如果我们get阻塞objectCompletableFuture，那么就是阻塞了那么就是阻塞了stringCompletableFuture + objectCompletableFuture
     * 毕竟objectCompletableFuture是在stringCompletableFuture的基础上得出的
     * 2.是一个线程
     */
    public void test2() throws ExecutionException, InterruptedException, TimeoutException {

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000L);
                System.out.println("stringCompletableFuture当前线程：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "test2结束";
        });

        System.out.println("stringCompletableFuture是异步的");


        CompletableFuture<Object> objectCompletableFuture = stringCompletableFuture.thenApply(new Function<String, Object>() {
            @SneakyThrows
            @Override
            public Object apply(String s) {
                System.out.println("我是stringCompletableFuture的返回值：" + s + ",thenApply当前线程：" + Thread.currentThread().getName());
                Thread.sleep(2000L);
                return null;
            }
        });

        System.out.println("thenApply是异步的");

        objectCompletableFuture.join();
//        CompletableFuture.allOf(stringCompletableFuture, objectCompletableFuture).join();
    }

    /**
     * exceptionally的返回值 返回到哪去了
     * 返回值可以被thenAccept、thenApply接收
     */
    @Test
    void test03() {
        // 实例化一个CompletableFuture,返回值是Integer
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 返回null
            int i = 1 / 0;
            return null;
        });

        CompletableFuture<String> exceptionally = future.thenApply(result -> {
            // 制造一个空指针异常NPE
            int i = result;
            // 这里不会执行，因为上面出现了异常
            return i;
        }).thenApply(result -> {
            // 这里不会执行，因为上面出现了异常
            String words = "现在是" + result + "点钟";
            return words;
        }).exceptionally(error -> {
            // 我们选择在这里打印出异常
            error.printStackTrace();
            // 并且当异常发生的时候，我们返回一个默认的文字
            return "出错啊~";
        });

        //这里收集的是exceptionally 是最全的 链路上谁有问题都被这里输出
        exceptionally.thenAccept(System.out::println);

    }

    /**
     * whenComplete和thenApply区别
     * 没啥区别 不用管
     * 并且比如下面的例子，future3里面的改动并不会影响future2的返回结果，依旧是8
     */
    @Test
    void test04() {

        //whenComplete
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 16 / 2);
        CompletableFuture<Integer> future = future1.whenComplete((t1, e) -> {
            Assertions.assertEquals(Thread.currentThread().getName(), "main");
            Assertions.assertEquals(t1, 8);
            Assertions.assertNull(e);
            t1 = 10;
        });
        //还是8(前面的返回结果) 说明whenComplete无法改变线程的返回结果
        Assertions.assertEquals(future.join(), 8);

        //thenAccept
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 16 / 2);
        CompletableFuture<Void> future3 = future2.thenAccept(t1 -> {
            Assertions.assertEquals(Thread.currentThread().getName(), "main");
            Assertions.assertEquals(t1, 8);
            t1++;
        });
        Assertions.assertEquals(future2.join(),8);//就算++还是8

        //thenApply
        CompletableFuture<Integer> future4 = CompletableFuture.supplyAsync(() -> 16 / 2);
        CompletableFuture<Integer> future5 = future4.thenApply(t1 -> {
            Assertions.assertEquals(Thread.currentThread().getName(), "main");
            Assertions.assertEquals(t1, 8);
            return t1 * 2;
        });
        Assertions.assertEquals(future4.join(), 8);//因为有返回值 所以再thenApply里面可以操作 * 2
        System.out.println("------OK-------");
    }

    /**
     * 如果A方法是异步的执行5s，但是有异常了，那么exceptionally还能捕获吗？
     * 可以的，项目又没挂。
     * 虽然方法结束了但是项目还在，exceptionally部分也是异步的，
     */
    @Test
    void test5() throws InterruptedException {
        System.out.println("test5开始执行");
        CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = 1/0;
        }).exceptionally(new Function<Throwable, Void>() {
            @SneakyThrows
            @Override
            public Void apply(Throwable t) {
                System.out.println("执行失败！" + t.getMessage());
                return null;
            }
        });
        System.out.println("test5执行结束");

        Thread.sleep(50000L);
    }
}

