package com.zchadli.xoz_backend.dao;

import com.zchadli.xoz_backend.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyDao extends JpaRepository<Party, String> {
    Optional<Party> findByUid(String uid);
}
