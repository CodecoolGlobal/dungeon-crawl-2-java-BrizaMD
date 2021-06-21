package com.codecool.dungeoncrawl.logic.particles;

public class ParticleSystemInstance {
    public float positionX;
    public float positionY;
    public int originalIndex;

    public ParticleSystemInstance(float x, float y, int originalIndex) {
        this.positionX = x;
        this.positionY = y;
        this.originalIndex = originalIndex;
    }
}
