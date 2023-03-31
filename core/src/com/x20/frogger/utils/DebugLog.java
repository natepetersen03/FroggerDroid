package com.x20.frogger.utils;

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
}
