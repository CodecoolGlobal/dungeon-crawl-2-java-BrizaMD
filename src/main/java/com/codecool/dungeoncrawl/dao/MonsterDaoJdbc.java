package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.FreeActor;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class MonsterDaoJdbc implements MonsterDao {
    private DataSource dataSource;

    public MonsterDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(FreeActor enemy, int saveId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO enemies (id, save_id, x, y, tile_name) " +
                    "VALUES (DEFAULT, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, saveId);
            statement.setInt(2, enemy.getX());
            statement.setInt(3, enemy.getY());
            statement.setString(4, enemy.getTileName());    //TODO this is not exactly what we want

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {

    }

}
