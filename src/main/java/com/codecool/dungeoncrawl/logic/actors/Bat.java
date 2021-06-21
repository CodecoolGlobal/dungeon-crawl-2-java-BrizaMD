package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bat extends Monster {
    public Bat(Cell cell) {
        super(cell);
        this.health= 5;
        this.damage= 5;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 1000;
        this.maxDiff = 500;
        this.maxSpeed = 6;
    }

    @Override
    public String getTileName() {
        return "bat";
    }

    @Override
    protected void attack(Actor actor) {

    }
}

