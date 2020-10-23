package com.lrw.ohter.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

public class OrderConsumer1 {
    public static void main(String[] args) {
        System.out.println("/**\n" +
                " * An allocate strategy proxy for based on machine room nearside priority. An actual allocate strategy can be\n" +
                " * specified.\n" +
                " *\n" +
                " * If any consumer is alive in a machine room, the message queue of the broker which is deployed in the same machine\n" +
                " * should only be allocated to those. Otherwise, those message queues can be shared along all consumers since there are\n" +
                " * no alive consumer to monopolize them.\n" +
                " */".replaceAll("\\*",""));
    }
}
