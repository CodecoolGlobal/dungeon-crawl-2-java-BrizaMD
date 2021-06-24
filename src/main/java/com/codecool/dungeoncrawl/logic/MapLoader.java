package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.model.InventoryItemModel;
import com.codecool.dungeoncrawl.model.MonsterModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String mapName) {
        return loadMap(mapName, null);
    }
    public static GameMap loadMap(String mapName, Player player) {
        InputStream is = MapLoader.class.getResourceAsStream("/" + mapName + ".txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        map.setMapName(mapName);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell, player));
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'G':
                            cell.setType(CellType.FLOOR);
                            new Shield(cell);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            new Key(cell, "gold");
                            break;
                        case 'q':
                            cell.setType(CellType.BONES);
                            break;
                        case 'e':
                            cell.setType(CellType.BONES2);
                            break;
                        case 'w':
                            cell.setType(CellType.BARS);
                            break;
                        case 'r':
                            cell.setType(CellType.OPENBARS);
                            break;
                        case 't':
                            cell.setType(CellType.WEB);
                            break;
                        case 'z':
                            cell.setType(CellType.CANDLE);
                            break;
                        case 'u':
                            cell.setType(CellType.CANDELABRE);
                            break;
                        case 'i':
                            cell.setType(CellType.FLOOR);
                            new Golem(cell);
                            break;
                        case 'o':
                            cell.setType(CellType.REDWALL1);
                            break;
                        case 'p':
                            cell.setType(CellType.REDWALL2);
                            break;
                        case 'd':
                            cell.setType(CellType.FLOOR);
                            new Wizard(cell);
                            break;
                        case 'f':
                            cell.setType(CellType.FLOOR);
                            new Spider(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.EXIT);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new Ghost(cell);
                            break;
                        case 'j':
                            cell.setType(CellType.FLOOR);
                            new Kraken(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Chest(cell);
                            break;
                        case 'x':
                            cell.setType(CellType.DIRT);
                            break;
                        case 'l':
                            cell.setType(CellType.TREE1);
                            break;
                        case 'L':
                            cell.setType(CellType.TREE2);
                            break;
                        case 'y':
                            cell.setType(CellType.GRAVE);
                            break;
                        case 'n':
                            cell.setType(CellType.BRIDGE1);
                            break;
                        case 'm':
                            cell.setType(CellType.BRIDGE2);
                            break;
                        case 'v':
                            cell.setType(CellType.BRIDGE3);
                            break;
                        case 'c':
                            cell.setType(CellType.TOWER);
                            break;
                        case 'C':
                            cell.setType(CellType.CASTLE);
                            break;
                        case 'W':
                            cell.setType(CellType.WATER);
                            break;
                        case 'Q':
                            cell.setType(CellType.REDDOOR);
                            map.setRedDoorLocation(new int[]{x, y});
                            break;
                        case 'R':
                            cell.setType(CellType.FLOOR);
                            new Key(cell, "red");
                            break;
                        case 'T':
                            cell.setType(CellType.BLUEDOOR);
                            map.setBlueDoorLocation(new int[]{x, y});
                            break;
                        case 'I':
                            cell.setType(CellType.OPENBLUEDOOR);
                            break;
                        case 'Z':
                            cell.setType(CellType.FLOOR);
                            new Key(cell, "blue");
                            break;
                        case 'U':
                            cell.setType(CellType.FLOOR);
                            new HealCrystal(cell);
                            break;
                        case 'O':
                            cell.setType(CellType.FLOOR);
                            new Food(cell);
                            break;
                        case 'P':
                            cell.setType(CellType.TOILET);
                            new Toilet(cell);
                            break;
                        case '+':
                            cell.setType(CellType.SPECTREE);
                            break;
                        case '!':
                            cell.setType(CellType.DEADTREE);
                            break;
                        case '%':
                            cell.setType(CellType.GRASS1);
                            break;
                        case '/':
                            cell.setType(CellType.GRASS2);
                            break;
                        case '=':
                            cell.setType(CellType.ROCKS);
                            break;
                        case '(':
                            cell.setType(CellType.GRASS1);
                            new Insect(cell);
                            break;
                        case ')':
                            cell.setType(CellType.FLOOR);
                            new Snake(cell);
                            break;
                        case 'K':
                            cell.setType(CellType.FLOOR);
                            new Croc(cell);
                            break;
                        case 'J':
                            cell.setType(CellType.FLOOR);
                            new Bat(cell);
                            break;
                        case 'H':
                            cell.setType(CellType.CITYPOST);
                            break;
                        case 'N':
                            cell.setType(CellType.SIGNPOST);
                            break;
                        case 'B':
                            cell.setType(CellType.FLOOR);
                            new Torch(cell);
                            break;
                        case 'E':
                            cell.setType(CellType.HOUSE1);
                            break;
                        case 'A':
                            cell.setType(CellType.HOUSE2);
                            break;
                        case 'S':
                            cell.setType(CellType.HOUSE3);
                            break;
                        case 'D':
                            cell.setType(CellType.HOUSE4);
                            break;
                        case 'F':
                            cell.setType(CellType.HOUSE5);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        map.updateLightCastingCells();
        return map;
    }

    public static GameMap loadMap(String mapName, PlayerModel loadedPlayer, List<MonsterModel> savedEnemies) {
        List<InventoryItemModel> inventory = loadedPlayer.getInventory();
        InputStream is = MapLoader.class.getResourceAsStream(mapName);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        map.setMapName(mapName);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Skeleton(cell);
                                    break;
                                }
                            }
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            boolean put = false;
                            for (InventoryItemModel item : inventory) {
                                if (item.getX() == x && item.getY() == y) {
                                    put = true;
                                    break;
                                }
                            }
                            if (put) new Sword(cell);
                            break;
                        case 'G':
                            cell.setType(CellType.FLOOR);
                            for (InventoryItemModel item : inventory) {
                                if (!(item.getX() == x && item.getY() == y)) {
                                    System.out.println("helóbeló");
                                    break;
                                }

                            }
                            new Shield(cell);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            put = false;
                            for (InventoryItemModel item : inventory) {
                                if (item.getX() == x && item.getY() == y) {
                                    put = true;
                                    break;
                                }
                            }
                             if (put) new Key(cell, "gold");
                            break;
                        case 'q':
                            cell.setType(CellType.BONES);
                            break;
                        case 'e':
                            cell.setType(CellType.BONES2);
                            break;
                        case 'w':
                            cell.setType(CellType.BARS);
                            break;
                        case 'r':
                            cell.setType(CellType.OPENBARS);
                            break;
                        case 't':
                            cell.setType(CellType.WEB);
                            break;
                        case 'z':
                            cell.setType(CellType.CANDLE);
                            break;
                        case 'u':
                            cell.setType(CellType.CANDELABRE);
                            break;
                        case 'i':
                            cell.setType(CellType.FLOOR);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Golem(cell);
                                    break;
                                }
                            }
                            break;
                        case 'o':
                            cell.setType(CellType.REDWALL1);
                            break;
                        case 'p':
                            cell.setType(CellType.REDWALL2);
                            break;
                        case 'd':
                            cell.setType(CellType.FLOOR);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Wizard(cell);
                                    break;
                                }
                            }
                            break;
                        case 'f':
                            cell.setType(CellType.FLOOR);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Spider(cell);
                                    break;
                                }
                            }
                            break;
                        case 'g':
                            cell.setType(CellType.EXIT);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Ghost(cell);
                                    break;
                                }
                            }
                            break;
                        case 'j':
                            cell.setType(CellType.FLOOR);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Kraken(cell);
                                    break;
                                }
                            }
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            put = false;
                            for (InventoryItemModel item : inventory) {
                                if (item.getX() == x && item.getY() == y) {
                                    put = true;
                                    break;
                                }
                            }
                            if (put) new Chest(cell);
                            break;
                        case 'x':
                            cell.setType(CellType.DIRT);
                            break;
                        case 'l':
                            cell.setType(CellType.TREE1);
                            break;
                        case 'L':
                            cell.setType(CellType.TREE2);
                            break;
                        case 'y':
                            cell.setType(CellType.GRAVE);
                            break;
                        case 'n':
                            cell.setType(CellType.BRIDGE1);
                            break;
                        case 'm':
                            cell.setType(CellType.BRIDGE2);
                            break;
                        case 'v':
                            cell.setType(CellType.BRIDGE3);
                            break;
                        case 'c':
                            cell.setType(CellType.TOWER);
                            break;
                        case 'C':
                            cell.setType(CellType.CASTLE);
                            break;
                        case 'W':
                            cell.setType(CellType.WATER);
                            break;
                        case 'Q':
                            if (loadedPlayer.hasRedKey()) {
                                cell.setType(CellType.REDDOOR);
                                map.setRedDoorLocation(new int[]{x, y});
                                break;
                            } else cell.setType(CellType.FLOOR); break;
                        case 'R':
                            cell.setType(CellType.FLOOR);
                            put = false;
                            for (InventoryItemModel item : inventory) {
                                if (item.getX() == x && item.getY() == y) {
                                    put = true;
                                    break;
                                }
                            }
                            if (put) new Key(cell, "red");
                            break;
                        case 'T':
                            if (loadedPlayer.hasBlueKey()) {
                                cell.setType(CellType.BLUEDOOR);
                                map.setRedDoorLocation(new int[]{x, y});
                                break;
                            } else cell.setType(CellType.OPENBLUEDOOR); break;
                        case 'I':
                            cell.setType(CellType.OPENBLUEDOOR);
                            break;
                        case 'Z':
                            cell.setType(CellType.FLOOR);
                            put = false;
                            for (InventoryItemModel item : inventory) {
                                if (item.getX() == x && item.getY() == y) {
                                    put = true;
                                    break;
                                }
                            }
                            if (put) new Key(cell, "blue");
                            break;
                        case 'U':
                            cell.setType(CellType.FLOOR);
                            put = false;
                            for (InventoryItemModel item : inventory) {
                                if (item.getX() == x && item.getY() == y) {
                                    put = true;
                                    break;
                                }
                            }
                            if (put) new HealCrystal(cell);
                            break;
                        case 'O':
                            cell.setType(CellType.FLOOR);
                            put =false;
                            for (InventoryItemModel item : inventory) {
                                if (item.getX() == x && item.getY() == y) {
                                    put = true;
                                    break;
                                }
                            }
                             if (put) new Food(cell);
                            break;
                        case 'P':
                            cell.setType(CellType.TOILET);
                            new Toilet(cell);
                            break;
                        case '+':
                            cell.setType(CellType.SPECTREE);
                            break;
                        case '!':
                            cell.setType(CellType.DEADTREE);
                            break;
                        case '%':
                            cell.setType(CellType.GRASS1);
                            break;
                        case '/':
                            cell.setType(CellType.GRASS2);
                            break;
                        case '=':
                            cell.setType(CellType.ROCKS);
                            break;
                        case '(':
                            cell.setType(CellType.GRASS1);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Insect(cell);
                                    break;
                                }
                            }
                            break;
                        case ')':
                            cell.setType(CellType.FLOOR);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Snake(cell);
                                    break;
                                }
                            }
                            break;
                        case 'K':
                            cell.setType(CellType.FLOOR);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Croc(cell);
                                    break;
                                }
                            }
                            break;
                        case 'J':
                            cell.setType(CellType.FLOOR);
                            for (MonsterModel enemy : savedEnemies) {
                                if (enemy.getX() == x && enemy.getY() == y) {
                                    new Bat(cell);
                                    break;
                                }
                            }
                            break;
                        case 'H':
                            cell.setType(CellType.CITYPOST);
                            break;
                        case 'N':
                            cell.setType(CellType.SIGNPOST);
                            break;
                        case 'B':
                            cell.setType(CellType.FLOOR);
                            put = false;
                            for (InventoryItemModel item : inventory) {
                                if (item.getX() == x && item.getY() == y) {
                                    put = true;
                                    break;
                                }
                            }
                            if (put) new Torch(cell);
                            break;
                        case 'E':
                            cell.setType(CellType.HOUSE1);
                            break;
                        case 'A':
                            cell.setType(CellType.HOUSE2);
                            break;
                        case 'S':
                            cell.setType(CellType.HOUSE3);
                            break;
                        case 'D':
                            cell.setType(CellType.HOUSE4);
                            break;
                        case 'F':
                            cell.setType(CellType.HOUSE5);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        map.setPlayer(new Player(map.getCell(loadedPlayer.getX(), loadedPlayer.getY())));
        for (InventoryItemModel item: inventory) {
            map.getPlayer().pickupItemByName(item.getTileName());
        }
        map.getPlayer().selectInventoryItem(loadedPlayer.getSelectedItem());
        map.getPlayer().setMaximumHealth(loadedPlayer.getMaximumHealth());
        map.getPlayer().setHealth(loadedPlayer.getHp());
        map.updateLightCastingCells();
        return map;
    }
}
