package com.cxr.other.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class OrderConsumer {
    public static void main(String[] args) throws MQClientException {
        consumerConcurrtly();
    }

    public static void consumerOrderly() throws MQClientException {
        DefaultMQPushConsumer consumer = MqConfig.getConsumer();
        /**
         * MessageListenerOrderly 为每个messageQUeue加锁 消费消息之前 需要先获取这个MessageQueue的锁
         * 这样同个时间 同一个Queue的消息不被并发处理 但是不同Queue的消息可以并发处理
         */
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                MessageExt messageExt = list.get(0);
                String topic = messageExt.getTopic();
                String mes = new String(messageExt.getBody());
                System.out.println("received new messages：        " + "topic:" + topic + "    mes:" + mes);
                System.out.println("==========================");
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        System.out.println("#################");
        consumer.start();
    }

    public static void consumerConcurrtly() throws MQClientException {

        DefaultMQPushConsumer consumer = MqConfig.getConsumer();
        /**
         * MessageListenerConcurrently
         */
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                int times = list.get(0).getReconsumeTimes();//重试次数
                MessageExt messageExt = list.get(0);
                String topic = messageExt.getTopic();
                String mes = new String(messageExt.getBody());
                System.out.println("received new messages：        " + "topic:" + topic + "    mes:" + mes +"    times:"+times);
                System.out.println("==========================");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
