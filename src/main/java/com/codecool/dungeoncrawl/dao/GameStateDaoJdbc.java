package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.model.SaveGame;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int add(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO saves (id, current_map, saved_at, name) VALUES (DEFAULT, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, state.getCurrentMap());
            statement.setTimestamp(2, state.getSavedAt());
            statement.setString(3, state.getPlayer().getPlayerName());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public GameState get(SaveGame save) {
        return save.loadData(dataSource);
    }

    @Override
    public List<SaveGame> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM saves";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            List<SaveGame> returnSaves = new ArrayList<>();
            while (results.next()){
                int saveId = results.getInt("id");
                String currentMap = results.getString("current_map");
                Timestamp savedAt = results.getTimestamp("saved_at");
                String saveName = results.getString("name");
                returnSaves.add(new SaveGame(saveId, currentMap, savedAt, saveName));
            }
            return returnSaves;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
