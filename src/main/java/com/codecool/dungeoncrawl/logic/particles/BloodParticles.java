package com.codecool.dungeoncrawl.logic.particles;

import javafx.scene.image.Image;

import java.awt.*;

public class BloodParticles extends ParticleSystem{

    private float directionX;
    private float directionY;

    public BloodParticles(int particleCount, float duration, float dirX, float dirY)
    {
        super(new Image[] { getBloodImage() }, particleCount, duration, false, false);

        this.directionX = dirX;
        this.directionY = dirY;

        setupSystem();
    }

    private void setupSystem()
    {
        defaultOpacity = 0.5f;
        defaultSize = new Dimension(10, 10);
        defaultImageIndex = 0;

        addSizeKeyFrame(0f, new Dimension(4, 4));
        addSizeKeyFrame(1f, new Dimension(7, 7));

        addPositionKeyFrame(0f, 0, 0);
        addPositionKeyFrame(0.2f, directionX * 0.6f, directionY * 0.6f);
        addPositionKeyFrame(1f, directionX, directionY);

        addOpacityKeyFrame(0f, 1f);
        addOpacityKeyFrame(0.6f, 1f);
        addOpacityKeyFrame(1f, 0f);
    }

    private static Image getBloodImage()
    {
        return new Image("/blood.png");
    }
}
