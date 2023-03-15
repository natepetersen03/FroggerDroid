package com.x20.frogger.tests;

import org.junit.Test;

import static org.junit.Assert.*;

import com.x20.frogger.GameScreen;
import com.x20.frogger.data.DataEnums;

import junit.runner.Version;

public class Test_Sprint3 {
    @Test
    public void testGenerateIronGolemWidth() {
        int width = GameScreen.getVehicleWidth(DataEnums.VehicleType.IRON_GOLEM);
        assertEquals(140, width);
    }

    @Test
    public void testGenerateCreeperWidth() {
        int width = GameScreen.getVehicleWidth(DataEnums.VehicleType.CREEPER);
        assertEquals(60, width);
    }

    //GenerateSkeletonWidth = 110

    //GenerateIronGolemVelocity = 30
    //GenerateCreeperVelocity = 130
    //GenerateSkeletonVelocity = 80

    //GenerateIronGolemSpacing = 7
    //GenerateCreeperSpacing = 6
    //GenerateSkeletonSpacing = 4
}
