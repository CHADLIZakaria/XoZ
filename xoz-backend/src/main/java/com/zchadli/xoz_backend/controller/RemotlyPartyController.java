package com.zchadli.xoz_backend.controller;

import com.zchadli.xoz_backend.dto.GameStartDto;
import com.zchadli.xoz_backend.dto.PartyDto;
import com.zchadli.xoz_backend.dto.PlayerDto;
import com.zchadli.xoz_backend.service.RemotlyPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/remote-party")
@RequiredArgsConstructor
public class RemotlyPartyController {
    private final RemotlyPartyService remotlyPartyService;

    @PostMapping()
    public PartyDto saveParty() throws Exception {
        return remotlyPartyService.joinParty();
    }
    @GetMapping("/{uid}")
    public GameStartDto getParty(@PathVariable String uid) {
        PartyDto partyDto = remotlyPartyService.findParty(uid);
        GameStartDto gameStartDto = new GameStartDto();
        gameStartDto.setPartyUid(partyDto.uid());
        gameStartDto.setPlayers(partyDto.players().stream().map(PlayerDto::name).toList());
        return gameStartDto;
    }
}
