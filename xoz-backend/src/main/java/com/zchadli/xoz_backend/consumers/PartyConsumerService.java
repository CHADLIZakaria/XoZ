package com.zchadli.xoz_backend.consumers;

import com.zchadli.xoz_backend.constants.XoZConstants;
import com.zchadli.xoz_backend.dto.GameStartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class PartyConsumerService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    @KafkaListener(topics = XoZConstants.PARTY_TOPIC)
    public void consume(GameStartDto gameStartDto) {
        log.info(gameStartDto.partyUid()+" start");
        simpMessagingTemplate.convertAndSend("/topic/"+XoZConstants.PARTY_TOPIC, gameStartDto);
    }
}
