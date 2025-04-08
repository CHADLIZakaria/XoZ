package com.zchadli.xoz_backend.service;

import com.zchadli.xoz_backend.dto.GameResultDto;
import com.zchadli.xoz_backend.dto.MoveDto;
import com.zchadli.xoz_backend.dto.PartyDto;
import com.zchadli.xoz_backend.model.Move;

import java.util.List;

public interface RemotlyMoveService {
    GameResultDto saveMove(MoveDto move) throws Exception;
    GameResultDto getGameResult(List<Move> movesPlayer);
    void deleteAllByGameId(List<Long> movesId);
}
