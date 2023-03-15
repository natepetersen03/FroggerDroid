package com.x20.frogger.tests;

import static org.junit.Assert.assertEquals;

import com.x20.frogger.GameScreen;
import com.x20.frogger.data.DataEnums;

import org.junit.Test;

public class TestSprint3 {
    // Owen's tests
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

    //Evan's Tests
    @Test
    public void testGenerateIronGolem() {
        DataEnums.VehicleType test = GameScreen.generateVehicleType(1);
        assertEquals(DataEnums.VehicleType.IRON_GOLEM, test);
    }

    @Test
    public void testGenerateCreeper() {
        DataEnums.VehicleType test = GameScreen.generateVehicleType(2);
        assertEquals(DataEnums.VehicleType.CREEPER, test);
    }

    // Donald's Tests
    @Test
    public void testGenerateSkeleton() {
        DataEnums.VehicleType test = GameScreen.generateVehicleType(3);
        assertEquals(test, DataEnums.VehicleType.SKELETON);
    }

    @Test
    public void testGenerateSkeletonWidth() {
        int width = GameScreen.getVehicleWidth(DataEnums.VehicleType.SKELETON);
        assertEquals(110, width);
    }

    //GenerateIronGolemVelocity = 30
    //GenerateCreeperVelocity = 130
    //GenerateSkeletonVelocity = 80

    //GenerateIronGolemSpacing = 7
    //GenerateCreeperSpacing = 6
    //GenerateSkeletonSpacing = 4
}
