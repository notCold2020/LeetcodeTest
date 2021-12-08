package com.cxr.other.strangeDemo.queue;

import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

/**
 * @Author: CiXingrui
 * @Create: 2021/11/14 1:27 下午
 */
public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        //实例化一个DelayQueue
        BlockingQueue<DelayObject<String>> DQ = new DelayQueue<>();

        //向DelayQueue添加四个元素对象，注意延时时间不同
        DQ.add(new DelayObject("A", 1000 * 10));  //延时10秒
        DQ.add(new DelayObject("B", 4000 * 10));  //延时40秒
        DQ.add(new DelayObject("C", 3000 * 10));  //延时30秒
        DQ.add(new DelayObject("D", 2000 * 10));  //延时20秒

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //将对象从DelayQueue取出，注意取出的顺序与延时时间有关
        //如果没到时间 就拿不到
        System.out.println(DQ.take());  //取出A
        System.out.println(DQ.take());  //取出D
        System.out.println(DQ.take());  //取出C
        System.out.println(DQ.take());  //取出B

    }
}
