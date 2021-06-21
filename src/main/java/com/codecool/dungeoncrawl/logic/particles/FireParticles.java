package com.codecool.dungeoncrawl.logic.particles;

import javafx.scene.image.Image;

import java.awt.*;
import java.awt.geom.Point2D;

public class FireParticles extends ParticleSystem {

    float yScale = 1;

    public FireParticles(int particleCount, float duration)
    {
        super(getExplosionIcons(), particleCount, duration, true, false);
        setupSystem();
    }
    public FireParticles(int particleCount, float duration, float yScale)
    {
        super(getExplosionIcons(), particleCount, duration, true, false);

        this.yScale = yScale;
        setupSystem();
    }

    private void setupSystem()
    {
        autoIndexKeyFrames();
        zOrder = ParticleZOrder.FIRST_ON_TOP;

        defaultOpacity = 1f;
        defaultRotation = 0;
        defaultSize = new Dimension(100, 100);
        defaultPosition = new Point2D.Double(0, 0);
        defaultImageIndex = 0;

        addPositionKeyFrame(0f, 0, 0);
        addPositionKeyFrame(0.2f, -5, -10 * yScale);
        addPositionKeyFrame(0.4f, 10, -35 * yScale);
        addPositionKeyFrame(0.6f, -15, -50 * yScale);
        addPositionKeyFrame(0.8f, 0, -60 * yScale);
        addPositionKeyFrame(1f, 15, -70 * yScale);

        addOpacityKeyFrame(0f, 0f);
        addOpacityKeyFrame(0.2f, 1f);
        addOpacityKeyFrame(0.8f, 0.2f);
        addOpacityKeyFrame(1f, 0f);

        addRotationKeyFrame(0f, 0);
        addRotationKeyFrame(0.5f, 180f);
        addRotationKeyFrame(1f, 360f);

        addSizeKeyFrame(0, 70, 70);
        addSizeKeyFrame(0.2f, 130, 130);
        addSizeKeyFrame(0.6f, 160, 160);
        addSizeKeyFrame(1f, 120, 120);

        addVelocityKeyFrame(0.1f, 0f);
        addVelocityKeyFrame(1f, 1f);
    }

    public static Image[] getExplosionIcons()
    {
        int expImageCount = 20;
        Image[] explosionImages = new Image[expImageCount];

        for (int i=0; i<expImageCount; i++)
        {
            explosionImages[i] = new Image("/explosion/explosion-" + ((Integer)(i+1)).toString() + ".png");
        }
        return explosionImages;
    }
}
