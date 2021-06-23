package com.codecool.dungeoncrawl.model;

public class InventoryItemModel {
    public String getTileName() {
        return tileName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private String tileName;
    private int x;
    private int y;

    public InventoryItemModel(String tileName, int x, int y)
    {
        this.tileName = tileName;
        this.x = x;
        this.y = y;
    }
}
