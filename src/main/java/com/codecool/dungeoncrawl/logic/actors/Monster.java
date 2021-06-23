package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.sql.Timestamp;
import java.util.Random;

public abstract class Monster extends FreeActor{
    boolean isMoving = true;
    long timer;
    long CYCLE_DURATION;
    float directionX;
    float directionY;
    long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
    int minTime;
    int maxDiff;
    float maxSpeed;
    float minSpeed;
    final int startingX;
    final int startingY;

    public Monster(Cell cell) {
        super(cell);
        startingX = cell.getX();
        startingY = cell.getY();
    }

    public int getStartingX() {
        return startingX;
    }

    public int getStartingY() {
        return startingY;
    }

    public void enemyMove() {
        if (new Timestamp(System.currentTimeMillis()).getTime()- timestamp > (Math.floor(Math.random() * maxDiff)+minTime)){
            this.directionY = getRandomDirection();
            this.directionX = getRandomDirection();
            this.timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        }
        super.moveSlightly(directionX,directionY);
    }

    protected float getRandomDirection(){
        Random r = new Random();
        float max = maxSpeed;
        float min = maxSpeed * -1;
        return  minSpeed + min + r.nextFloat() * (max - min);
    }

    protected boolean isTimerExpired(){
        return new Timestamp(System.currentTimeMillis()).getTime() - timer > CYCLE_DURATION;
    }
}
