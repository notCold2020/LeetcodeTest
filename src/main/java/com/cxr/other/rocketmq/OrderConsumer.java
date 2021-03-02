package com.cxr.other.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class OrderConsumer {
    public static void main(String[] args) {
        System.out.println("Id:" + Thread.currentThread().getId() + "    name:" + Thread.currentThread().getName());
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("syncConsumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("sync_topic", "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        /**
         * MessageListenerOrderly 为每个messageQUeue加锁 消费消息之前 需要先获取这个MessageQueue的锁
         * 这样同个时间 同一个Queue的消息不被并发处理 但是不同Queue的消息可以并发处理
         */
        consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
            System.out.println("received new messages: " + new String(msgs.get(0).getBody()) + " ");

            return ConsumeOrderlyStatus.SUCCESS;
        });

        /**
         * MessageListenerConcurrently
         */
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                int times = list.get(0).getReconsumeTimes();//重试次数
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
