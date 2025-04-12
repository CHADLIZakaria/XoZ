package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.dao.GameDao;
import com.zchadli.xoz_backend.dto.GameDto;
import com.zchadli.xoz_backend.mapper.XoZMapper;
import com.zchadli.xoz_backend.model.Game;
import com.zchadli.xoz_backend.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameDao gameDao;
    private final XoZMapper xoZMapper;
    @Override
    public GameDto saveGame(Game game) {
        Game gameSaved = gameDao.save(game);
        return xoZMapper.toGameDto(gameSaved);
    }

    @Override
    public GameDto getGame(Long id) {
        return xoZMapper.toGameDto(gameDao.findById(id).orElseThrow());
    }

    @Override
    public void updateWinner(Long id, Long idWinner) throws Exception {
       Game game = gameDao.findById(id).orElseThrow(() -> new Exception("Not Found"));
       game.setIdWinner(idWinner);
       game.setFinished(true);
       gameDao.save(game);
    }

    @Override
    public GameDto updateCurrentPlayer(Long id, Long idCurrentPlayer) throws Exception {
        Game game = gameDao.findById(id).orElseThrow(() -> new Exception("Not Found"));
        game.setIdCurrentPlayer(idCurrentPlayer);
        return xoZMapper.toGameDto(gameDao.save(game));
    }

}
