package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Kraken extends Monster {
    public Kraken(Cell cell) {
        super(cell);
        this.health= 50;
        this.damage= 25;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 200;
        this.maxDiff = 500;
        this.maxSpeed = 0;
    }

    @Override
    public String getTileName() {
        return "kraken";
    }

    @Override
    protected void attack(Actor actor) {

    }
}
