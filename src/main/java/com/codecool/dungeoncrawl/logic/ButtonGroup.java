package com.codecool.dungeoncrawl.logic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class ButtonGroup {
    private Map<Integer, String> values;
    private GridPane pane;
    private int x;
    private int y;
    private Button[] buttons;

    private int selectedIndex = -1;

    public ButtonGroup(GridPane pane, int x, int y)
    {
        values = new Hashtable<>();
        buttons = new Button[0];
        this.pane = pane;
        this.x = x;
        this.y = y;

        refreshButtons();
    }

    public void update(Map<Integer, String> newValues)
    {
        values = newValues;

        refreshButtons();
    }

    private void refreshButtons()
    {
        for (Button button : buttons)
            pane.getChildren().remove(button);

        buttons = new Button[values.size()];

        int index = 0;
        for (Integer itemId : values.keySet())
        {
            String itemName = values.get(itemId);

            Button button = new Button("[" + itemId.toString() + "] " +itemName);
            button.setFocusTraversable(false);
            pane.add(button, x, y + index * 30);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    selectedIndex = itemId;
                }
            });

            buttons[index] = button;

            index++;
        }
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int i) {
        selectedIndex = i;
    }
}
