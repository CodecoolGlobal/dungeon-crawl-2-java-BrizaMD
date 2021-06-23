package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Inventory;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.InventoryItemModel;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class InventoryDaoJdbc implements InventoryDao {
    private DataSource dataSource;

    public InventoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Item item, int saveId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO playerinventory (id, save_id, tile_name, x, y) " +
                    "VALUES (DEFAULT, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, saveId);
            statement.setString(2, item.getTileName());
            statement.setInt(3, item.getCell().getX());
            statement.setInt(4, item.getCell().getX());

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

    public List<InventoryItemModel> getItems(int saveId)
    {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "SELECT * FROM playerinventory WHERE save_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, saveId);

            ResultSet results = statement.executeQuery();

            List<InventoryItemModel> allItems = new ArrayList<>();

            while(results.next())
            {
                String tileName = results.getString("tile_name");
                int x = results.getInt("x");
                int y = results.getInt("y");

                allItems.add(new InventoryItemModel(tileName, x, y));
            }

            return allItems;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

