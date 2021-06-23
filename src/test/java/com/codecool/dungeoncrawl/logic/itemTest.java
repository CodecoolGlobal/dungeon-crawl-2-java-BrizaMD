package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.EquippableItem;
import com.codecool.dungeoncrawl.logic.items.Inventory;
import com.codecool.dungeoncrawl.logic.items.Item;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class itemTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);
    Item item = new Item(new Cell(map, 1, 2, CellType.FLOOR)) {
        @Override
        public String getTileName() {
            return "test";
        }
    };
    EquippableItem armor = new EquippableItem(new Cell(map, 1, 2, CellType.FLOOR),0,10,0) {
        @Override
        public String getTileName() {
            return "armor";
        }
    };
    Item cheese = new Item(new Cell(map, 1, 2, CellType.FLOOR)) {
        @Override
        public String getTileName() {
            return "cheese";
        }
    };

}
