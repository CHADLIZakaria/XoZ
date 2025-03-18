package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.dao.PartyDao;
import com.zchadli.xoz_backend.dao.PlayerDao;
import com.zchadli.xoz_backend.dto.GameDto;
import com.zchadli.xoz_backend.dto.GameResultDto;
import com.zchadli.xoz_backend.dto.PartyDto;
import com.zchadli.xoz_backend.mapper.XoZMapper;
import com.zchadli.xoz_backend.model.Game;
import com.zchadli.xoz_backend.model.Party;
import com.zchadli.xoz_backend.model.Player;
import com.zchadli.xoz_backend.service.GameService;
import com.zchadli.xoz_backend.service.MoveService;
import com.zchadli.xoz_backend.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyServiceImpl implements PartyService  {
    private final PartyDao partyDao;
    private final XoZMapper xoZMapper;
    private final GameService gameService;
    private final MoveService moveService;
    private final PlayerDao playerDao;
    @Override
    public PartyDto saveParty() {
        // Save new Party
        Party party = new Party();
        Party partySaved = partyDao.save(party);
        List<Player> players = playerDao.findAll();
        party.setPlayers(new HashSet<>(players));
        // Set game to Party
        Game game = new Game();
        game.setParty(party);
        game.setCurrent(true);
        game.setIdCurrentPlayer(players.get(0).getId());
        GameDto gameSaved = gameService.saveGame(game);
        GameResultDto gameResultDto = new GameResultDto(false, new ArrayList<>());
        return xoZMapper.toPartyDto(partySaved, gameSaved, gameResultDto);
    }

    @Override
    public PartyDto findParty(String uid) throws Exception {
        Party party = partyDao.findByUid(uid).orElseThrow(() -> new Exception("Not found"));
        Game currentGame = party.getGames().stream().filter(Game::isCurrent).findFirst().orElseThrow(() -> new Exception("Not found"));
        GameResultDto gameResultDto = moveService.getGameResult(currentGame.getMoves());
        GameDto gameDto = xoZMapper.toGameDto(currentGame);
        return xoZMapper.toPartyDto(party, gameDto, gameResultDto);
    }
    @Override
    public PartyDto restartGame(String uid) throws Exception {
        Party party = partyDao.findByUid(uid).orElseThrow(() -> new Exception("Not found"));
        Game currentGame = party.getGames().stream().filter(Game::isCurrent).findFirst().orElseThrow(() -> new Exception("Not found"));
        currentGame.setCurrent(false);
        // Set new game to Party
        Game game = new Game();
        List<Player> players = playerDao.findAll();
        party.setPlayers(new HashSet<>(players));
        game.setParty(party);
        game.setCurrent(true);
        game.setIdCurrentPlayer(players.get(0).getId());
        // Save new Game
        GameDto gameSaved = gameService.saveGame(game);
        GameResultDto gameResultDto = new GameResultDto(false, new ArrayList<>());
        return xoZMapper.toPartyDto(party, gameSaved, gameResultDto);
    }
}
