package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Toilet extends Item{

    public Toilet(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "toilet";
    }
}
