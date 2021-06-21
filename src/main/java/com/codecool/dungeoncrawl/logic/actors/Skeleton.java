package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Monster {
    public Skeleton(Cell cell) {
        super(cell);
        this.health= 5;
        this.damage= 5;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 5001;
        this.maxSpeed = 0.5F;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    protected void attack(Actor actor) {

    }
}
