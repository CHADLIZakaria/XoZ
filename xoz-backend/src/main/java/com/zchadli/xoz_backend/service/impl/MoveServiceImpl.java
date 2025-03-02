package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.dao.MoveDao;
import com.zchadli.xoz_backend.dto.MoveDto;
import com.zchadli.xoz_backend.mapper.XoZMapper;
import com.zchadli.xoz_backend.model.Game;
import com.zchadli.xoz_backend.model.Move;
import com.zchadli.xoz_backend.service.MoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoveServiceImpl implements MoveService  {
    private final MoveDao moveDao;
    private final XoZMapper xoZMapper;
    @Override
    public Boolean saveMove(MoveDto moveDto) {
        Move move = xoZMapper.toMove(moveDto);
        Game game = move.getGame();
        if(!game.isFinished()) {
            moveDao.save(move);
            List<Move> test = moveDao.findByGameIdAndPlayerId(move.getGame().getId(), move.getPlayer().getId());
            List<Move> movesPlayer = moveDao.findByGameIdAndPlayerId(move.getGame().getId(), move.getPlayer().getId());
            return isRowWin(movesPlayer) || isColumnWin(movesPlayer) || isDiagonalWin(movesPlayer);
        }
        return null;
    }

    @Override
    public List<Move> findByIdGameAndIdPlayer(Long idGame, Long idPlayer) {
        return null;
    }


    private boolean isRowWin(List<Move> movePlayer) {
        return movePlayer.stream().collect(Collectors.groupingBy(Move::getX, Collectors.counting())).values().stream().anyMatch(count -> count == 3);
    }
    private boolean isColumnWin(List<Move> movePlayer) {
        return movePlayer.stream().collect(Collectors.groupingBy(Move::getY, Collectors.counting())).values().stream().anyMatch(count -> count == 3);
    }
    private boolean isDiagonalWin(List<Move> movePlayer) {
        boolean diagonal1 = movePlayer.stream().filter(move -> Objects.equals(move.getX(), move.getY())).toList().size()==3;
        boolean diagonal2 = movePlayer.stream().filter(move -> move.getX() + move.getY() == 2).toList().size()==3;
        return diagonal1 || diagonal2;
    }
}
