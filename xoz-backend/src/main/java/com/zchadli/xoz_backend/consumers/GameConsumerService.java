package com.zchadli.xoz_backend.consumers;

import com.zchadli.xoz_backend.constants.XoZConstants;
import com.zchadli.xoz_backend.dto.GameResultDto;
import com.zchadli.xoz_backend.dto.GameStartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class GameConsumerService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    @KafkaListener(topics = XoZConstants.GAME_TOPIC)
    public void consume(GameResultDto gameResult) {
        log.info("game "+gameResult.finished());
        simpMessagingTemplate.convertAndSend("/topic/"+XoZConstants.GAME_TOPIC, gameResult);
    }
}
