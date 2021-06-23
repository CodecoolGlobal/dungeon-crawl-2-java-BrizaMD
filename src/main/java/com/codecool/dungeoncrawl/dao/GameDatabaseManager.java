package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.FreeActor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.MonsterModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.model.SaveGame;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDaoJdbc gameStateDao;
    private MonsterDaoJdbc monsterDao;
    private InventoryDaoJdbc inventoryDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
        monsterDao = new MonsterDaoJdbc(dataSource);
        inventoryDao = new InventoryDaoJdbc(dataSource);
    }

    public void saveGame(Player player, GameState gameState, List<FreeActor> enemies) throws SQLException {
        setup();
        PlayerModel model = new PlayerModel(player);
        int saveId = gameStateDao.add(gameState);
        playerDao.add(model, saveId);
        for (FreeActor enemy: enemies) {
            monsterDao.add(enemy, saveId);
        }
        for (Item item: player.getAllItems()){
            inventoryDao.add(item, saveId);
        }
    }

    public List<SaveGame> loadGame() throws SQLException {
        setup();
        return gameStateDao.getAll();
    }

    public PlayerModel loadPlayer(int saveId) throws SQLException{
        return playerDao.get(saveId);
    }

    public List<MonsterModel> loadEnemies(int saveId) throws SQLException{
        return monsterDao.get(saveId);
    }

    public String loadMapName(int saveId) throws SQLException{
        return gameStateDao.getMapName(saveId);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        String dbName = System.getenv().get("SQL_DATABASE");
        String user = System.getenv().get("SQL_USER");
        String password = System.getenv().get("SQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }


}
