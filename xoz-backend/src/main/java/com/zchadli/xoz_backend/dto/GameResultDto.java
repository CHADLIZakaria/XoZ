package com.zchadli.xoz_backend.dto;

import java.util.List;

public record GameResultDto(boolean isFinished, List<MoveDto> movesWin) {
}
