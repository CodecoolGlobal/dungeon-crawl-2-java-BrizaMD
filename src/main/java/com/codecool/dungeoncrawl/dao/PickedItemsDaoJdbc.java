package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Item;


import javax.sql.DataSource;
import java.sql.*;


public class PickedItemsDaoJdbc implements PickedItemsDao {
    private DataSource dataSource;

    public PickedItemsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Item item, int saveId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO pickeditems (id, save_id, x, y) " +
                    "VALUES (DEFAULT, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, saveId);
            statement.setInt(2, item.getCell().getX());
            statement.setInt(3, item.getCell().getY());

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
