package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Torch extends EquippableItem {
    public Torch(Cell cell)
    {
        super(cell, 0, 0, 0);
    }

    @Override
    public String getTileName() {
        return "torch";
    }

    @Override
    public boolean isTorch() {
        return true;
    }
}
