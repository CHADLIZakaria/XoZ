package com.zchadli.xoz_backend.controller;

import com.zchadli.xoz_backend.model.Game;
import com.zchadli.xoz_backend.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    @PostMapping
    public void save(@RequestBody Game game) {
        gameService.saveGame(game);
    }
}
