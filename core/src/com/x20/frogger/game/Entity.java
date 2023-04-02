package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.Debuggable;
import com.x20.frogger.data.Renderable;
import com.x20.frogger.data.Updatable;

public abstract class Entity implements Updatable, Renderable, Debuggable {

    protected Vector2 position = Vector2.Zero;
    protected Vector2 velocity = Vector2.Zero;

    protected Rectangle hitbox;
    protected TextureRegion sprite;

    public void setPosition(float x, float y) {
        position.set(x, y);
        updateHitboxPosition();
    }

    /**
     * Return a copy of this entity's position
     * @return a copy of position
     */
    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(Vector2 newPosition) {
        position = newPosition.cpy();
        updateHitboxPosition();
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
        updateHitboxPosition();
    }

    protected void updateHitboxPosition() {
        hitbox.setPosition(position.x, position.y);
    }

    // todo: use with logs/turtles to see if player would go offscreen when moving
    public boolean checkBounds(Vector2 location, float xMin, float xMax, float yMin, float yMax) {
        // todo: update these with the proper calls from the tile board
        if (location.x < xMin || location.x > xMax || location.y < yMin || location.y > yMax) {
            return false;
        }
        return true;
    }

    /**
     * Limits coordinates within a rectangular boundary defined by mins, maxes.
     * DOES NOT ACCOUNT FOR WIDTH/HEIGHT OF AN ENTITY, if using an entity's position
     * Does not modify the original vector.
     * @param location
     * @param mins
     * @param maxes
     * @return A new vector within the bounds
     */
    public Vector2 clampToBounds(Vector2 location, Vector2 mins, Vector2 maxes) {
        Vector2 clamped = location.cpy();
        // clamp x
        clamped.x = Math.max(Math.min(clamped.x, maxes.x), mins.x);
        // clamp y
        clamped.y = Math.max(Math.min(clamped.y, maxes.y), mins.y);
        return clamped;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Batch batch) {
        animate();
        batch.draw(sprite, position.x, position.y, 1, 1);
    }

    @Override
    public void debug() {

    }

    // todo: figure out the right way of doing this

    public void animate() {
        // flip x direction based on last horizontal move
        if (velocity.x > 0) {
            if (sprite.isFlipX()) {
                sprite.flip(true, false);
            }
        } else {
            if (!sprite.isFlipX()) {
                sprite.flip(true, false);
            }
        }
    }
}
