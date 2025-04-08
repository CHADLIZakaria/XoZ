package com.zchadli.xoz_backend.controller;

import com.zchadli.xoz_backend.dto.*;
import com.zchadli.xoz_backend.service.RemotlyMoveService;
import com.zchadli.xoz_backend.service.RemotlyPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/remote-party")
@RequiredArgsConstructor
public class RemotlyPartyController {
    private final RemotlyPartyService remotlyPartyService;
    private final RemotlyMoveService remotlyMoveService;

    @PostMapping()
    public PartyDto saveParty() throws Exception {
        return remotlyPartyService.joinParty();
    }

    @GetMapping("/default/{uid}")
    public GameStartDto getDefaultParty(@PathVariable String uid) {
        PartyDto partyDto = remotlyPartyService.findDefaultParty(uid);
        GameStartDto gameStartDto = new GameStartDto();
        gameStartDto.setPartyUid(partyDto.uid());
        gameStartDto.setPlayers(partyDto.players().stream().map(PlayerDto::name).toList());
        return gameStartDto;
    }
    @GetMapping("{uid}")
    public PartyDto findParty(@PathVariable String uid) throws Exception {
        return remotlyPartyService.findParty(uid);
    }

    @PostMapping("/move")
    public GameResultDto addMove(@RequestBody MoveDto move) throws Exception {
        return remotlyMoveService.saveMove(move);
    }
}
