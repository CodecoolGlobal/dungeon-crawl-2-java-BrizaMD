package com.codecool.dungeoncrawl.logic.particles;

import com.codecool.dungeoncrawl.Display;
import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class ParticleSystem {
    public ParticleZOrder zOrder = ParticleZOrder.LAST_ON_TOP;

    private javafx.scene.image.Image[] fxImages;
    private ImageIcon[] images;

    private int imageCount() {
        return swingMode ? images.length : fxImages.length;
    }

    private Particle[] particles;

    private boolean swingMode = true;

    private OpacityKeyFrame[] opacityKeyFrames = new OpacityKeyFrame[0];
    private SizeKeyFrame[] sizeKeyFrames = new SizeKeyFrame[0];
    private RotationKeyFrame[] rotationKeyFrames = new RotationKeyFrame[0];
    private PositionKeyFrame[] positionKeyFrames = new PositionKeyFrame[0];
    private IndexKeyFrame[] indexKeyFrames = new IndexKeyFrame[0];
    private VelocityMultiplierKeyFrame[] velocityKeyFrames = new VelocityMultiplierKeyFrame[0];

    public float defaultOpacity = 1;
    public Dimension defaultSize = new Dimension(10, 10);
    public float defaultRotation = 0;
    public Point2D defaultPosition = new Point2D.Double(0, 0);
    public int defaultImageIndex = 0;
    public float defaultVelocity = 0;

    private boolean preheating = false;
    private boolean repeating = false;

    private float duration = 5; // másodperc
    private float lifetime = 0;

    public float scale = 1;
    public float opacity = 1;
    public float offsetX = 0;
    public float offsetY = 0;
    public float velocityX = 0;
    public float velocityY = 0;

    public ParticleSystem(ImageIcon[] images, int particleCount, float duration, boolean repeating, boolean preheating) {
        swingMode = true;

        this.duration = duration;
        this.repeating = repeating;
        this.preheating = preheating;
        this.images = images;

        particles = new Particle[particleCount];

        for (int i = 0; i < particleCount; i++) {
            particles[i] = new Particle((float) i / (float) particleCount, defaultOpacity, defaultRotation, defaultVelocity, defaultSize, defaultPosition, defaultImageIndex, i == 0 || preheating);
        }

        reorderParticles();
    }

    public ParticleSystem(javafx.scene.image.Image[] images, int particleCount, float duration, boolean repeating, boolean preheating) {
        swingMode = false;

        this.duration = duration;
        this.repeating = repeating;
        this.preheating = preheating;
        this.fxImages = images;

        particles = new Particle[particleCount];

        for (int i = 0; i < particleCount; i++) {
            particles[i] = new Particle((float) i / (float) particleCount, defaultOpacity, defaultRotation, defaultVelocity, defaultSize, defaultPosition, defaultImageIndex, i == 0 || preheating);
        }

        reorderParticles();
    }

    public void draw(Graphics2D graphics, Point2D point) {
        draw(graphics, Math.round(point.getX()), Math.round(point.getY()));
    }

    public void draw(Graphics2D graphics, double x, double y) {
        draw(graphics, Math.round(x), Math.round(y));
    }

    public void draw(Graphics2D graphics, float x, float y) {
        draw(graphics, Math.round(x), Math.round(y));
    }

    public void draw(Graphics2D graphics, int x, int y) {
        if (isEnded())
            return;

        for (int i = 0; i < particles.length; i++) {
            if (!particles[i].enabled)
                continue;

            updateParticles();

            // draw particle
            float fullOpacity = particles[i].opacity;
            float image1Transp = (1 - particles[i].imageLerp) * fullOpacity;
            float image2Transp = particles[i].imageLerp * fullOpacity;

            if (image1Transp > 0)
                image1Transp = (float) Math.sqrt(image1Transp);

            if (image2Transp > 0)
                image2Transp = (float) Math.sqrt(image2Transp);

            image1Transp = Math.max(0f, Math.min(1f, image1Transp)) * opacity;
            image2Transp = Math.max(0f, Math.min(1f, image2Transp)) * opacity;

            Rectangle2D particleRect = particleRectangle(particles[i], x, y);

            AffineTransform transformBackup = graphics.getTransform();
            AffineTransform rotatedTransform = AffineTransform.getRotateInstance(Math.toRadians(particles[i].rotation), particleRect.getX() + particleRect.getWidth() / 2, particleRect.getY() + particleRect.getHeight() / 2);
            graphics.setTransform(rotatedTransform);

            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, image1Transp);

            graphics.setComposite(composite);

            graphics.drawImage(particles[i].image1, (int) particleRect.getX(), (int) particleRect.getY(), (int) particleRect.getWidth(), (int) particleRect.getHeight(), null);

            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, image2Transp);

            graphics.setComposite(composite);

            graphics.drawImage(particles[i].image2, (int) particleRect.getX(), (int) particleRect.getY(), (int) particleRect.getWidth(), (int) particleRect.getHeight(), null);

            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

            graphics.setTransform(transformBackup);
        }
    }

    public void draw(GraphicsContext context, float x, float y) {
        draw(context, (int) Math.round(x), (int) Math.round(y));
    }

    public void draw(GraphicsContext context, double x, double y) {
        draw(context, (int) Math.round(x), (int) Math.round(y));
    }

    public void draw(GraphicsContext context, int x, int y) {
        if (isEnded())
            return;

        for (int i = 0; i < particles.length; i++) {

            if (!particles[i].enabled)
                continue;

            updateParticles();

            // draw particle
            float fullOpacity = particles[i].opacity;
            float image1Transp = (1 - particles[i].imageLerp) * fullOpacity * particles[i].opacityMultiplier;
            float image2Transp = particles[i].imageLerp * fullOpacity * particles[i].opacityMultiplier;

            image1Transp = Math.max(0f, Math.min(1f, image1Transp)) * opacity;
            image2Transp = Math.max(0f, Math.min(1f, image2Transp)) * opacity;

            Rectangle2D particleRect = particleRectangle(particles[i], x, y);

            // rotation
            Affine backupTransform = context.getTransform();
            Rotate rotate = new Rotate(particles[i].rotation, particleRect.getX() + particleRect.getWidth() / 2, particleRect.getY() + particleRect.getHeight() / 2);
            context.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());

            double alphaBackup = context.getGlobalAlpha();

            context.setGlobalAlpha(image1Transp);
            context.drawImage(particles[i].fxImage1, particleRect.getX(), particleRect.getY(), particleRect.getWidth(), particleRect.getHeight());

            context.setGlobalAlpha(image2Transp);
            context.drawImage(particles[i].fxImage2, particleRect.getX(), particleRect.getY(), particleRect.getWidth(), particleRect.getHeight());

            context.setTransform(backupTransform);

            context.setGlobalAlpha(alphaBackup);
        }
    }

    private Rectangle2D particleRectangle(Particle particle, float x, float y) {
        return new Rectangle2D.Double(
                Math.floor(x + (particle.position.getX() * scale - (particle.size.width * scale) / 2f) + offsetX + velocityX * particle.velocityMultiplier),
                Math.floor(y + (particle.position.getY() * scale - (particle.size.height * scale) / 2f) + offsetY + velocityY * particle.velocityMultiplier),
                Math.ceil(particle.size.width * scale),
                Math.ceil(particle.size.height * scale));
    }

    public boolean isEnded()
    {
        return lifetime > duration && !repeating;
    }

    public float getLifetimeLeft()
    {
        return Math.min(1, Math.max(0, (duration - lifetime) / lifetime));
    }

    public void frame()
    {
        if (isEnded())
            return;

        lifetime += frameStep();

        boolean reorderZ = false;

        for (int i=0; i<particles.length; i++) {
            if (particles[i].progress >= 1) {
                particles[i].progress -= 1;

                if (lifetime < duration || repeating) {
                    particles[i].enabled = true;
                    reorderZ = true;
                }
                else
                    particles[i].enabled = false;
            }

            // advance progress
            particles[i].progress += frameStep();
        }

        if (reorderZ)
            reorderParticles();
    }

    void updateParticles()
    {
        for (int i=0; i<particles.length; i++) {

            if (!particles[i].enabled)
                continue;


            // calculate keyframes
            if (opacityKeyFrames.length > 0) {
                int opacityKeyFrameIndex = getKeyframeIndexFromPosition(opacityKeyFrames, particles[i].progress);
                int opacityNextKeyframeIndex = opacityKeyFrameIndex < opacityKeyFrames.length - 1 ? opacityKeyFrameIndex + 1 : 0;

                float ratioDelta = opacityKeyFrames[opacityNextKeyframeIndex].point - opacityKeyFrames[opacityKeyFrameIndex].point;
                float ratioMin = particles[i].progress - opacityKeyFrames[opacityKeyFrameIndex].point;
                float opacityRatio = ratioMin / ratioDelta;

                particles[i].opacity = lerp(opacityKeyFrames[opacityKeyFrameIndex].opacity, opacityKeyFrames[opacityNextKeyframeIndex].opacity, opacityRatio);
            }
            else
                particles[i].opacity = defaultOpacity;

            if (sizeKeyFrames.length > 0) {
                int sizeKeyFrameIndex = getKeyframeIndexFromPosition(sizeKeyFrames, particles[i].progress);
                int sizeNextKeyframeIndex = sizeKeyFrameIndex < sizeKeyFrames.length - 1 ? sizeKeyFrameIndex + 1 : 0;

                float ratioDelta = sizeKeyFrames[sizeNextKeyframeIndex].point - sizeKeyFrames[sizeKeyFrameIndex].point;
                float ratioMin = particles[i].progress - sizeKeyFrames[sizeKeyFrameIndex].point;
                float sizeRatio = ratioMin / ratioDelta;

                float sizeX = lerp((float)sizeKeyFrames[sizeKeyFrameIndex].size.width, (float)sizeKeyFrames[sizeNextKeyframeIndex].size.width, sizeRatio);
                float sizeY = lerp((float)sizeKeyFrames[sizeKeyFrameIndex].size.height, (float)sizeKeyFrames[sizeNextKeyframeIndex].size.height, sizeRatio);

                particles[i].size = new Dimension(Math.round(sizeX), Math.round(sizeY));
            }
            else
                particles[i].size = defaultSize;

            if (rotationKeyFrames.length > 0) {
                int rotationKeyFrameIndex = getKeyframeIndexFromPosition(rotationKeyFrames, particles[i].progress);
                int rotationNextKeyframeIndex = rotationKeyFrameIndex < rotationKeyFrames.length - 1 ? rotationKeyFrameIndex + 1 : 0;

                float ratioDelta = rotationKeyFrames[rotationNextKeyframeIndex].point - rotationKeyFrames[rotationKeyFrameIndex].point;
                float ratioMin = particles[i].progress - rotationKeyFrames[rotationKeyFrameIndex].point;
                float rotationRatio = ratioMin / ratioDelta;

                particles[i].rotation = lerpRotation(rotationKeyFrames[rotationKeyFrameIndex].rotation, rotationKeyFrames[rotationNextKeyframeIndex].rotation, rotationRatio);
            }
            else
                particles[i].rotation = defaultRotation;

            if (positionKeyFrames.length > 0) {
                int positionKeyFrameIndex = getKeyframeIndexFromPosition(positionKeyFrames, particles[i].progress);
                int positionNextKeyframeIndex = positionKeyFrameIndex < positionKeyFrames.length - 1 ? positionKeyFrameIndex + 1 : 0;

                float ratioDelta = positionKeyFrames[positionNextKeyframeIndex].point - positionKeyFrames[positionKeyFrameIndex].point;
                float ratioMin = particles[i].progress - positionKeyFrames[positionKeyFrameIndex].point;
                float positionRatio = ratioMin / ratioDelta;

                float positionX = lerp((float)positionKeyFrames[positionKeyFrameIndex].position.getX(), (float)positionKeyFrames[positionNextKeyframeIndex].position.getX(), positionRatio);
                float positionY = lerp((float)positionKeyFrames[positionKeyFrameIndex].position.getY(), (float)positionKeyFrames[positionNextKeyframeIndex].position.getY(), positionRatio);

                particles[i].position = new Point2D.Double(positionX, positionY);
            }
            else
                particles[i].position = defaultPosition;

            if (indexKeyFrames.length > 0) {
                int indexKeyFrameIndex = getKeyframeIndexFromPosition(indexKeyFrames, particles[i].progress);
                int indexNextKeyFrameIndex = indexKeyFrameIndex < indexKeyFrames.length - 1 ? indexKeyFrameIndex + 1 : 0;

                float ratioDelta = indexKeyFrames[indexNextKeyFrameIndex].point - indexKeyFrames[indexKeyFrameIndex].point;
                float ratioMin = particles[i].progress - indexKeyFrames[indexKeyFrameIndex].point;
                particles[i].imageLerp = ratioMin / ratioDelta;

                particles[i].imageIndex = indexKeyFrames[indexKeyFrameIndex].index;

                if (swingMode) {
                    particles[i].image1 = images[indexKeyFrames[indexKeyFrameIndex].index].getImage();
                    particles[i].image2 = images[indexKeyFrames[indexNextKeyFrameIndex].index].getImage();
                }
                else {
                    particles[i].fxImage1 = fxImages[indexKeyFrames[indexKeyFrameIndex].index];
                    particles[i].fxImage2 = fxImages[indexKeyFrames[indexNextKeyFrameIndex].index];
                }
            }
            else {
                particles[i].imageIndex = defaultImageIndex;
                particles[i].imageLerp = 0;

                if (swingMode)
                    particles[i].image1 = particles[i].image2 = images[particles[i].imageIndex].getImage();
                else
                    particles[i].fxImage1 = particles[i].fxImage2 = fxImages[particles[i].imageIndex];
            }
            if (velocityKeyFrames.length > 0) {
                int velocityKeyframeIndex = getKeyframeIndexFromPosition(velocityKeyFrames, particles[i].progress);
                int velocityNextKeyframeIndex = velocityKeyframeIndex < velocityKeyFrames.length - 1 ? velocityKeyframeIndex + 1 : 0;

                float ratioDelta = velocityKeyFrames[velocityNextKeyframeIndex].point - velocityKeyFrames[velocityKeyframeIndex].point;
                float ratioMin = particles[i].progress - velocityKeyFrames[velocityKeyframeIndex].point;
                float velocityRatio = ratioMin / ratioDelta;

                particles[i].velocityMultiplier = lerp(velocityKeyFrames[velocityKeyframeIndex].velocityMultiplier, velocityKeyFrames[velocityNextKeyframeIndex].velocityMultiplier, velocityRatio);
            }
            else
                particles[i].velocityMultiplier = defaultVelocity;

            particles[i].imageIndex = Math.max(0, Math.min(imageCount() - 1, particles[i].imageIndex));
        }
    }


    public void restart()
    {
        lifetime = 0f;

        for (int i=0; i<particles.length;i++)
        {
            particles[i].progress = (float)i / (float)particles.length;
            particles[i].enabled = i == 0 || preheating;
        }

        reorderParticles();
    }


    private void reorderParticles()
    {
        int iteration = 0;
        int particleCount = particles.length;

        while (iteration < particleCount - 1)
        {
            int subIteration = 0;

            while (subIteration < particleCount - iteration - 1)
            {
                if ((zOrder == ParticleZOrder.LAST_ON_TOP && particles[subIteration].progress > particles[subIteration + 1].progress) ||
                        (zOrder == ParticleZOrder.FIRST_ON_TOP && particles[subIteration].progress < particles[subIteration + 1].progress))
                {
                    Particle temp = particles[subIteration + 1];
                    particles[subIteration + 1] = particles[subIteration];
                    particles[subIteration] = temp;
                }

                subIteration++;
            }

            iteration++;
        }
    }

    public void calculateParticleLights(float x, float y, ArrayList<Cell> lightCastingCells, float playerPositionX, float playerPositionY, boolean playerHasTorch)
    {
        for (Particle particle : particles)
        {
            Rectangle2D particleRect = particleRectangle(particle, x, y);

            float particleCellX = (float)(particleRect.getX() + particleRect.getWidth() / 2) / Tiles.TILE_WIDTH;
            float particleCellY = (float)(particleRect.getY() + particleRect.getHeight() / 2) / Tiles.TILE_WIDTH;

            float minDistance = playerHasTorch ? Main.VIEW_DISTANCE_TORCH_MIN : Main.VIEW_DISTANCE_MIN;
            float maxDistance = playerHasTorch ? Main.VIEW_DISTANCE_TORCH_MAX : Main.VIEW_DISTANCE_MAX;

            particle.opacityMultiplier = Display.calculateCellLight(lightCastingCells, playerPositionX, playerPositionY, particleCellX, particleCellY, minDistance, maxDistance);
        }
    }

    private float frameStep()
    {
        float frameTime = 1f / 50f;
        return frameTime * (1f / duration);
    }

    private float lerp(float a, float b, float t)
    {
        return a + t * (b - a);
    }

    public static float lerpRotation(float a, float b, float t)
    {
        float difference = Math.abs(b - a);
        if (difference > 180) {
            if (b > a)
                a += 360;
            else
                b += 360;
        }

        float value = (a + ((b - a) * t));

        float rangeZero = 360;

        if (value >= 0 && value <= 360)
            return value;

        return (value % rangeZero);
    }

    private OpacityKeyFrame[] addToArray(OpacityKeyFrame[] array, OpacityKeyFrame element)
    {
        OpacityKeyFrame[] result = new OpacityKeyFrame[array.length + 1];
        for (int i=0; i< array.length; i++)
            result[i] = array[i];
        result[array.length] = element;
        return result;
    }
    private RotationKeyFrame[] addToArray(RotationKeyFrame[] array, RotationKeyFrame element)
    {
        RotationKeyFrame[] result = new RotationKeyFrame[array.length + 1];
        for (int i=0; i< array.length; i++)
            result[i] = array[i];
        result[array.length] = element;
        return result;
    }
    private SizeKeyFrame[] addToArray(SizeKeyFrame[] array, SizeKeyFrame element)
    {
        SizeKeyFrame[] result = new SizeKeyFrame[array.length + 1];
        for (int i=0; i< array.length; i++)
            result[i] = array[i];
        result[array.length] = element;
        return result;
    }
    private PositionKeyFrame[] addToArray(PositionKeyFrame[] array, PositionKeyFrame element)
    {
        PositionKeyFrame[] result = new PositionKeyFrame[array.length + 1];
        for (int i=0; i< array.length; i++)
            result[i] = array[i];
        result[array.length] = element;
        return result;
    }
    private IndexKeyFrame[] addToArray(IndexKeyFrame[] array, IndexKeyFrame element)
    {
        IndexKeyFrame[] result = new IndexKeyFrame[array.length + 1];
        for (int i=0; i< array.length; i++)
            result[i] = array[i];
        result[array.length] = element;
        return result;
    }
    private VelocityMultiplierKeyFrame[] addToArray(VelocityMultiplierKeyFrame[] array, VelocityMultiplierKeyFrame element)
    {
        VelocityMultiplierKeyFrame[] result = new VelocityMultiplierKeyFrame[array.length + 1];
        for (int i=0; i< array.length; i++)
            result[i] = array[i];
        result[array.length] = element;
        return result;
    }


    private boolean keyframeExists(float point, KeyFrame[] array)
    {
        if (array.length == 0)
            return false;

        for (int i=0; i< array.length; i++) {
            if (array[i].point == point)
                return true;
        }
        return false;
    }

    private int getKeyframeIndexFromPosition(KeyFrame[] keyFrames, float point)
    {
        if (keyFrames.length <= 1)
            return 0;

        int result = 0;

        for (int i = 0; i<keyFrames.length; i++) {
            if (point >= keyFrames[i].point)
                result = i;
        }

        return result;
    }


    public void addOpacityKeyFrame(float point, float opacity)
    {
        // check false input
        if (point < 0 || point > 1 || opacity < 0 || opacity > 1)
            return;

        if (keyframeExists(point, opacityKeyFrames))
            return;

        opacityKeyFrames = addToArray(opacityKeyFrames, new OpacityKeyFrame(point, opacity));
    }

    public void addSizeKeyFrame(float point, float width, float height)
    {
        addSizeKeyFrame(point, new Dimension(Math.round(width), Math.round(height)));
    }
    public void addSizeKeyFrame(float point, int width, int height)
    {
        addSizeKeyFrame(point, new Dimension(width, height));
    }
    public void addSizeKeyFrame(float point, Dimension size)
    {
        // check false input
        if (point < 0 || point > 1)
            return;

        if (keyframeExists(point, sizeKeyFrames))
            return;

        sizeKeyFrames = addToArray(sizeKeyFrames, new SizeKeyFrame(point, size));
    }

    public void addRotationKeyFrame(float point, float rotation)
    {
        // check false input
        if (point < 0 || point > 1)
            return;

        if (keyframeExists(point, rotationKeyFrames))
            return;

        rotationKeyFrames = addToArray(rotationKeyFrames, new RotationKeyFrame(point, rotation));
    }

    public void addPositionKeyFrame(float point, float x, float y)
    {
        addPositionKeyFrame(point, new Point2D.Double(x, y));
    }
    public void addPositionKeyFrame(float point, Point2D position)
    {
        // check false input
        if (point < 0 || point > 1)
            return;

        if (keyframeExists(point, positionKeyFrames))
            return;

        positionKeyFrames = addToArray(positionKeyFrames, new PositionKeyFrame(point, position));
    }

    public void addIndexKeyFrame(float point, int index)
    {
        if (point < 0 || point > 1 || index < 0 || index >= imageCount())
            return;

        if (keyframeExists(point, indexKeyFrames))
            return;

        indexKeyFrames = addToArray(indexKeyFrames, new IndexKeyFrame(point, index));
    }

    public void addVelocityKeyFrame(float point, float velocity)
    {
        if (point < 0 || point > 1)
            return;

        if (keyframeExists(point, velocityKeyFrames))
            return;

        velocityKeyFrames = addToArray(velocityKeyFrames, new VelocityMultiplierKeyFrame(point, velocity));
    }



    public void autoIndexKeyFrames()
    {
        float distance = 1f / imageCount();

        for (int i=0; i<imageCount(); i++)
        {
            addIndexKeyFrame(distance * i, i);
        }
    }
}

class Particle
{
    public boolean enabled;

    public float progress;

    public float opacity;
    public float opacityMultiplier;
    public float rotation;
    public Dimension size;
    public Point2D position;
    public float velocityMultiplier;

    public int imageIndex;
    public Image image1;
    public Image image2;
    public javafx.scene.image.Image fxImage1;
    public javafx.scene.image.Image fxImage2;
    public float imageLerp;

    public Particle(float progress, float opacity, float rotation, float velocityMultiplier, Dimension size, Point2D position, int imageIndex, boolean enabled)
    {
        this.progress = progress;
        this.opacity = opacity;
        this.rotation = rotation;
        this.size = size;
        this.position = position;
        this.enabled = enabled;
        this.imageIndex = imageIndex;
        this.opacityMultiplier = 1;
        this.velocityMultiplier = velocityMultiplier;
    }
}

class KeyFrame
{
    public float point;
}

class OpacityKeyFrame extends KeyFrame
{
    public float opacity;

    public OpacityKeyFrame(float point, float opacity)
    {
        this.point = point;
        this.opacity = opacity;
    }
}

class SizeKeyFrame extends KeyFrame
{
    public Dimension size;

    public SizeKeyFrame(float point, Dimension size)
    {
        this.point = point;
        this.size = size;
    }
}

class RotationKeyFrame extends KeyFrame
{
    public float rotation;

    public RotationKeyFrame(float point, float rotation)
    {
        this.point = point;
        this.rotation = rotation;
    }

    public float radians()
    {
        return (float)Math.toRadians(rotation);
    }
}

class PositionKeyFrame extends KeyFrame
{
    public Point2D position;

    public PositionKeyFrame(float point, Point2D position)
    {
        this.point = point;
        this.position = position;
    }
}

class IndexKeyFrame extends KeyFrame
{
    public int index;

    public IndexKeyFrame(float point, int index)
    {
        this.point = point;
        this.index = index;
    }
}

class VelocityMultiplierKeyFrame extends KeyFrame
{
    public float velocityMultiplier;

    public VelocityMultiplierKeyFrame(float point, float velocityMultiplier)
    {
        this.point = point;
        this.velocityMultiplier = velocityMultiplier;
    }
}
