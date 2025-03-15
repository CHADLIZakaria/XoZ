package com.zchadli.xoz_backend.dto;

import java.util.Set;

public record GameDto(Long id, Set<PlayerDto> players, Set<MoveDto> moves, boolean finished, boolean current) {
}