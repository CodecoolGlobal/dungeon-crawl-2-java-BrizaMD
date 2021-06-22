package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.FreeActor;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface MonsterDao {
    void add(FreeActor enemy, int saveId);
    void update();
}
