package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.FreeActor;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.model.MonsterModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonsterDaoJdbc implements MonsterDao {
    private DataSource dataSource;

    public MonsterDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Monster enemy, int saveId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO enemies (id, save_id, x, y, tile_name) " +
                    "VALUES (DEFAULT, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, saveId);
            statement.setInt(2, enemy.getStartingX());
            statement.setInt(3, enemy.getStartingY());
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

    @Override
    public List<MonsterModel> get(int saveId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM enemies WHERE save_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, saveId);

            ResultSet resultSet = statement.executeQuery();
            List<MonsterModel> monsters= new ArrayList<>();
            while(resultSet.next()) {

                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                String monsterTileName = resultSet.getString("tile_name");

                monsters.add(new MonsterModel(monsterTileName, x,y));
            }

            return monsters;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
