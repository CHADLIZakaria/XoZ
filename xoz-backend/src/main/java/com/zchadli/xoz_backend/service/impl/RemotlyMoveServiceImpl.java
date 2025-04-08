package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.constants.XoZConstants;
import com.zchadli.xoz_backend.dao.MoveDao;
import com.zchadli.xoz_backend.dao.PartyDao;
import com.zchadli.xoz_backend.dto.GameDto;
import com.zchadli.xoz_backend.dto.GameResultDto;
import com.zchadli.xoz_backend.dto.MoveDto;
import com.zchadli.xoz_backend.mapper.XoZMapper;
import com.zchadli.xoz_backend.model.Move;
import com.zchadli.xoz_backend.service.GameService;
import com.zchadli.xoz_backend.service.MoveService;
import com.zchadli.xoz_backend.service.RemotlyMoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemotlyMoveServiceImpl implements RemotlyMoveService {
    private final XoZMapper xoZMapper;
    private final GameService gameService;
    private final MoveDao moveDao;
    private final PartyDao partyDao;
    private final MoveService moveService;
    private final KafkaTemplate<String, List<MoveDto>> kafkaTemplate;
    @Override
    public GameResultDto saveMove(MoveDto moveDto) throws Exception {
        Move move = xoZMapper.toMove(moveDto);
        GameDto gameDto = gameService.getGame(move.getGame().getId());
        if(gameDto.finished()) {
            return null;
        }
        moveDao.save(move);
        //Check Draw
        if(moveDao.findByGameId(gameDto.id()).size()==9) {
            gameService.updateWinner(gameDto.id(),null);
            return new GameResultDto(true, Collections.emptyList());
        }
        gameService.updateCurrentPlayer(gameDto.id(), moveDto.id_next_player());
        List<Move> movesPlayer = moveDao.findByGameIdAndPlayerId(move.getGame().getId(), move.getPlayer().getId());
        GameResultDto gameResultDto = getGameResult(movesPlayer);
        if(gameResultDto.finished() && !gameResultDto.movesWin().isEmpty()) {
            gameService.updateWinner(gameDto.id(), gameResultDto.movesWin().get(0).id_player());
        }
        else {
            kafkaTemplate.send(XoZConstants.MOVE_TOPIC, gameDto.moves());
        }
        return gameResultDto;
    }

    @Override
    public GameResultDto getGameResult(List<Move> movesPlayer) {
        GameResultDto gameResultDto = rowWin(movesPlayer);
        if(gameResultDto.finished()) {
            return gameResultDto;
        }
        gameResultDto = columnWin(movesPlayer);
        if(gameResultDto.finished()) {
            return gameResultDto;
        }
        gameResultDto = diagonalWin(movesPlayer);
        if(gameResultDto.finished()) {
            return gameResultDto;
        }
        return draw(movesPlayer);
    }

    @Override
    public void deleteAllByGameId(List<Long> movesId) {
        moveDao.deleteALlByIdIn(movesId);
    }

    private GameResultDto rowWin(List<Move> movePlayer) {
        Map<Integer, Long> countRow = movePlayer.stream().collect(Collectors.groupingBy(Move::getX, Collectors.counting()));
        for(Map.Entry<Integer, Long> entry: countRow.entrySet()) {
            if(entry.getValue()==3L) {
                Integer x = entry.getKey();
                return new GameResultDto(true, movePlayer.stream().filter(move -> Objects.equals(move.getX(), x)).map(xoZMapper::toMoveDto).toList());
            }
        }
        return new GameResultDto(false, Collections.emptyList());
    }
    private GameResultDto columnWin(List<Move> movePlayer) {
        Map<Integer, Long> countRow = movePlayer.stream().collect(Collectors.groupingBy(Move::getY, Collectors.counting()));
        for(Map.Entry<Integer, Long> entry: countRow.entrySet()) {
            if(entry.getValue()==3L) {
                Integer y = entry.getKey();
                return new GameResultDto(true, movePlayer.stream().filter(move -> Objects.equals(move.getY(), y)).map(xoZMapper::toMoveDto).toList());
            }
        }
        return new GameResultDto(false, Collections.emptyList());
    }
    private GameResultDto diagonalWin(List<Move> movePlayer) {
        boolean diagonal1 = movePlayer.stream().filter(move -> Objects.equals(move.getX(), move.getY())).toList().size()==3;
        boolean diagonal2 = movePlayer.stream().filter(move -> move.getX() + move.getY() == 2).toList().size()==3;
        if(diagonal1) {
            return new GameResultDto(true, movePlayer.stream().filter(move -> Objects.equals(move.getX(), move.getY())).map(xoZMapper::toMoveDto).toList());
        }
        if (diagonal2) {
            return new GameResultDto(true, movePlayer.stream().filter(move -> move.getX() + move.getY() == 2).map(xoZMapper::toMoveDto).toList());
        }
        return new GameResultDto(false, Collections.emptyList());
    }
    private GameResultDto draw(List<Move> movePlayer) {
        return new GameResultDto(movePlayer.size()==9, Collections.emptyList());
    }
}
