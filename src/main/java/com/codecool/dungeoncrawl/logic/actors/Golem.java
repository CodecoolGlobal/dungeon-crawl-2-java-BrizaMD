package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Golem extends Monster {
    public Golem(Cell cell) {
        super(cell);
        this.health= 35;
        this.damage= 15;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 3000;
        this.maxDiff = 1500;
        this.maxSpeed = 1;
    }

    @Override
    public String getTileName() {
        return "golem";
    }

    @Override
    protected void attack(Actor actor) {

    }
}
