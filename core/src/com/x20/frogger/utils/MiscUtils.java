package com.x20.frogger.utils;

import com.badlogic.gdx.math.Vector2;

import java.text.DecimalFormat;

public class MiscUtils {
    private static float snapToInt(float a, float threshold) {
        return Math.abs(Math.ceil(a) - a) <= threshold
                ? (float) Math.ceil(a)
                : (float) Math.floor(a);
    }

    protected static DecimalFormat maxPrecisionFormat =
            new DecimalFormat("0.############################");

    public static DecimalFormat getMaxPrecisionFormat() {
        return maxPrecisionFormat;
    }

    public static String maxPrecisionVector2(Vector2 v) {
        return "(" + maxPrecisionFormat.format(v.x) + ", " +  maxPrecisionFormat.format(v.y) + ")";
    }
}
