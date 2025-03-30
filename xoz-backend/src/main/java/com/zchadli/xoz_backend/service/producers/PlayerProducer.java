package com.zchadli.xoz_backend.service.producers;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public void sendPlayingWaiting(String playerId) {
        kafkaTemplate.send("player-waiting-topci", playerId);
    }

}
