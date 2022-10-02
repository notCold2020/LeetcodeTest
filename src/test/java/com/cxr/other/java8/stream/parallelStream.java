package com.cxr.other.java8.stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: CiXingrui
 * @Create: 2021/12/1 7:58 下午
 *
 * 并行流对于list来说是并行的，但不是异步的
 */
@SpringBootTest
public class parallelStream {


    /**
     * forEach
     * forEachOrder
     * 一句话：在并行流的时候forEachOrder保证按照顺序执行
     */
    @Test
    void test0() {

        Stream.of("A", "B", "C", "D").parallel()
                .forEach(e -> System.out.println(e));

        Stream.of("A", "B", "C", "D").parallel()
                .forEachOrdered(e -> System.out.println(e));
    }

    /**
     * 使用场景
     */
    @Test
    void test1() throws InterruptedException {
        List<Integer> ids = new ArrayList<>();
        //ids 按照maxBatchCount分割好 Lists.partition
        List<List<String>> parallelList = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(parallelList.size());

        parallelList.parallelStream().forEachOrdered(parallel -> {
            //执行费事的方法

            //计数器减减，万一某一个流执行失败了呢
            countDownLatch.countDown();
        });

        //countdownLatch保证list中的每一项都执行到了
        countDownLatch.await();

    }

    /**
     * 速度判断是不是真的快了
     */
    @Test
    void test2() {

        long start = System.currentTimeMillis();
        System.out.println("开始");
        //这里boxed()的作用是int->integer，不然.collect(Collections.toList())的时候 基本数据类型会报错
        IntStream.range(0, 6).boxed().parallel().forEach(m -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("并行流结束");//!!!!并不是异步的

        long end1 = System.currentTimeMillis();

        System.out.println("并行流耗时：" + (end1 - start) + "ms");

        IntStream.range(0, 6).forEach(m -> {
            try {
//                if (m == 0) {
//                    System.out.println("发生异常");//如果发生异常后续的并不会执行
//                    int i = 1 / 0;
//                }
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end2 = System.currentTimeMillis();

        System.out.println("串行流耗时：" + (end2 - end1) + "ms");
    }


}
