package com.zchadli.xoz_backend.consumers;

import com.zchadli.xoz_backend.constants.XoZConstants;
import com.zchadli.xoz_backend.dto.GameResultDto;
import com.zchadli.xoz_backend.dto.GameStartDto;
import com.zchadli.xoz_backend.dto.MoveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class MoveConsumerService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    @KafkaListener(topics = XoZConstants.MOVE_TOPIC)
    public void consume(List<MoveDto> moves) {
        log.info(moves.size()+" finished");
        simpMessagingTemplate.convertAndSend("/topic/"+XoZConstants.MOVE_TOPIC, moves);
    }
}
