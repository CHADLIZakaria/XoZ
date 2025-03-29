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
    @GetMapping("{uid}/restart")
    public PartyDto restartGame(@PathVariable String uid) throws Exception {
        return  partyService.restartGame(uid);
    }
    @GetMapping("{uid}/reset")
    public PartyDto resetGame(@PathVariable String uid) throws Exception {
        return partyService.resetGame(uid);
    }
    @DeleteMapping("{uid}")
    public void deleteParty(@PathVariable String uid) throws Exception {
        partyService.deleteParty(uid);
    }

}
