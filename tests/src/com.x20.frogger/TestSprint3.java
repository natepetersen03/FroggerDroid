package com.x20.frogger;

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

    @Test
    public void testGenerateIronGolemVelocity() {
        int vel = GameScreen.getVehicleVelocity(DataEnums.VehicleType.IRON_GOLEM);
        assertEquals(30, vel);
    }

    @Test
    public void testGenerateCreeperVelocity() {
        int vel = GameScreen.getVehicleVelocity(DataEnums.VehicleType.CREEPER);
        assertEquals(130, vel);
    }

    @Test
    public void testGenerateSkeletonVelocity() {
        int vel = GameScreen.getVehicleVelocity(DataEnums.VehicleType.SKELETON);
        assertEquals(80, vel);
    }

    @Test
    public void testGenerateSkeletonSpacing() {
        int vel = GameScreen.getVehicleSpacing(DataEnums.VehicleType.SKELETON);
        assertEquals(4, vel);
    }

    @Test
    //Daniel tests
    public void testGenerateCreeperSpacing() {
        int spacing = GameScreen.getVehicleSpacing(DataEnums.VehicleType.CREEPER);
        assertEquals(6, spacing);
    }

    @Test
    public void testGenerateIronGolemSpacing() {
        int spacing = GameScreen.getVehicleSpacing(DataEnums.VehicleType.IRON_GOLEM);
        assertEquals(7, spacing);
    }
}
