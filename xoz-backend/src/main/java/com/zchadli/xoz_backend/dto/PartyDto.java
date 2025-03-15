package com.zchadli.xoz_backend.dto;

import java.util.List;

public record PartyDto(Long id, String uid, CurrentGameDto currentGame, List<GameDto> history) {
}
