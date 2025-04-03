package com.zchadli.xoz_backend.service.producers;

import com.zchadli.xoz_backend.dto.GameStartDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final SimpMessagingTemplate messagingTemplate;

    public KafkaConsumerService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "party-topic", groupId = "my-group")
    public void consumeMessage(GameStartDto gameStartDto) {
        System.out.println("Sending "+gameStartDto.getPartyUid());
        messagingTemplate.convertAndSend("/topic/party-topic", gameStartDto);
    }
}
