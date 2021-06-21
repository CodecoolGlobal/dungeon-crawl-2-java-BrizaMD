package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class HealCrystal extends Item{

    public HealCrystal(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "healcrystal";
    }
}
