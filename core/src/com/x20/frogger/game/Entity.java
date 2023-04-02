package com.x20.frogger.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.Updatable;

import javax.swing.OverlayLayout;

public abstract class Entity implements Updatable {

    protected Vector2 position = Vector2.Zero;

    protected Vector2 velocity;

    protected Rectangle hitbox;

    protected TextureRegion entitySprite;
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void setPosition(Vector2 newPosition) {
        position = newPosition.cpy();
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public Rectangle getHitbox() { return hitbox; }

    @Override
    public void update() {

    }
}
