package com.zchadli.xoz_backend.controller;

import com.zchadli.xoz_backend.model.Player;
import com.zchadli.xoz_backend.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    @PostMapping
    public void save(@RequestBody Player player) {
        playerService.savePlayer(player);
    }
}
