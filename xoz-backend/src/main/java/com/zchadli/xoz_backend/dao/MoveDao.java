package com.zchadli.xoz_backend.dao;

import com.zchadli.xoz_backend.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveDao extends JpaRepository<Move, Long> {
}
