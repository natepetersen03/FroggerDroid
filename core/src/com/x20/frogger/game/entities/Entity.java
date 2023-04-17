package com.x20.frogger.game.entities;

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

    // kind of a jank solution for centering entities
    protected float width = 1;
    protected float height = 1;

    protected Rectangle hitbox;
    protected TextureRegion sprite;

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

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
        float x = position.x - (hitbox.width / 2);
        float y = position.y;
        hitbox.setPosition(x, y);
    }

    // todo: use with logs/turtles to see if player would go offscreen when moving

    /**
     * Check if a given vector is within defined bounds
     * @param location vector to check
     * @param xMin x min
     * @param xMax x max
     * @param yMin y min
     * @param yMax y max
     * @return true if within bounds, false if out of bounds
     */
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
     * @param location location
     * @param mins mins
     * @param maxes maxes
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

    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Batch batch) {
        animate();
        float x = position.x - (width / 2);
        float y = position.y;
        batch.draw(sprite, x, y, width, height);
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
