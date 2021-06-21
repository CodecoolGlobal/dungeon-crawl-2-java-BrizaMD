package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Snake extends Monster {
    public Snake(Cell cell) {
        super(cell);
        this.health= 5;
        this.damage= 5;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 2000;
        this.maxDiff = 1000;
        this.maxSpeed = 16;
    }

    @Override
    public String getTileName() {
        return "snake";
    }

    @Override
    protected void attack(Actor actor) {

    }
}
