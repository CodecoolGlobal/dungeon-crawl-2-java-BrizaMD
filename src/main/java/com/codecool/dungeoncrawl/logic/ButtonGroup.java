package com.codecool.dungeoncrawl.logic;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Hashtable;
import java.util.Map;

public class ButtonGroup {
    private Map<Integer, String> values;
    private GridPane pane;
    private int x;
    private int y;
    private double w;
    private double h;
    public SavegameButton[] buttons;

    public EventHandler<ActionEvent> buttonClick = null;

    public ButtonGroup(GridPane pane, int x, int y, double w, double h)
    {
        values = new Hashtable<>();
        buttons = new SavegameButton[0];
        this.pane = pane;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        refreshButtons();
    }

    public void AddButton(Integer id, String name)
    {
        values.put(id, name);
        refreshButtons();
    }

    private void refreshButtons()
    {
        for (Button button : buttons)
            pane.getChildren().remove(button);

        buttons = new SavegameButton[values.size()];

        int index = 0;
        for (Integer itemId : values.keySet())
        {
            String itemName = values.get(itemId);

            SavegameButton button = new SavegameButton(itemId, "[" + itemId.toString() + "] " +itemName);
            button.setPrefSize(w, h);
            button.setFocusTraversable(false);
            pane.add(button, x, y + index * 30);

            buttons[index] = button;

            index++;
        }
    }
}