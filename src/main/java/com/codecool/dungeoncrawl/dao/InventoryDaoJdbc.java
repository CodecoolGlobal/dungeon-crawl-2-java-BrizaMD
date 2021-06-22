package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Item;


import javax.sql.DataSource;
import java.sql.*;


public class InventoryDaoJdbc implements InventoryDao {
    private DataSource dataSource;

    public InventoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Item item, int saveId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO playerinventory (id, save_id, tile_name) " +
                    "VALUES (DEFAULT, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, saveId);
            statement.setString(2, item.getTileName());

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

