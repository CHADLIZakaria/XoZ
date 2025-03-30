package com.zchadli.xoz_backend.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics="party-topic", groupId = "my-group")
    public void listen(String message) {
        System.out.println("received message : "+message);
    }

}
