package com.zchadli.xoz_backend.service.producers;

import com.zchadli.xoz_backend.dto.GameStartDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, GameStartDto> kafkaTemplate;
    private static final String TOPIC = "party-topic";

    public KafkaProducerService(KafkaTemplate<String, GameStartDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(GameStartDto gameStart) {
        kafkaTemplate.send(TOPIC, gameStart);
    }
}
