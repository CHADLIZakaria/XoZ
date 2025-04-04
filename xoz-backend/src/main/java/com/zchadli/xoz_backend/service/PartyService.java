package com.zchadli.xoz_backend.service;

import com.zchadli.xoz_backend.dto.PartyDto;

public interface PartyService {
    PartyDto saveParty();
    PartyDto findParty(String uid);
    PartyDto restartGame(String uid) throws Exception;
    PartyDto resetGame(String uid) throws Exception;
    void deleteParty(String Uid);

}
