package com.cxr.other.rocketmq;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class OrderConsumer {
    public static void main(String[] args) throws MQClientException {
        consumerOrderly(MqConfig.getConsumer("syncConsumerA", "topicname", "tagA"));
        consumerOrderly(MqConfig.getConsumer("syncConsumerB", "topicname", "tagB"));

    }

    public static void consumerOrderly(DefaultMQPushConsumer consumer) throws MQClientException {
        /**
         * MessageListenerOrderly 为每个messageQUeue加锁 消费消息之前 需要先获取这个MessageQueue的锁
         * 这样同个时间 同一个Queue的消息不被并发处理 但是不同Queue的消息可以并发处理
         *
         * 这个是对于MessageQueue来说的
         * 就算是设置了MessageListenerOrderly，但是Provider把消息放到8个MessageQueue 依然是每个MessageQueue开一个线程 依旧不能顺序执行
         */
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @SneakyThrows
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                MessageExt messageExt = list.get(0);
                String topic = messageExt.getTopic();
                String tags = messageExt.getTags();
                int queueId = messageExt.getQueueId();
                String mes = new String(messageExt.getBody());
                System.out.println("received new messages：        " + "topic:" + topic + "    mes:" + mes + "    tags:" + tags + "    queueId:" + queueId);
                System.out.println("==========================");
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        System.out.println("consumer启动成功");
        consumer.start();
    }

    public static void consumerConcurrtly() throws MQClientException {

        DefaultMQPushConsumer consumer = MqConfig.getConsumer();
        /**
         * MessageListenerConcurrently
         *
         * 如果是1个MessageQueue里面 有30个消息 设置了MessageListenerConcurrently 和 setConsumeMessageBatchMaxSize（5）
         * 那么 就是开启6个线程 各5个 我猜的 但是挺有道理
         */
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                int times = list.get(0).getReconsumeTimes();//重试次数
                if(times>3) System.out.println("人工介入");

                MessageExt messageExt = list.get(0);
                String topic = messageExt.getTopic();
                String mes = new String(messageExt.getBody());
                System.out.println("received new messages：        " + "topic:" + topic + "    mes:" + mes + "    times:" + times);
                System.out.println("==========================");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}


