package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int x;
    private int y;
    private int armor;

    private Integer selectedInventoryItemIndex = -1;
    private boolean hasBlueKey = false;
    private boolean hasRedKey = false;

    private int maximumHealth = 25;

    public PlayerModel(String playerName, int x, int y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getPlayerName();
        this.x = player.getX();
        this.y = player.getY();
        this.hasBlueKey= player.hasBlueKey();
        this.hp = player.getHealth();
        this.armor = player.getArmor();
        this.maximumHealth = player.getMaximumHealth();
        this.selectedInventoryItemIndex = player.getSelectedInventoryItemIndex();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean hasBlueKey() {
        return hasBlueKey;
    }

    public boolean hasRedKey() {
        return hasRedKey;
    }

    public Integer getSelectedItem() {
        return selectedInventoryItemIndex;
    }

    public int getArmor() {
        return armor;
    }

    public int getMaximumHealth() {
        return maximumHealth;
    }
}
