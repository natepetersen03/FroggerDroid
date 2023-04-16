package com.x20.frogger.game.mobs;

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

    public int getPoints() {
        return points;
    }


    public WaterEntity(Vector2 position, float speed, int points, Rectangle hitbox) {
        this.position = new Vector2(position.x + (width / 2), position.y);
        this.velocity = new Vector2(speed, 0);
        this.points = points;
        this.hitbox = hitbox;
    }

    public void update() {
        updatePos();

        if (position.x < -1 && velocity.x < 0) {
            position.x = GameLogic.getInstance().getTileMap().getWidth() + 1;
        } else if (position.x > GameLogic.getInstance().getTileMap().getWidth() + 1
            && velocity.x > 0
        ) {
            position.x = -1;
        }
    }
}