package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.dao.PartyDao;
import com.zchadli.xoz_backend.dao.PlayerDao;
import com.zchadli.xoz_backend.dto.GameDto;
import com.zchadli.xoz_backend.dto.GameResultDto;
import com.zchadli.xoz_backend.dto.GameStartDto;
import com.zchadli.xoz_backend.dto.PartyDto;
import com.zchadli.xoz_backend.mapper.XoZMapper;
import com.zchadli.xoz_backend.model.Game;
import com.zchadli.xoz_backend.model.Party;
import com.zchadli.xoz_backend.model.Player;
import com.zchadli.xoz_backend.service.GameService;
import com.zchadli.xoz_backend.service.RemotlyPartyService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    private final KafkaTemplate<String, GameStartDto> kafkaTemplate; // 🔹 Inject KafkaTemplate

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
            kafkaTemplate.send("game-start-topic", new GameStartDto(Collections.emptyList(), party.getUid()));
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

    public void notifyGameStart(Party party) {
        List<String> playerNames = party.getPlayers().stream()
                .map(Player::getName)
                .toList();
        GameStartDto gameStartDto = new GameStartDto(playerNames, party.getUid());

        System.out.println("🔵 Sending GameStart event to /party-topic for players: " + playerNames);

        kafkaTemplate.send("game-start-topic", gameStartDto);

    }
}
