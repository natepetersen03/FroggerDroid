package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.Updatable;

public abstract class Entity implements Updatable {

    protected Vector2 position = Vector2.Zero;
    protected Vector2 velocity = Vector2.Zero;
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(Vector2 newPosition) {
        position = newPosition.cpy();
    }

    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public void setVelocity(Vector2 newVel) {
        velocity = newVel.cpy();
    }

    public void addVelocity(Vector2 delta) {
        velocity.add(delta.cpy());
    }

    public void subtractVelocity(Vector2 delta) {
        velocity.sub(delta.cpy());
    }

    protected void updatePos() {
        position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
    }

    @Override
    public void update() {

    }
}
