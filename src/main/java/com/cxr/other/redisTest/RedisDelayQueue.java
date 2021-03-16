package com.cxr.other.redisTest;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

/**
 * redis 延时队列
 * redis 如果加锁失败，建议采用延时队列处理请求
 */
@AllArgsConstructor
public class RedisDelayQueue<T> {

    static class TaskItem<T> {
        public String id;
        public T msg;
    }


    private Jedis jedis;
    private String queueKey; //延迟队列的名称 这个队列里面只有一个消息啊

    //元素入队
    public void entreQueue(T msg) {
        TaskItem<T> task = new TaskItem<>();
        //保证唯一ID
        task.id = UUID.randomUUID().toString();
        task.msg = msg;
        String taskStr = JSON.toJSONString(task);
        //塞进延时队列，时间戳+5000 作为score  -->5s后再试
        jedis.zadd(queueKey, System.currentTimeMillis(), taskStr);
        System.out.println("生产了：" + msg);
    }

    //取最近的一条
    public void loop() {
        while (!Thread.interrupted()) {
            Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis() - 5000, 0, 1);
            //队列中没有在这个区间的值
            if (values == null || values.isEmpty()) {
                try {
                    //意思现在这个延时的区间里没消息
                    System.out.println(Thread.currentThread().getId() + ",休息一下");
                    Thread.sleep(500);//休息一下，再重试
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String result = values.iterator().next();
            //抢到了cpu 删除-->就是消费嘛
            if (jedis.zrem(queueKey, result) > 0) {
                TaskItem<T> taskObject = JSON.parseObject(result, TaskItem.class);
                //这里应该是函数，表示去处理请求,简单输出一下
                System.out.println("消费了：" + taskObject.msg);
            } else {
                System.out.println(Thread.currentThread().getId() + ",没有抢到cpu");
            }
        }
    }

    public static void main(String[] args) {

        Jedis jedis = new RedisUtil().getJedis();
        RedisDelayQueue redisDelayQueue = new RedisDelayQueue(jedis, "delayQueue");
        Thread producer = new Thread(() -> {
            for (int x = 0; x < 1000; x++) {
                redisDelayQueue.entreQueue("test" + x);
            }
        });
        Thread consumer = new Thread(() -> {
            redisDelayQueue.loop();
        });
        producer.start();
        consumer.start();
        try {
            /**
             * 1.消费者消费为什么不按照顺序
             * 2.为什么consumer.interrupt();
             *        consumer.join();       之后islive就死了？ 没有抛出异常
             * 3.为啥会有个类型转换的错误 有时候有 有时候没有
             */
            producer.join();
            Thread.sleep(6000);
            consumer.interrupt();
            System.out.println(consumer.isAlive());
            consumer.join();
            System.out.println(consumer.isAlive());
            System.out.println("结束");
        } catch (Exception e) {
            System.out.println("抛出了异常");
            e.printStackTrace();
        }
    }


}
