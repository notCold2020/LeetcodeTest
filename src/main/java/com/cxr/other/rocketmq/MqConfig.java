package com.cxr.other.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

public class MqConfig {
    public static DefaultMQProducer getProduct() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("syncProducer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setRetryTimesWhenSendFailed(2);
        producer.start();
        return producer;
    }

    public static DefaultMQPushConsumer getConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("syncConsumer");
        /**
         *  批量消费的大小，也就是说当MQ消息从Broker拉取之后每次派发给我们每个消费类的大小，
         *  consumeMessage(List<MessageExt> list) -->指这个list的Size 这不就是批量消费的个数吗
         *  当消息堆积的时候 可以修改这个值
         *  如果不设置 默认是1 其他的消费不过来的（比如从broker拉取了30个消息 一次就能消费5个 其他的就会提交线程来处理 concurrently!）
         *  https://blog.csdn.net/cliuyang/article/details/109221653 : 辨析
         */
        consumer.setConsumeMessageBatchMaxSize(5);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //设置consumer所订阅的Topic和Tag，*代表全部的Tag
        consumer.subscribe("topicname", "*");
        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，跳过历史消息
        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        return consumer;
    }

    public static DefaultMQPushConsumer getConsumer(String group, String topic, String tag) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setConsumeMessageBatchMaxSize(5);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //设置consumer所订阅的Topic和Tag，*代表全部的Tag
        consumer.subscribe(topic, tag);
        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，跳过历史消息
        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        return consumer;
    }
}
