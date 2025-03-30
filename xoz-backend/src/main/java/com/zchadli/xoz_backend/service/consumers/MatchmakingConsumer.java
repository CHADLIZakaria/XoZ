package com.zchadli.xoz_backend.service.consumers;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatchmakingConsumer {
    private final Map<String, String> waitingPlayers = new HashMap<>();
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "player-waiting-topic", groupId = "matchmaking-group")
    public void matchPlayers(String playerId) {
        if (waitingPlayers.isEmpty()) {
            waitingPlayers.put(playerId, "waiting");
            System.out.println("Player " + playerId + " is waiting...");
        } else {
            String opponentId = waitingPlayers.keySet().iterator().next();
            waitingPlayers.remove(opponentId);
            System.out.println("Matched: " + playerId + " vs " + opponentId);
            startGame(playerId, opponentId);
        }
    }
    private void startGame(String player1, String player2) {
        System.out.println("Game started: " + player1 + " vs " + player2);
        kafkaTemplate.send("game-start-topic", player1 + "," + player2);
    }
}

