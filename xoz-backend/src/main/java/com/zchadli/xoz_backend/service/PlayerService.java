package com.zchadli.xoz_backend.service;

import com.zchadli.xoz_backend.dto.PlayerDto;
import com.zchadli.xoz_backend.model.Player;

import java.util.List;

public interface PlayerService {
    void savePlayer(Player player);
    List<PlayerDto> saveDefaultPlayer();
}
