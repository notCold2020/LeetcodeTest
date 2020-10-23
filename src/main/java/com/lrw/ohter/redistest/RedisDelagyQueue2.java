package com.lrw.ohter.redistest;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class RedisDelagyQueue2<T> {

    Logger logger = LoggerFactory.getLogger(RedisDelagyQueue2.class);

    public RedisDelagyQueue2(Jedis jedis, String queueName) {
        this.jedis = jedis;
        this.queueName = queueName;
    }

    static class TaskItem<T> {
        public String id;
        public T msg;
    }

    private Jedis jedis;
    private String queueName;//延迟队列的名称

    public void entryQueue(T msg) {
        TaskItem<T> tTaskItem = new TaskItem<>();
        tTaskItem.id = UUID.randomUUID().toString();
        tTaskItem.msg = msg;
        //这个5000是 5s后 才能尝试取出这条消息 毕竟取的时候是按照当前的毫秒值来取的  存进去的都是json
        jedis.zadd(queueName, System.currentTimeMillis() + 5000, JSON.toJSONString(tTaskItem));
    }

    public void popQueue() {
        while (!Thread.interrupted()) {
            //消费第一条
            Set<String> set = jedis.zrangeByScore(queueName, 0, System.currentTimeMillis(), 0, 1);

            if (set == null && set.isEmpty()) {
                logger.info("空了 睡一会");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                continue;
            }

            String next = set.iterator().next();
            //开始消费
            if (jedis.zrem(queueName, next) > 0) {
                //消费成功
                TaskItem<String> taskItem = JSON.parseObject(next, TaskItem.class);
                //这里应该是业务逻辑
                logger.info(Thread.currentThread().getId() + "------取出：" + taskItem.msg);

            } else {
                logger.info(Thread.currentThread().getId() + "------" + "没有抢到cpu");
            }

        }

    }

    public static void main(String[] args) {
        Jedis jedis = new RedisUtil().getJedis();
        RedisDelagyQueue2 redisDelayQueue = new RedisDelagyQueue2(jedis,"queueue");
        Thread producer = new Thread(() -> {
            for (int x = 0; x < 100000; x++) {
                redisDelayQueue.entryQueue("test" + x);
            }
        });
        Thread consumer = new Thread(() -> {
            redisDelayQueue.popQueue();
        });
        producer.start();
        consumer.start();
        jedis.close();
    }


}
