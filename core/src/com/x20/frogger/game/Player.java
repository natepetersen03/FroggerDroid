package com.x20.frogger.game;

import com.x20.frogger.data.Updatable;

public class Player implements Updatable {
    private int x = 0;
    private int y = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getPos() {
        return new int[] {x, y};
    }

    @Override
    public void update() {

    }
}
