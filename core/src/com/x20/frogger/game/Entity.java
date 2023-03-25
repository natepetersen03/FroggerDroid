package com.x20.frogger.game;

import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.Updatable;

public abstract class Entity implements Updatable {

    protected Vector2 position = Vector2.Zero;
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void setPosition(Vector2 newPosition) {
        position = newPosition.cpy();
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    @Override
    public void update() {

    }
}
