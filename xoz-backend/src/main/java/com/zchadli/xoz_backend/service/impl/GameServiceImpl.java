package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.dao.GameDao;
import com.zchadli.xoz_backend.dao.PlayerDao;
import com.zchadli.xoz_backend.dto.GameDto;
import com.zchadli.xoz_backend.mapper.XoZMapper;
import com.zchadli.xoz_backend.model.Game;
import com.zchadli.xoz_backend.model.Player;
import com.zchadli.xoz_backend.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameDao gameDao;
    private final PlayerDao playerDao;
    private final XoZMapper xoZMapper;
    @Override
    public GameDto saveGame(Game game) {
        List<Player> players = playerDao.findAll();
        game.setPlayers(new HashSet<>(players));
        Game gameSaved = gameDao.save(game);
        return xoZMapper.toGameDto(gameSaved);
    }

    @Override
    public GameDto getGame(Long id) {
        return xoZMapper.toGameDto(gameDao.findById(id).orElseThrow());
    }

}
