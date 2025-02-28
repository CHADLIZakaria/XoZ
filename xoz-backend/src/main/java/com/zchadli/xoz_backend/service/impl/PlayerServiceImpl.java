package com.zchadli.xoz_backend.service.impl;

import com.zchadli.xoz_backend.dao.PlayerDao;
import com.zchadli.xoz_backend.model.Player;
import com.zchadli.xoz_backend.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerDao playerDao;
    @Override
    public void savePlayer(Player player) {
        playerDao.save(player);
    }

}
