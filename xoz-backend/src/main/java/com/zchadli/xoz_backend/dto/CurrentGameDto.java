package com.zchadli.xoz_backend.dto;

import java.util.Set;

public record CurrentGameDto(Long id, Set<PlayerDto> players, Set<MoveDto> moves, boolean finished, boolean current, GameResultDto gameResult) {
}
