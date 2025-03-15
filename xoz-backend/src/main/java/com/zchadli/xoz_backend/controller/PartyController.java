package com.zchadli.xoz_backend.controller;


import com.zchadli.xoz_backend.dto.PartyDto;
import com.zchadli.xoz_backend.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;
    @PostMapping
    public PartyDto save() {
        return partyService.saveParty();
    }
    @GetMapping("{uid}")
    public PartyDto findParty(@PathVariable String uid) throws Exception {
        return  partyService.findParty(uid);
    }
}
