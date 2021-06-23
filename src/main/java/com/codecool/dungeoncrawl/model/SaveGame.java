package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;

import javax.sql.DataSource;
import java.sql.Timestamp;

public class SaveGame {
    int saveId;
    String currentMap;
    Timestamp savedAt;
    String saveName;

    public SaveGame(int saveId, String currentMap, Timestamp savedAt, String saveName) {
        this.saveId = saveId;
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.saveName = saveName;
    }

    public GameState loadData (DataSource dataSource){
        return new GameState(currentMap, savedAt, new PlayerDaoJdbc(dataSource).get(saveId));
    }
}
