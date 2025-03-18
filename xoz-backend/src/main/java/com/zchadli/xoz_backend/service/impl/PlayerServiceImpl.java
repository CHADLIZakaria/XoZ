package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.dao.PlayerDao;
import com.zchadli.xoz_backend.dto.PlayerDto;
import com.zchadli.xoz_backend.mapper.XoZMapper;
import com.zchadli.xoz_backend.model.Player;
import com.zchadli.xoz_backend.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerDao playerDao;
    private final XoZMapper xoZMapper;
    @Override
    public void savePlayer(Player player) {
        playerDao.save(player);
    }

    @Override
    public List<PlayerDto> saveDefaultPlayer() {
        Player player1 = playerDao.findById(1L).orElseGet(() -> {
            Player newPlayer = new Player();
            newPlayer.setName("player 1");
            return playerDao.save(newPlayer);
        });
        Player player2 = playerDao.findById(2L).orElseGet(() -> {
            Player newPlayer = new Player();
            newPlayer.setName("player 2");
            return playerDao.save(newPlayer);
        });
        return xoZMapper.toPlayersDto(List.of(player1, player2));
    }

}
