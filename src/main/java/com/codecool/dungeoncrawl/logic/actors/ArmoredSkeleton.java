package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class ArmoredSkeleton extends Monster {

    public ArmoredSkeleton(Cell cell) {
        super(cell);
        this.health= 10;
        this.damage= 5;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 2001;
        this.maxDiff = 3000;
        this.maxSpeed = 0.25F;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
