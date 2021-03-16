package com.cxr.other.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
//@Component
public class KafkaMessageListener implements AcknowledgingMessageListener<String, String> {

    static Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    @Override
    public void onMessage(ConsumerRecord<String, String> message, Acknowledgment acknowledgment) {
        String topic = message.topic();
        String msg = message.value();
        //TODO 这里具体实现个人业务逻辑
        // 最后 调用acknowledgment的ack方法，提交offset
        acknowledgment.acknowledge();

    }
}
