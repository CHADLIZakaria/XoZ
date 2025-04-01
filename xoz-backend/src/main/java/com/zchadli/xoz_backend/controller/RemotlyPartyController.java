package com.zchadli.xoz_backend.controller;

import com.zchadli.xoz_backend.dto.PartyDto;
import com.zchadli.xoz_backend.service.RemotlyPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remote-party")
@RequiredArgsConstructor
public class RemotlyPartyController {
    private final RemotlyPartyService remotlyPartyService;

    @PostMapping()
    public PartyDto saveParty() throws Exception {
        return remotlyPartyService.joinParty();
    }
}
