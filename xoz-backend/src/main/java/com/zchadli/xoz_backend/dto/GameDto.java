package com.zchadli.xoz_backend.dto;

import com.zchadli.xoz_backend.model.Player;

import java.util.Set;

public record GameDto(Long id, Set<Player> players, Boolean isFinished) {

}
