package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Util;

public class Sword extends EquippableItem {
    public Sword(Cell cell) {
        super(cell, Util.randomRange(2, 10), 0, Util.randomRange(5, 55));
    }

    @Override
    public String getTileName() {
        return "sword";
    }
}
