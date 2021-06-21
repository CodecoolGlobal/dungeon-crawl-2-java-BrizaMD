package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.Util;

public class FreeActor extends Actor {

    private float positionX;
    private float positionY;

    private int moveDirectionX = 0;
    private int moveDirectionY = 0;



    public FreeActor(Cell cell)
    {
        super(cell);
        positionX = cell.getX() * Tiles.TILE_WIDTH;
        positionY = cell.getY() * Tiles.TILE_WIDTH;
    }



    public int getPositionX() {
        return Math.round(positionX);
    }

    public int getPositionY() {
        return Math.round(positionY);
    }

    public int getMoveDirectionX() {
        return moveDirectionX;
    }

    public int getMoveDirectionY() {
        return moveDirectionY;
    }

    public Cell getCellFrontOfActor(GameMap map)
    {
        if (moveDirectionX == 0 && moveDirectionY == 0)
            return this.cell;

        int cellPosX = this.cell.getX() + moveDirectionX;
        int cellPosY = this.cell.getY() + moveDirectionY;

        // TODO: if lemegy a mapról akkor this.cell

        return map.getCell(cellPosX, cellPosY);
    }

    @Override
    public String getTileName() {
        return "";
    }



    @Override
    protected void attack(Actor actor) {

    }

    public void moveSlightly(float x, float y)
    {
        if (x == 0 && y == 0)
            return;

        moveDirectionX = (int)Util.normalize(x);
        moveDirectionY = (int)Util.normalize(y);

        updateCellPosition(positionX + x, positionY + y);
    }

    public void alignWithCells()
    {
        float cellPreciseX = (float)positionX / (float)Tiles.TILE_WIDTH;
        float cellPreciseY = (float)positionY / (float)Tiles.TILE_WIDTH;

        int cellX = Math.round(cellPreciseX);
        int cellY = Math.round(cellPreciseY);

        if (cellX != cell.getX() || cellY != cell.getY())
            super.move(cellX - cell.getX(), cellY - cell.getY());
    }

    private void updateCellPosition(float positionX, float positionY)
    {
        float cellPreciseX = positionX / (float)Tiles.TILE_WIDTH;
        float cellPreciseY = positionY / (float)Tiles.TILE_WIDTH;

        int cellX = Math.round(cellPreciseX);

        if (cellPreciseX < this.positionX / (float)Tiles.TILE_WIDTH)
            cellX = (int)Math.floor(cellPreciseX);
        else if (cellPreciseX > this.positionX / (float)Tiles.TILE_WIDTH)
            cellX = (int)Math.ceil(cellPreciseX);


        int cellY = Math.round(cellPreciseY);

        if (cellPreciseY < this.positionY / (float)Tiles.TILE_WIDTH)
            cellY = (int)Math.floor(cellPreciseY);
        else if (cellPreciseY > this.positionY / (float)Tiles.TILE_WIDTH)
            cellY = (int)Math.ceil(cellPreciseY);


        if (cellX != cell.getX() || cellY != cell.getY()) {
            if (!super.move(cellX - cell.getX(), cellY - cell.getY()))
                return;
        }

        this.positionX = positionX;
        this.positionY = positionY;
    }
}
