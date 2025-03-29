package com.zchadli.xoz_backend.dao;

import com.zchadli.xoz_backend.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveDao extends JpaRepository<Move, Long> {
    List<Move> findByGameIdAndPlayerId(Long idGame, Long idPlayer);
    List<Move> findByGameId(Long idGame);
    void deleteALlByIdIn(List<Long> movesId);
}
