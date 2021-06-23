package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.FreeActor;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.model.MonsterModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface MonsterDao {
    void add(Monster enemy, int saveId);
    void update();
    List<MonsterModel> get(int saveId);
}
