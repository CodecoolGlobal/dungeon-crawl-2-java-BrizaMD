package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.items.*;

public class Player extends FreeActor {
    private Inventory inventory = new Inventory();
    private Integer selectedInventoryItemIndex = -1;
    private boolean hasBlueKey = false;
    private boolean hasRedKey = false;

    private int maximumHealth = 25;
    private int criticalChange = 0;

    private boolean isHit = false;

    public Player(Cell cell)
    {
        super(cell);
        InitPlayer(null);
    }
    public Player(Cell cell, Player player) {
        super(cell);
        InitPlayer(player);
    }

    private void InitPlayer(Player player)
    {
        if (player == null) {
            this.health = 25;
            this.maximumHealth = 25;
            this.damage = 10;
        }
        else {
            this.health = player.health;
            this.maximumHealth = player.maximumHealth;
            this.damage = player.damage;
            this.inventory = player.inventory;
            this.selectedInventoryItemIndex = player.selectedInventoryItemIndex;
            this.criticalChange = player.criticalChange;
        }
    }


    public int getMaximumHealth() {
        return maximumHealth;
    }

    public String getTileName() {
        EquippableItem inventoryItem = selectedInventoryItem();

        if (inventoryItem != null)
        {
            if (inventoryItem.getTileName().equals("sword"))
                return "player2sword";
            else if (inventoryItem.getTileName().equals("shield"))
                return "playershield";
            else if (inventoryItem.getTileName().equals("torch"))
                return "playertorch";
        }
        return "player";
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean hasBlueKey() {
        return hasBlueKey;
    }

    public boolean hasRedKey() {
        return hasRedKey;
    }
    public EquippableItem selectedInventoryItem()
    {
        if (selectedInventoryItemIndex >= inventory.size() || selectedInventoryItemIndex < 0)
            return null;

        return (EquippableItem)inventory.get(selectedInventoryItemIndex);
    }

    @Override
    public int getDamage()
    {
        EquippableItem usedItem = selectedInventoryItem();
        return usedItem == null ? damage : damage + usedItem.getPlusDamage();
    }


    public int getCriticalChance()
    {
        EquippableItem usedItem = selectedInventoryItem();
        return usedItem == null ? criticalChange : criticalChange + usedItem.getPlusCriticalChance();
    }

    public int getArmor()
    {
        EquippableItem usedItem = selectedInventoryItem();
        return usedItem == null ? 0 : usedItem.getPlusArmor();
    }

    public boolean hasTorch()
    {
        EquippableItem userItem = selectedInventoryItem();
        return userItem == null ? false : userItem.isTorch();
    }

    private void getRandomBoon() {
        int randomPick = (int)Math.round(Math.random()*10);
        switch (randomPick){
            case 0:
            case 1:
            case 2:
                health = Math.min(health+5, maximumHealth);
                break;
            case 3:
            case 4:
            case 5:
                maximumHealth += 5;
                break;
            case 6:
            case 7:
            case 8:
                damage += 5;
                break;
            case 9:
                maximumHealth += 5;
                health = maximumHealth;
                damage += 5;
                break;
            case 10:
                health -= 10;
        }
    }

    public boolean getIsHit()
    {
        boolean result = isHit;
        isHit = false;
        return result;
    }



    public void selectInventoryItem(Integer itemIndex)
    {
        selectedInventoryItemIndex = itemIndex;
    }

    public void pickupItem() {
        alignWithCells();

        Item itemOnTheGround = this.getCell().getItem();

        if (itemOnTheGround == null)
            return;


        if (itemOnTheGround instanceof HealCrystal){
            maximumHealth += 5;
            health = Math.min(health+5, maximumHealth);
            this.getCell().setItem(null);
            return;
        }

        if (itemOnTheGround instanceof Food){
            health = Math.min(health+10, maximumHealth);
            this.getCell().setItem(null);
            return;
        }

        if (itemOnTheGround instanceof Toilet){
            health = Math.min(health+5, maximumHealth);
            return;
        }

        if (itemOnTheGround instanceof Chest){
            getRandomBoon();
            this.getCell().setItem(null);
            return;
        }

        if (itemOnTheGround instanceof Key){
            if(itemOnTheGround.getTileName().equals("bluekey")){
                hasBlueKey = true;
            } else if (itemOnTheGround.getTileName().equals("redkey")){
                hasRedKey = true;
            }
        }

        inventory.add(itemOnTheGround);

        this.getCell().setItem(null);
    }

    @Override
    public void attack(Actor actor) {

        // TODO: use criticalChance()

        actor.setHealth(actor.getHealth() - getDamage());
        health -= Math.max(1, actor.damage - getArmor());

        isHit = true;
    }

}
