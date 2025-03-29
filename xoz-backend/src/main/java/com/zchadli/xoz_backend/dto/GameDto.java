package com.zchadli.xoz_backend.dto;

import java.util.List;

public record GameDto(Long id, List<MoveDto> moves, boolean finished, boolean current, Long idWinner, Long idCurrentPlayer) {
}