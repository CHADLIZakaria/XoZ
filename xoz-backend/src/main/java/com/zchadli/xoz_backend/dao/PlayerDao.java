package com.zchadli.xoz_backend.dao;

import com.zchadli.xoz_backend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerDao extends JpaRepository<Player, Long> {
}
