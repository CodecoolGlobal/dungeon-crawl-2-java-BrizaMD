package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.SaveGame;

import java.util.List;

public interface GameStateDao {
    int add(GameState state);
    void update(GameState state);
    GameState get(SaveGame save);
    List<SaveGame> getAll();
}
