package com.zchadli.xoz_backend.controller;

import com.zchadli.xoz_backend.dto.GameResultDto;
import com.zchadli.xoz_backend.dto.MoveDto;
import com.zchadli.xoz_backend.service.MoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/move")
@RequiredArgsConstructor
public class MoveController {
    private final MoveService moveService;
    @PostMapping
    public GameResultDto save(@RequestBody MoveDto move) {
        return moveService.saveMove(move);
    }
}
