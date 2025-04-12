package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.constants.XoZConstants;
import com.zchadli.xoz_backend.dao.GameDao;
import com.zchadli.xoz_backend.dao.MoveDao;
import com.zchadli.xoz_backend.dao.PartyDao;
import com.zchadli.xoz_backend.dto.GameDto;
import com.zchadli.xoz_backend.dto.GameResultDto;
import com.zchadli.xoz_backend.dto.MoveDto;
import com.zchadli.xoz_backend.mapper.XoZMapper;
import com.zchadli.xoz_backend.model.Game;
import com.zchadli.xoz_backend.model.Move;
import com.zchadli.xoz_backend.service.GameService;
import com.zchadli.xoz_backend.service.MoveService;
import com.zchadli.xoz_backend.service.RemotlyMoveService;
import jakarta.transaction.Transactional;
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
    private final GameDao gameDao;
    private final MoveDao moveDao;
    private final PartyDao partyDao;
    private final MoveService moveService;
    private final KafkaTemplate<String, List<MoveDto>> kafkaTemplate;
    @Override
    public GameResultDto saveMove(MoveDto moveDto) throws Exception {
        Move move = xoZMapper.toMove(moveDto);
        Game gameDto = gameDao.findById(move.getGame().getId()).orElseThrow();
        if(gameDto.isFinished()) {
            return new GameResultDto(true, Collections.emptyList());
        }
        gameDto.addMove(move);
        //Check Draw
        if(gameDto.getMoves().size()==9) {
            gameDto.setFinished(true);
            moveDao.save(move);
            return new GameResultDto(true, Collections.emptyList());
        }
        gameDto.setIdCurrentPlayer(moveDto.id_next_player());
        List<Move> movesPlayer =  gameDto.getMoves().stream().filter(movePlayer -> Objects.equals(movePlayer.getPlayer().getId(), moveDto.id_player())).toList();
        GameResultDto gameResultDto = getGameResult(movesPlayer);
        if(gameResultDto.finished() && !gameResultDto.movesWin().isEmpty()) {
            gameDto.setIdWinner(gameResultDto.movesWin().get(0).id_player());
            gameDto.setFinished(true);
            moveDao.save(move);
            kafkaTemplate.send(XoZConstants.MOVE_TOPIC, xoZMapper.toMovesDto(gameDto.getMoves()));
        }
        else {
            moveDao.save(move);
            kafkaTemplate.send(XoZConstants.MOVE_TOPIC, xoZMapper.toMovesDto(gameDto.getMoves()));
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
