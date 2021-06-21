package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.sql.Timestamp;

public class Croc extends Monster {
    public Croc(Cell cell) {
        super(cell);
        this.timer = new Timestamp(System.currentTimeMillis()).getTime();
        this.CYCLE_DURATION = (int) (Math.random() * 3000 + 2000);
        this.health= 10;
        this.damage= 10;
        this.directionY = getRandomDirection();
        this.directionX = getRandomDirection();
        this.minTime = 5000;
        this.maxDiff = 2500;
        this.maxSpeed = 0.5f;
    }

    @Override
    public String getTileName() {
        return "croc";
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
