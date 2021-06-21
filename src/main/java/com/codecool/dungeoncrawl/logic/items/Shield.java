package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Util;

public class Shield extends EquippableItem {
    public Shield(Cell cell)
    {
        super(cell, 0, Util.randomRange(2, 12), Util.randomRange(0, 15));
    }

    @Override
    public String getTileName() {
        return "shield";
    }
}
