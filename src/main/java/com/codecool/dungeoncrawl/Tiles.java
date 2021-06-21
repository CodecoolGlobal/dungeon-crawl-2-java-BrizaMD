package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    public static Image tileset = new Image("/tiles_transparent_3.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    // TODO: stora isWall and isLightCasting into Tiles

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(28, 2));
        tileMap.put("playershield", new Tile(27, 0));
        tileMap.put("playertorch", new Tile(25, 0));
        tileMap.put("player2sword", new Tile(30, 2));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("sword", new Tile(0, 29));
        tileMap.put("shield", new Tile(6, 24));
        tileMap.put("key", new Tile(16,23));
        tileMap.put("bones", new Tile(0,15));
        tileMap.put("bones2", new Tile(1,15));
        tileMap.put("bars", new Tile(5,3));
        tileMap.put("openbars", new Tile(4,4));
        tileMap.put("web", new Tile(2,15));
        tileMap.put("candle", new Tile(4,15));
        tileMap.put("candelabre", new Tile(5,15));
        tileMap.put("redwall1", new Tile(7,15));
        tileMap.put("redwall2", new Tile(6,13));
        tileMap.put("golem", new Tile(30,6));
        tileMap.put("wizard", new Tile(24,1));
        tileMap.put("spider", new Tile(30,5));
        tileMap.put("exit", new Tile(3,4));
        tileMap.put("ghost", new Tile(27,8));
        tileMap.put("kraken", new Tile(25,8));
        tileMap.put("chest", new Tile(8,6));
        tileMap.put("dirt", new Tile(2,0));
        tileMap.put("tree1", new Tile(3,1));
        tileMap.put("tree2", new Tile(3,2));
        tileMap.put("grave", new Tile(1,14));
        tileMap.put("bridge1", new Tile(10,15));
        tileMap.put("bridge2", new Tile(11,15));
        tileMap.put("bridge3", new Tile(12,15));
        tileMap.put("tower", new Tile(2,19));
        tileMap.put("castle", new Tile(6,19));
        tileMap.put("water", new Tile(8,5));
        tileMap.put("bluedoor", new Tile(0,9));
        tileMap.put("openbluedoor", new Tile(2,9));
        tileMap.put("bluekey", new Tile(17,23));
        tileMap.put("reddoor", new Tile(13,18));
        tileMap.put("redkey", new Tile(18,23));
        tileMap.put("healcrystal", new Tile(18,22));
        tileMap.put("food", new Tile(18,28));
        tileMap.put("toilet", new Tile(12,10));
        tileMap.put("torch", new Tile(10, 25));
        tileMap.put("spectree", new Tile(4,2));
        tileMap.put("deadtree", new Tile(6,2));
        tileMap.put("grass1", new Tile(6,0));
        tileMap.put("grass2", new Tile(7,0));
        tileMap.put("rocks", new Tile(5,2));
        tileMap.put("insect", new Tile(26,5));
        tileMap.put("snake", new Tile(28,8));
        tileMap.put("croc", new Tile(29,8));
        tileMap.put("bat", new Tile(26,8));
        tileMap.put("citypost", new Tile(0,7));
        tileMap.put("signpost", new Tile(1,7));
        tileMap.put("house1", new Tile(0,21));
        tileMap.put("house2", new Tile(1,21));
        tileMap.put("house3", new Tile(2,21));
        tileMap.put("house4", new Tile(3,20));
        tileMap.put("house5", new Tile(4,20));
    }

    public final static String[] lightCastingTiles = new String[] {
            "candle"
    };

    public final static String[] wallBlocks = new String[] {
            "redwall1",
            "redwall2",
            "bars",
            "candle",
            "candelabre",
            "reddoor",
            "bluedoor",
            "tree1",
            "tree2",
            "spectree",
            "deadtree",
            "water",
            "tower",
            "house1",
            "house2",
            "house3",
            "house4",
            "house5",
            "citypost",
            "signpost",
    };

    public static boolean isWall(Cell cell)
    {
        return Arrays.asList(wallBlocks).contains(cell.getTileName());
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        drawRawTile(context, d, x * TILE_WIDTH, y * TILE_WIDTH);
    }

    public static void drawRawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x, y, TILE_WIDTH, TILE_WIDTH);
    }

    public static Tile getTile(String tileName) {
        return tileMap.get(tileName);
    }
}
