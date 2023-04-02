package com.x20.frogger.utils;

import com.badlogic.gdx.math.Vector2;

import java.text.DecimalFormat;

/**
 * Helper class for printing debug messages and writing to a file
 * https://libgdx.com/wiki/file-handling
 */
public class DebugLog {
    protected static DecimalFormat maxPrecisionFormat =
        new DecimalFormat("0.############################");

    public static DecimalFormat getMaxPrecisionFormat() {
        return maxPrecisionFormat;
    }

    public static String maxPrecisionVector2(Vector2 v) {
        return "(" + maxPrecisionFormat.format(v.x) + ", " +  maxPrecisionFormat.format(v.y) + ")";
    }
}
