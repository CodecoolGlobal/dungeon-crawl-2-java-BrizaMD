package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Insect extends Monster {
    public Insect(Cell cell) {
        super(cell);
        this.health= 5;
        this.damage= 5;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 100;
        this.maxDiff = 200;
        this.maxSpeed = 20;
    }

    @Override
    public String getTileName() {
        return "insect";
    }

    @Override
    protected void attack(Actor actor) {

    }
}
