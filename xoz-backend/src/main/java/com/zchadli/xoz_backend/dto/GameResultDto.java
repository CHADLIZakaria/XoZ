package com.zchadli.xoz_backend.dto;

import java.util.List;

public record GameResultDto(boolean finished, List<MoveDto> movesWin) {
}
