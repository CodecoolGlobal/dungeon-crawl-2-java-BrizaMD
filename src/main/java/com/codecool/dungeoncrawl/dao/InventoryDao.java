package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Item;

public interface InventoryDao {
    void add(Item item, int saveId);
    void update();
}
