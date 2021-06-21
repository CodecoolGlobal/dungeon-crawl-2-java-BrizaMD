package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Wizard extends Monster {
    public Wizard(Cell cell) {
        super(cell);
        this.health= 10;
        this.damage= 20;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 200;
        this.maxDiff = 500;
        this.maxSpeed = 0;
    }

    @Override
    public String getTileName() {
        return "wizard";
    }

    @Override
    protected void attack(Actor actor) {

    }
}
