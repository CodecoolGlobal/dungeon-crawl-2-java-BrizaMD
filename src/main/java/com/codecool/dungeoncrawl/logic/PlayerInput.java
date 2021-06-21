package com.codecool.dungeoncrawl.logic;

import javafx.scene.input.KeyEvent;

public class PlayerInput {

    private boolean buttonLeft = false;
    private boolean buttonRight = false;
    private boolean buttonUp = false;
    private boolean buttonDown = false;
    private boolean useButton = false;

    public boolean isTestButton() {
        return testButton;
    }

    private boolean testButton = false;

    public int getAxisX() {
        return axisX;
    }
    public int getAxisY() {
        return axisY;
    }


    private int axisX;
    private int axisY;

    public PlayerInput()
    {
        this.axisX = 0;
        this.axisY = 0;
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case W:
                buttonUp = true;
                break;
            case DOWN:
            case S:
                buttonDown = true;
                break;
            case LEFT:
            case A:
                buttonLeft = true;
                break;
            case RIGHT:
            case D:
                buttonRight = true;
                break;
            case F:
                useButton = true;
                break;
            case INSERT:
                testButton = true;
                break;
        }
        updateAxes();
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case W:
                buttonUp = false; break;
            case DOWN:
            case S:
                buttonDown = false; break;
            case LEFT:
            case A:
                buttonLeft = false; break;
            case RIGHT:
            case D:
                buttonRight = false; break;
            case F:
                useButton = false;
                break;
            case INSERT:
                testButton = false;
                break;
        }
        updateAxes();
    }

    public void updateAxes()
    {
        axisX = 0;
        if (buttonLeft)
            axisX --;
        if (buttonRight)
            axisX ++;

        axisY = 0;
        if (buttonUp)
            axisY --;
        if (buttonDown)
            axisY ++;
    }

    public boolean isUseButton() {
        return useButton;
    }
}
