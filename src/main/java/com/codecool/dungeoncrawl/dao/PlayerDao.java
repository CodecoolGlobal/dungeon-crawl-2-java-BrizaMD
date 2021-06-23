package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDao {
    void add(PlayerModel player, int saveId);
    void update(PlayerModel player);
    PlayerModel get(int saveId);
    List<PlayerModel> getAll();
}
