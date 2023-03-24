package com.x20.frogger.game;

import com.x20.frogger.data.Updatable;

public class Player extends Entity {

    public boolean checkBounds(float xMin, float xMax, float yMin, float yMax) {
        // todo: update these with the proper calls from the tile board
        if (x < xMin || x > xMax || y < yMin || y > yMax) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        super.update();
    }
}
