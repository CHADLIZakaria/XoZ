package com.zchadli.xoz_backend.dao;

import com.zchadli.xoz_backend.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameDao extends JpaRepository<Game, Long> {
}
