package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public abstract class EquippableItem extends Item{

    private final int plusDamage;
    private final int plusArmor;
    private final int plusCriticalChance;

    public EquippableItem(Cell cell, int plusDamage, int plusArmor, int plusCriticalChance)
    {
        super(cell);

        this.plusDamage = plusDamage;
        this.plusArmor = plusArmor;
        this.plusCriticalChance = plusCriticalChance;
    }

    public boolean isTorch() { return false; }

    public int getPlusDamage() {
        return plusDamage;
    }

    public int getPlusArmor() {
        return plusArmor;
    }

    public int getPlusCriticalChance() {
        return plusCriticalChance;
    }

    @Override
    public boolean isEquippable() {return true;}
}
