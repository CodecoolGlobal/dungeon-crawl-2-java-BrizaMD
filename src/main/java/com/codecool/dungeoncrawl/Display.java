package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Music;
import com.codecool.dungeoncrawl.logic.Util;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.FreeActor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Inventory;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.particles.BloodParticles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Display {
    private GraphicsContext context;
    private Main main;

    public Display(GraphicsContext context, Main main)
    {
        this.context = context;
        this.main = main;
    }

    float oldCameraPixelX = -100000;
    float oldCameraPixelY = -100000;

    float cameraVelocityX = 0;
    float cameraVelocityY = 0;

    float cameraSmoothVelocityX = 0;
    float cameraSmoothVelocityY = 0;

    void updateCameraVelocityX(float cameraPixelX)
    {
        cameraVelocityX = oldCameraPixelX == -100000 ? 0 : cameraPixelX - oldCameraPixelX;
        cameraVelocityX *= 100;
        oldCameraPixelX = cameraPixelX;

        cameraSmoothVelocityX = Util.smoothDamp(cameraSmoothVelocityX, cameraVelocityX, 0.1f, 2f);
    }
    void updateCameraVelocityY(float cameraPixelY)
    {
        cameraVelocityY = oldCameraPixelY == -100000 ? 0 : cameraPixelY - oldCameraPixelY;
        cameraVelocityY *= 100;
        oldCameraPixelY = cameraPixelY;

        cameraSmoothVelocityY = Util.smoothDamp(cameraSmoothVelocityY, cameraVelocityY, 0.1f, 2f);
    }

    float oldPlayerPixelX = -10000;
    float oldPlayerPixelY = -10000;

    float playerVelocityX = 0;
    float playerVelocityY = 0;

    float playerSmoothVelocityX = 0;
    float playerSmoothVelocityY = 0;

    void updatePlayerVelocityX(float playerPositionX)
    {
        playerVelocityX = oldPlayerPixelX == -10000 ? 0 : playerPositionX - oldPlayerPixelX;
        playerVelocityX *= 20;
        oldPlayerPixelX = playerPositionX;

        playerSmoothVelocityX = Util.smoothDamp(playerSmoothVelocityX, playerVelocityX, 0.1f, 2f);
    }
    void updatePlayerVelocityY(float playerPositionY)
    {
        playerVelocityY = oldPlayerPixelY == -10000 ? 0 : playerPositionY - oldPlayerPixelY;
        playerVelocityY *= 10;
        oldPlayerPixelY = playerPositionY;

        playerSmoothVelocityY = Util.smoothDamp(playerSmoothVelocityY, playerVelocityY, 0.1f, 2f);
    }

    private float vibrationProgress = 10;
    private float vibrationRatio()
    {
        float vibrationDuration = 0.5f;
        if (vibrationProgress > vibrationDuration)
            return 0;
        if (vibrationProgress < 0)
            return 1;

        return 1 - Math.max(0, Math.min(1,vibrationProgress / vibrationDuration));
    }
    public void vibrate()
    {
        vibrationProgress = 0;
    }

    public void drawFrame() {
        vibrationProgress += main.framerateInterval() / 1000f;

        float vibrationSize = 15;

        float randomX = Util.randomRange(0, (int)vibrationSize) - vibrationSize / 2;
        float randomY = Util.randomRange(0, (int)vibrationSize) - vibrationSize / 2;

        float vibrationRatio = vibrationRatio();
        float vibrationX = randomX * vibrationRatio;
        float vibrationY = randomY * vibrationRatio;

        // Background color
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, main.canvas.getWidth(), main.canvas.getHeight());

        Player playerActor = main.map.getPlayer();

        float cameraPixelX = (float) playerActor.getPositionX();
        float cameraPixelY = (float) playerActor.getPositionY();

        updateCameraVelocityX(cameraPixelX);
        updateCameraVelocityY(cameraPixelY);

        updatePlayerVelocityX(playerActor.getPositionX());
        updatePlayerVelocityY(playerActor.getPositionY());

        // CALCULATE VIEWPORT
        float viewportTopLeftX = cameraPixelX - (float) Main.VIEWPORT_WIDTH / 2;
        float viewportTopLeftY = cameraPixelY - (float) Main.VIEWPORT_HEIGHT / 2;

        // Sprites
        for (int x = 0; x < main.map.getWidth(); x++) {
            for (int y = 0; y < main.map.getHeight(); y++) {

                int pixelX = Math.round(x * Tiles.TILE_WIDTH - viewportTopLeftX);
                int pixelY = Math.round(y * Tiles.TILE_WIDTH - viewportTopLeftY);

                float lighting = calculateCellLight(x, y);

                if (lighting == 0)
                    continue;

                Cell cell = main.map.getCell(x, y);

                double alphaBackup = context.getGlobalAlpha();

                context.setGlobalAlpha(lighting);

                Tiles.drawRawTile(context, cell, Math.round(pixelX + vibrationX), Math.round(pixelY + vibrationY));

                if (cell.getItem() != null) {
                    Tiles.drawRawTile(context, cell.getItem(), Math.round(pixelX + vibrationX), Math.round(pixelY + vibrationY));
                }

                Actor cellActor = cell.getActor();
                if (cellActor != null && !(cellActor instanceof FreeActor))
                    Tiles.drawRawTile(context, cell.getActor(), Math.round(pixelX + vibrationX), Math.round(pixelY + vibrationY));

                context.setGlobalAlpha(alphaBackup);
            }
        }

        main.torchParticle.frame();
        for (BloodParticles particle : main.bloodParticles)
            particle.frame();

        for (BloodParticles particle : main.bloodParticles) {
            particle.opacity = particle.getLifetimeLeft();
            particle.draw(context, playerActor.getPositionX() + Tiles.TILE_WIDTH / 2 - viewportTopLeftX + vibrationX, playerActor.getPositionY() + Tiles.TILE_WIDTH / 2 - viewportTopLeftY + vibrationY);
        }

        for (int x = 0; x < main.map.getWidth(); x++) {
            for (int y = 0; y < main.map.getHeight(); y++) {

                Cell cell = main.map.getCell(x, y);
                Actor cellActor = cell.getActor();

                if (!(cellActor instanceof  FreeActor))
                    continue;

                FreeActor cellFreeActor = (FreeActor)cellActor;

                int pixelX = Math.round(cellFreeActor.getPositionX() - viewportTopLeftX);
                int pixelY = Math.round(cellFreeActor.getPositionY() - viewportTopLeftY);

                float lighting = calculateCellLight(x, y);

                if (lighting == 0)
                    continue;


                double alphaBackup = context.getGlobalAlpha();

                context.setGlobalAlpha(lighting);

                Tiles.drawRawTile(context, cellFreeActor, Math.round(pixelX + vibrationX), Math.round(pixelY + vibrationY));

                context.setGlobalAlpha(alphaBackup);
            }
        }

        if (playerActor.hasTorch()) {
            main.torchParticle.scale = 0.2f;
            main.torchParticle.velocityX = -playerSmoothVelocityX;
            main.torchParticle.velocityY = -playerSmoothVelocityY;
            main.torchParticle.draw(context, playerActor.getPositionX() + Tiles.TILE_WIDTH / 2 + Main.TORCH_OFFSET_X - viewportTopLeftX + vibrationX, playerActor.getPositionY() + Tiles.TILE_WIDTH / 2 + Main.TORCH_OFFSET_Y - viewportTopLeftY + vibrationY);
        }
        main.setSoundIsPlaying(main.torchSound, playerActor.hasTorch());

        // PARTICLES
        main.particles.updateCameraPosition(cameraPixelX, cameraPixelY);
        main.particles.drawAll(context);

        main.screenParticles.updateParticlesVelocity(-cameraSmoothVelocityX,  0);
        main.screenParticles.updateParticlesRotation(Util.lerp(75, 105, (cameraSmoothVelocityX / 300 + 1) / 2));
        main.screenParticles.updatePerParticleLighting(cameraPixelX, cameraPixelY, viewportTopLeftX, viewportTopLeftY, playerActor.hasTorch());
        main.screenParticles.drawAll(context);
    }

    public void drawUI() {
        final int CURRENTHEALTHPOSX = 26;
        final int CURRENTHEALTHPOSY = 22;
        final int MAXHEALTHPOSX = 24;
        final int MAXHEALTHPOSY = 22;
        setFontSize(15);
        setFontColor(Color.WHITE);
        //draw health bars
        for (int i=0; i< main.map.getPlayer().getHealth()/5;i++) {
            drawTile(CURRENTHEALTHPOSX, CURRENTHEALTHPOSY, 20+(i*Tiles.TILE_WIDTH), 50);
        }

        // draw maximum health bars
        for (int i=0; i< (main.map.getPlayer().getMaximumHealth() - main.map.getPlayer().getHealth())/5;i++) {
            drawTile(MAXHEALTHPOSX, MAXHEALTHPOSY, 20+(main.map.getPlayer().getHealth()/5* Tiles.TILE_WIDTH)+(i*Tiles.TILE_WIDTH), 50);
        }

        // draw inventory items
        context.strokeText(Main.DAMAGE_PREFIX + main.map.getPlayer().getDamage(), 20, 20);
        context.strokeText(Main.ARMOR_PREFIX + main.map.getPlayer().getArmor(), 20, 35);
        int inventoryCounter =0;
        Inventory inventory = main.map.getPlayer().getInventory();
        Item equippedItem = main.map.getPlayer().selectedInventoryItem();
        for (Item item : inventory) {
            Tiles.Tile tile = Tiles.getTile(item.getTileName());
            if (equippedItem == item) {
                // draw background for equipped item
                context.drawImage(Tiles.tileset, 23*(Tiles.TILE_WIDTH+2), 26*(Tiles.TILE_WIDTH+2), Tiles.TILE_WIDTH, Tiles.TILE_WIDTH,
                        18, 78+(inventoryCounter*Tiles.TILE_WIDTH), Tiles.TILE_WIDTH+5, Tiles.TILE_WIDTH+5);
            }
            drawTile(tile.x/(32+2), tile.y/(32+2), 20, 80+(inventoryCounter*Tiles.TILE_WIDTH));
            inventoryCounter++;
        }
    }

    public void drawTile(int row,int column, int pixelX, int pixelY) {
        context.drawImage(Tiles.tileset, row*(Tiles.TILE_WIDTH+2), column*(Tiles.TILE_WIDTH+2), Tiles.TILE_WIDTH, Tiles.TILE_WIDTH,
                pixelX, pixelY, Tiles.TILE_WIDTH, Tiles.TILE_WIDTH);
    }

    // MISC
    private void setFontSize(int size)
    {
        context.setFont(Font.font("Monospaced", size));
    }
    private void setFontColor(Color color)
    {
        context.setStroke(color);
    }

    public float calculateCellLight(float cellX, float cellY)
    {
        Player playerActor = main.map.getPlayer();
        float minDistance = playerActor.hasTorch() ? Main.VIEW_DISTANCE_TORCH_MIN : Main.VIEW_DISTANCE_MIN;
        float maxDistance = playerActor.hasTorch() ? Main.VIEW_DISTANCE_TORCH_MAX : Main.VIEW_DISTANCE_MAX;

        return calculateCellLight(main.map.getLightCastingCells(), playerActor.getPositionX(), playerActor.getPositionY(), cellX, cellY, minDistance, maxDistance);
    }

    public static float calculateCellLight(ArrayList<Cell> lightCastingCells, float playerPositionX, float playerPositionY, float cellX, float cellY, float minDistance, float maxDistance)
    {
        float light = calculateLight(playerPositionX / Tiles.TILE_WIDTH, playerPositionY / Tiles.TILE_WIDTH, cellX, cellY, minDistance, maxDistance);

        for(Cell cell : lightCastingCells)
        {
            float cellLight = calculateLight(cell.getX(), cell.getY(), cellX, cellY, Main.CANDLE_LIGHT_MIN, Main.CANDLE_LIGHT_MAX);
            if (cellLight > light)
                light = cellLight;
        }

        return light;
    }

    public static float calculateLight(float cameraX, float cameraY, float cellX, float cellY) {
        return calculateLight(cameraX, cameraY, cellX, cellY, Main.VIEW_DISTANCE_MIN, Main.VIEW_DISTANCE_MAX);
    }
    public static float calculateLight(float cameraX, float cameraY, float cellX, float cellY, float minDistance, float maxDistance)
    {
        float distance = Util.cellDistance(cameraX, cameraY, cellX, cellY);
        return Util.calculateDistanceFade(distance, minDistance, maxDistance);
    }
}
