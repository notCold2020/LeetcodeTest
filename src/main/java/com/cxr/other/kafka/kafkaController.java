package com.cxr.other.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class kafkaController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @RequestMapping("/testt/{log}")
    public void terst(@PathVariable String log) {
        kafkaProducer.sendLog(log);
        System.out.println("========" + log);
    }

}
