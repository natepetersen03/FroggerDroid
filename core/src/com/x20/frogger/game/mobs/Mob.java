package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.x20.frogger.game.Entity;
import com.x20.frogger.game.GameLogic;

// todo: update to use Entity's velocity Vector instead of speed
// for individual mob types,
// speed is determined by concrete Mob implementations
// concrete Mob implementations should take a moveDir vector with their constructor
// to determine velocity of the Mob
public abstract class Mob extends Entity implements PointEntity {
    protected int points;
    protected float speed;

    public float getSpeed() { return speed; }
    public int getPoints() { return points; }

    public void update() {
        position.x += speed * Gdx.graphics.getDeltaTime();

        if (position.x < -1 && speed < 0) {
            position.x = GameLogic.getInstance().getTileMap().getWidth() + 1;
        } else if (position.x > GameLogic.getInstance().getTileMap().getWidth() + 1 && speed > 0) {
            position.x = -1;
        }
        hitbox.x = position.x;


    }

    @Override
    public void animate() {
        // flip x direction based on last horizontal move
        if (speed > 0) {
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