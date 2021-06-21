package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {

    protected Cell cell;
    protected int health;
    protected int damage;



    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }



    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public boolean isDead() {
        return (this.getHealth() <= 0);
    }



    public boolean move(int dx, int dy) {

        Cell nextCell = cell.getNeighbor(dx, dy);

        if (Tiles.isWall(nextCell))
            return false;

        Actor nextCellActor = nextCell.getActor();

        // TODO: fight
        if (nextCell.getTileName().equals("wall")) {return false;}

        if (nextCellActor instanceof Monster) {
            attack(nextCellActor);
            if (nextCellActor.isDead()) {
                nextCell.setActor(null);
            }
            if (this.isDead()) {
                nextCell.setActor(null);
            }
            return false;
        }
        if (nextCellActor instanceof Player) {
            // TODO: damage to the player
            return false;
        }

        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;

        return true;
    }

    protected abstract void attack(Actor actor);
}
