package com.zchadli.xoz_backend.service;

import com.zchadli.xoz_backend.dto.PartyDto;

public interface PartyService {
    PartyDto saveParty();
    PartyDto findParty(String uid) throws Exception;
}
