package com.codecool.dungeoncrawl.logic.items;

import java.util.*;

public class Inventory extends ArrayList<Item> {
    public Inventory()
    {
        super();
    }

    @Override
    public String toString()
    {
        ArrayList<String> itemNames = new ArrayList<>();

        for(Item item : this)
        {
            if (item.isEquippable())
                continue;

            itemNames.add(item.getTileName());
        }

        return String.join("\n", itemNames);
    }

    public Map<Integer, String> equippableItems()
    {
        Map<Integer, String> result = new HashMap<>();

        for (int i=0; i<this.size(); i++)
        {
            if (!this.get(i).isEquippable())
                continue;

            result.put(i, this.get(i).getTileName());
        }

        return result;
    }
}
