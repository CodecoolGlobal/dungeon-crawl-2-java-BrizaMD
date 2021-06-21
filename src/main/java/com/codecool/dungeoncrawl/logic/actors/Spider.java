package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.sql.Timestamp;

public class Spider extends Monster {

    public Spider(Cell cell) {
        super(cell);
        this.timer = new Timestamp(System.currentTimeMillis()).getTime();
        this.CYCLE_DURATION = (int) (Math.random() * 1000 + 1000);
        this.health= 5;
        this.damage= 10;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 200;
        this.maxDiff = 500;
        this.maxSpeed = 9;


    }

    @Override
    public String getTileName() {
        return "spider";
    }

    @Override
    protected void attack(Actor actor) {

    }

    @Override
    public void enemyMove() {
        if (isTimerExpired()){
            isMoving = !isMoving;
            timer = new Timestamp(System.currentTimeMillis()).getTime();
        }
        if (isMoving) super.enemyMove();
    }
}
