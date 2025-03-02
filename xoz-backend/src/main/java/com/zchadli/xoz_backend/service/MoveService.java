package com.zchadli.xoz_backend.service;

import com.zchadli.xoz_backend.dto.MoveDto;
import com.zchadli.xoz_backend.model.Move;

import java.util.List;

public interface MoveService {
    Boolean saveMove(MoveDto move);
    List<Move> findByIdGameAndIdPlayer(Long idGame, Long idPlayer);
}
