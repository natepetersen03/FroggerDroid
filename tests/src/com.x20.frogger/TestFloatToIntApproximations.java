package com.x20.frogger;

import com.x20.frogger.utils.MiscUtils;

import org.junit.Test;

import java.util.Arrays;

public class TestFloatToIntApproximations {
    public int[] floatToInt(float a, float threshold) {
        int i1 = Math.round(a);
        int i2 = (int) a;
        int i3 = (int) Math.floor(a);
        int i4 = Math.abs(Math.floor(a) - a) <= threshold ? (int) Math.floor(a) : (int) Math.ceil(a);

        int i5 = Math.abs(Math.ceil(a) - a) <= threshold ? (int) Math.ceil(a) : (int) Math.floor(a);
        return new int[] {i1, i2, i3, i4, i5}; // i5 is the one we'll use
    }

    @Test
    public void testValues() {
        // approximate, within 0.1 pixels (0.00625 units)

        System.out.println(11.99999904632568359375f);
        System.out.println(String.format("%.20f", 11.99999904632568359375f));

        System.out.println(MiscUtils.getMaxPrecisionFormat().format(11.99999904632568359375f));


        System.out.println("Close to 1 and -1");
        System.out.println("0.9:" + Arrays.toString(floatToInt(0.9f, 0.00625f)));
        System.out.println("0.99:" + Arrays.toString(floatToInt(0.99f, 0.00625f)));
        System.out.println("0.999:" + Arrays.toString(floatToInt(0.999f, 0.00625f)));
        System.out.println("-0.9:" + Arrays.toString(floatToInt(-0.9f, 0.00625f)));
        System.out.println("-0.99:" + Arrays.toString(floatToInt(-0.99f, 0.00625f)));
        System.out.println("-0.999:" + Arrays.toString(floatToInt(-0.999f, 0.00625f)));

        System.out.println("Close to 0");
        System.out.println("0.1:" + Arrays.toString(floatToInt(0.1f, 0.00625f)));
        System.out.println("0.01:" + Arrays.toString(floatToInt(0.01f, 0.00625f)));
        System.out.println("0.001:" + Arrays.toString(floatToInt(0.001f, 0.00625f)));
        System.out.println("-0.1:" + Arrays.toString(floatToInt(-0.1f, 0.00625f)));
        System.out.println("-0.01:" + Arrays.toString(floatToInt(-0.01f, 0.00625f)));
        System.out.println("-0.001:" + Arrays.toString(floatToInt(-0.001f, 0.00625f)));

        System.out.println("Exactly +-0.5f");
        System.out.println("0.5:" + Arrays.toString(floatToInt(0.5f, 0.00625f)));
        System.out.println("-0.5:" + Arrays.toString(floatToInt(-0.5f, 0.00625f)));

    }
}
