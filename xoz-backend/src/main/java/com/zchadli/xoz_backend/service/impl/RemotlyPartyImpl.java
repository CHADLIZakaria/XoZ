package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.constants.XoZConstants;
import com.zchadli.xoz_backend.dao.PartyDao;
import com.zchadli.xoz_backend.dao.PlayerDao;
import com.zchadli.xoz_backend.dto.GameDto;
import com.zchadli.xoz_backend.dto.GameResultDto;
import com.zchadli.xoz_backend.dto.GameStartDto;
import com.zchadli.xoz_backend.dto.PartyDto;
import com.zchadli.xoz_backend.exception.CurrentGameNotFoundException;
import com.zchadli.xoz_backend.exception.PartyNotFoundException;
import com.zchadli.xoz_backend.mapper.XoZMapper;
import com.zchadli.xoz_backend.model.Game;
import com.zchadli.xoz_backend.model.Party;
import com.zchadli.xoz_backend.model.Player;
import com.zchadli.xoz_backend.service.GameService;
import com.zchadli.xoz_backend.service.MoveService;
import com.zchadli.xoz_backend.service.RemotlyPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RemotlyPartyImpl implements RemotlyPartyService {
    private final PartyDao partyDao;
    private final PlayerDao playerDao;
    private final GameService gameService;
    private final XoZMapper xoZMapper;
    private final MoveService moveService;
    private final KafkaTemplate<String, GameStartDto> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public PartyDto joinParty() throws Exception {
        Optional<Party> partyExist = partyDao.findAll().stream().filter(party -> party.getPlayers().size() == 1).findFirst();
        if (partyExist.isPresent()) {
            Party party = partyExist.get();
            Game game = new Game();
            game.setParty(party);
            game.setCurrent(true);
            Player player = playerDao.findById(2L).orElseThrow(() -> new Exception("Player Not Found"));
            party.getPlayers().add(player);
            game.setIdCurrentPlayer(party.getPlayers().get(0).getId());
            GameDto gameDto = gameService.saveGame(game);
            notifyGameStart(party);
            return xoZMapper.toPartyDto(party, gameDto, new GameResultDto(false, Collections.emptyList()));
        } else {
            Party party = createParty();
            notifyGameStart(party);
            return xoZMapper.toPartyDto(party, null, new GameResultDto(false, Collections.emptyList()));
        }
    }

    @Override
    public Party createParty() throws Exception {
        Party party = new Party();
        Player player = playerDao.findById(1L).orElseThrow(() -> new Exception("Player Not Found"));
        party.setPlayers(List.of(player));
        return partyDao.save(party);
    }
    @Override
    public PartyDto findParty(String uid) {
        Party party = partyDao.findByUid(uid).orElseThrow(() -> new PartyNotFoundException("Party Not Found with UID: "+uid));
        return xoZMapper.toPartyDto(party, null,  new GameResultDto(false, Collections.emptyList()));
    }

    public void notifyGameStart(Party party) {
        List<String> playerNames = party.getPlayers().stream()
                .map(Player::getName)
                .toList();
        GameStartDto gameStartDto = new GameStartDto(playerNames, party.getUid());
        kafkaTemplate.send(XoZConstants.PARTY_TOPIC, gameStartDto);
    }
}
