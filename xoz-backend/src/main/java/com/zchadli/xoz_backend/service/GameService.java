package com.zchadli.xoz_backend.service;

import com.zchadli.xoz_backend.dto.GameDto;
import com.zchadli.xoz_backend.model.Game;

public interface GameService {
    GameDto saveGame(Game game);
    GameDto getGame(Long id);
    void updateWinner(Long id, Long idWinner) throws Exception;
    void updateCurrentPlayer(Long id, Long idCurrentPlayer) throws Exception;
}
