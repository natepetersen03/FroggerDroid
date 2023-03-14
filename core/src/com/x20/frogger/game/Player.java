package com.x20.frogger.game;

import com.x20.frogger.data.Updatable;

public class Player implements Updatable {
    private float x = 0;
    private float y = 0;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float[] getPos() {
        return new float[] {x, y};
    }

    // todo: move to Player.java
    public boolean checkBounds(float xMin, float xMax, float yMin, float yMax) {
        // todo: update these with the proper calls from the tile board
        if (x < xMin || x > xMax || y < yMin || y > yMax) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {

    }
}
