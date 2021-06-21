package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item{
    String color;

    public Key(Cell cell, String keyColor) {
        super(cell);
        this.color = keyColor;
    }

    @Override
    public String getTileName() {
        switch (color){
            case "gold":
                return "key";
            case "red":
                return "redkey";
            case "blue":
                return "bluekey";
        }
        return "key";
    }
}