package com.codecool.dungeoncrawl.logic.particles;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Util;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;

public class ParticleSystemCollection extends ArrayList<ParticleSystem> {
    ArrayList<ParticleSystemInstance> systemInstances;
    private Main main;

    public ParticleSystemCollection(Main main)
    {
        super();
        systemInstances = new ArrayList<>();
        this.main = main;
    }

    public void add(ParticleSystem system, float x, float y)
    {
        int newIndex = super.size();

        systemInstances.add(new ParticleSystemInstance(x, y, newIndex));
        super.add(system);

        reorderSystems();
    }

    public void updateCameraPosition(float playerX, float playerY)
    {
        for (int i = 0; i<super.size(); i++)
        {
            ParticleSystemInstance instance = getInstance(i);
            if (instance == null)
                continue;

            super.get(i).opacity = main.display.calculateCellLight(instance.positionX / (float) Tiles.TILE_WIDTH, instance.positionY / (float) Tiles.TILE_WIDTH);

            super.get(i).offsetX = -(playerX - (float)Main.VIEWPORT_WIDTH / 2);
            super.get(i).offsetY = -(playerY - (float)Main.VIEWPORT_HEIGHT / 2);
        }
    }

    public void updatePerParticleLighting(float playerWorldX, float playerWorldY, float topLeftX, float topLeftY, boolean playerHasTorch)
    {
        for (int i=0; i<super.size(); i++)
        {
            ParticleSystemInstance instance = getInstance(i);
            if (instance == null)
                continue;

            float instanceWorldX = instance.positionX + topLeftX;
            float instanceWorldY = instance.positionY + topLeftY;

            super.get(i).calculateParticleLights(instanceWorldX, instanceWorldY, main.map.getLightCastingCells(), playerWorldX, playerWorldY, playerHasTorch);
        }
    }

    public void updateParticlesVelocity(float velocityX, float velocityY)
    {
        for (int i=0; i<super.size(); i++) {
            super.get(i).velocityX = velocityX;
            super.get(i).velocityY = velocityY;
        }
    }

    public void updateParticlesRotation(float rotation)
    {
        for (int i=0; i<super.size(); i++) {
            super.get(i).defaultRotation = rotation;
        }
    }

    public ParticleSystemInstance getInstance(int index)
    {
        for (ParticleSystemInstance instance : systemInstances)
        {
            if (instance.originalIndex == index)
                return instance;
        }

        return null;
    }

    public void setSystemPosition(int index, float x, float y)
    {
        ParticleSystemInstance instance = getInstance(index);

        if (instance == null)
            return;

        instance.positionX = x;
        instance.positionY = y;

        reorderSystems();
    }

    public void drawAll(GraphicsContext context)
    {
        for (ParticleSystemInstance instance : systemInstances)
        {
            ParticleSystem system = super.get(instance.originalIndex);

            system.frame();
            system.draw(context, instance.positionX, instance.positionY);
        }
    }

    private void reorderSystems()
    {
        int systemCount = systemInstances.size();
        int iteration = 0;

        ParticleSystemInstance[] instances = new ParticleSystemInstance[systemCount];
        systemInstances.toArray(instances);

        while (iteration < systemCount - 1)
        {
            int subIteration = 0;

            while (subIteration < systemCount - iteration - 1)
            {
                if (instances[subIteration].positionY > instances[subIteration + 1].positionY)
                {
                    ParticleSystemInstance backup = instances[subIteration];
                    instances[subIteration] = instances[subIteration + 1];
                    instances[subIteration + 1] = backup;
                }

                subIteration++;
            }

            iteration++;
        }

        systemInstances.clear();
        systemInstances.addAll(Arrays.asList(instances));
    }
}

