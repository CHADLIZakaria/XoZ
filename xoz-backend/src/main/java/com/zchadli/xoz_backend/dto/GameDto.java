package com.zchadli.xoz_backend.dto;

import java.util.Set;

public record GameDto(Long id, Set<MoveDto> moves, boolean finished, boolean current, Long idWinner, Long idCurrentPlayer) {
}