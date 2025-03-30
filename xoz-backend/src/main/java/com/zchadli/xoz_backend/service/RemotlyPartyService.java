package com.zchadli.xoz_backend.service;

import com.zchadli.xoz_backend.dto.PartyDto;
import com.zchadli.xoz_backend.model.Party;

public interface RemotlyPartyService {
    PartyDto joinParty() throws Exception;
    Party createParty() throws Exception;
}
