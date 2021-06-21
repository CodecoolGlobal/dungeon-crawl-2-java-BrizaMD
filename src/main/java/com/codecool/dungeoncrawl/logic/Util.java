package com.codecool.dungeoncrawl.logic;

import java.util.Random;

public class Util {
    public static float cellDistance(float x1, float y1, float x2, float y2)
    {
        float xDistance = x1 - x2;
        float yDistance = y1 - y2;

        return (float)Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }

    public static float calculateDistanceFade(float distance, float min, float max)
    {
        float maxDelta = max - min;
        float raw = Math.min(maxDelta, Math.max(0, distance - min));

        return 1f - raw / maxDelta;
    }

    public static int randomRange(int from, int to)
    {
        Random r = new Random();
        int low = Math.min(from, to);
        int high = Math.max(from, to);
        return r.nextInt(high-low) + low;
    }

    public static float normalize(float original) {
        if (original == 0) return 0;
        if (original < 0) return -1;
        return 1;
    }

    public static float smoothDamp(float current, float target, float ratio) {
        return smoothDamp(current, target, ratio, 0.001f);
    }
    public static float smoothDamp(float current, float target, float ratio, float minThreshold) {
        if (Math.abs(target - current) <= minThreshold)
            return target;

        return lerp(current, target, ratio);
    }

    public static float lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }
}
