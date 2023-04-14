package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.game.Entity;
import com.x20.frogger.game.GameLogic;

// todo: update to use Entity's velocity Vector instead of speed
// for individual mob types,
// speed is determined by concrete Mob implementations
// concrete Mob implementations should take a moveDir vector with their constructor
// to determine velocity of the Mob
public abstract class WaterEntity extends Entity implements PointEntity {
    protected int points;
    protected float speed;

    public int yScale;

    public float getSpeed() {
        return speed;
    }

    public int getPoints() {
        return points;
    }


    public WaterEntity(Vector2 position, float speed, int points, Rectangle hitbox, int yScale) {
        this.position = position;
        this.speed = speed;
        this.points = points;
        this.hitbox = hitbox;
        this.yScale = yScale;
    }

    public void update() {
        position.x += speed * Gdx.graphics.getDeltaTime();

        if (position.x < -1 && speed < 0) {
            position.x = GameLogic.getInstance().getTileMap().getWidth() + 1;
        } else if (position.x > GameLogic.getInstance().getTileMap().getWidth() + 1 && speed > 0) {
            position.x = -1;
        }
        hitbox.x = position.x;


    }

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

    @Override
    public void render(Batch batch) {
        animate();
        float x = position.x;
        float y = position.y;
        batch.draw(sprite, x, y + (yScale/2)/16f, 1 + (2/16f), (16f - yScale)/16f);
    }
}