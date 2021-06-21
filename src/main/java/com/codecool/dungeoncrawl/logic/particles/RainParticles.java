package com.codecool.dungeoncrawl.logic.particles;

import javafx.scene.image.Image;

import java.awt.*;

public class RainParticles extends ParticleSystem{

    private float xOffset;
    private float yOffset;

    public RainParticles(int particleCount, float duration, float xOffset, float yOffset)
    {
        super(new Image[] {getRainIcon()}, particleCount, duration, true, true);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        setupSystem();
    }

    private void setupSystem()
    {
        defaultOpacity = 0.8f;
        defaultRotation = 90;
        defaultSize = new Dimension(16, 2);
        defaultImageIndex = 0;

        addPositionKeyFrame(0f, 0, 0);
        addPositionKeyFrame(1f, xOffset, yOffset);

        addVelocityKeyFrame(0f, 0f);
        addVelocityKeyFrame(1f, 1f);
    }

    private static Image getRainIcon()
    {
        return new Image("/rain.png");
    }
}
