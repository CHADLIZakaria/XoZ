package com.zchadli.xoz_backend.controller;

import com.zchadli.xoz_backend.model.Move;
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
    private MoveService moveService;
    @PostMapping
    public void save(@RequestBody Move move) {
        moveService.saveMove(move);
    }
}
