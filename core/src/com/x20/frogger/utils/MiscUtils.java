package com.x20.frogger.utils;

public class MiscUtils {
    private static float snapToInt(float a, float threshold) {
        return Math.abs(Math.ceil(a) - a) <= threshold
                ? (float) Math.ceil(a)
                : (float) Math.floor(a);
    }
}
