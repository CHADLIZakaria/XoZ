package com.zchadli.xoz_backend.service.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MoveConsumer {
    @KafkaListener(topics = "move-topic", groupId = "game-group")
    public void receiveMove(String moveData) {
        String[] data = moveData.split(",");
        String gameId = data[0];
        String playerId = data[1];
        int x = Integer.parseInt(data[2]);
        int y = Integer.parseInt(data[3]);

        System.out.println("Received move: Player " + playerId + " played at (" + x + "," + y + ")");

        // Update game state here...
    }
}

