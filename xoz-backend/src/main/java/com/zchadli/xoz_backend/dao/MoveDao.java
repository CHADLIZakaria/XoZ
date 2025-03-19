package com.zchadli.xoz_backend.dao;

import com.zchadli.xoz_backend.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MoveDao extends JpaRepository<Move, Long> {
    Set<Move> findByGameIdAndPlayerId(Long idGame, Long idPlayer);
    Set<Move> findByGameId(Long idGame);
}
