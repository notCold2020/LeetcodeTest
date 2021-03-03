package com.cxr.other.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendLog(String log){
        System.out.println("向kafka中生产消息:"+log);
        kafkaTemplate.send("topic_log", log);
        System.out.println();
    }
}
