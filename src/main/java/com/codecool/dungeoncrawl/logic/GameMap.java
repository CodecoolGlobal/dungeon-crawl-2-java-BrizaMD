package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private int[] blueDoorLocation;
    private int[] redDoorLocation;

    private ArrayList<Cell> lightCastingCells;

    private Player player;

    public Cell[][] getCells() {
        return cells;
    }

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }

        lightCastingCells = new ArrayList<>();
    }

    public void updateLightCastingCells()
    {
        lightCastingCells.clear();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cells[x][y].isLightCasting())
                    lightCastingCells.add(cells[x][y]);
            }
        }
    }

    public ArrayList<Cell> getLightCastingCells() {
        return lightCastingCells;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getBlueDoorLocation() {
        return blueDoorLocation;
    }

    public void setBlueDoorLocation(int[] blueDoorLocation) {
        this.blueDoorLocation = blueDoorLocation;
    }

    public int[] getRedDoorLocation() {
        return redDoorLocation;
    }

    public void setRedDoorLocation(int[] redDoorLocation) {
        this.redDoorLocation = redDoorLocation;
    }

}
