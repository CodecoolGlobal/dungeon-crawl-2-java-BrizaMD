package com.codecool.dungeoncrawl.logic;

import javafx.scene.control.Button;

public class SavegameButton extends Button {
    public int ID;

    public SavegameButton(int ID, String label)
    {
        super(label);
        this.ID = ID;
    }
}
