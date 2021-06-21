package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Monster {

    public Ghost(Cell cell) {
        super(cell);
        this.health= 15;
        this.damage= 5;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 1500;
        this.maxDiff = 500;
        this.maxSpeed = 2;
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    protected void attack(Actor actor) {

    }
}
