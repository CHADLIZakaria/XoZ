package com.zchadli.xoz_backend.service.producers;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMove(String gameId, String playerId, int x, int y) {
        String moveData = gameId + "," + playerId + "," + x + "," + y;
        kafkaTemplate.send("move-topic", moveData);
        System.out.println("Move sent: " + moveData);
    }
}

