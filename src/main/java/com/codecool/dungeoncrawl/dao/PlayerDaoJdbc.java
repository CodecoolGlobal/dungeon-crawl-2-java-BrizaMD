package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.model.SaveGame;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player, int saveId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (id, save_id, hp, maximum_hp, armor, x, y, selected_item, has_red_key, has_blue_key) " +
                    "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, saveId);
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getMaximumHealth());
            statement.setInt(4, player.getArmor());
            statement.setInt(5, player.getX());
            statement.setInt(6, player.getY());
            statement.setInt(7, player.getSelectedItem());
            statement.setBoolean(8, player.hasRedKey());
            statement.setBoolean(9, player.hasBlueKey());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

    }

    @Override
    public PlayerModel get(int saveId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM player WHERE save_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, saveId);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            //TODO how to return? Player needs a Cell, Cell needs a lot more info? Leave null deal with later?
//            PlayerModel loadedPlayer = new Player(null);
//
//            int hp = resultSet.getInt("hp");
//            int maxHp = resultSet.getInt("maximum_hp");
//            int armor = resultSet.getInt("armor");
//            int x = resultSet.getInt("x");
//            int y = resultSet.getInt("y");
//            int selectedItem = resultSet.getInt("selected_item");
//            boolean hasRedKey = resultSet.getBoolean("has_red_key");
//            boolean hasBlueKey = resultSet.getBoolean("has_blue_key");
//
//
//            return loadedPlayer;
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }
}
