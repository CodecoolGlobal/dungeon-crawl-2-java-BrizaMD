package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Chest extends Item{

    public Chest(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "chest";
    }
}
