package com.cxr.other.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(groupId = "test",topics = "topic_log")
    public void listen(ConsumerRecord<?, ?> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        try {
            String logStr = (String) record.value();
            acknowledgment.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("kafka消息消费失败", e);
        }
    }
}
