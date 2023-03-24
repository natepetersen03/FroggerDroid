package com.x20.frogger.game;

import com.x20.frogger.data.Updatable;

public abstract class Entity implements Updatable {

    protected float x = 0;
    protected float y = 0;

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

    @Override
    public void update() {

    }
}
