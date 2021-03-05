package com.cxr.other.rocketmq.TransactionMQ;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 使用RocketMQ事务消息——商品服务接收下单的事务消息,如果消息成功commit则本地减库存
 */
public class ProductService {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumerGroup("syncConsumer");
        consumer.subscribe("topicname", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                Optional.ofNullable(msgs).orElse(Collections.emptyList()).forEach(m -> {
                    String orderId = m.getKeys();
                    System.err.println("监听到下单消息,orderId: " + orderId + ", 商品服务减库存");
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.err.println("商品服务启动");
    }
}
