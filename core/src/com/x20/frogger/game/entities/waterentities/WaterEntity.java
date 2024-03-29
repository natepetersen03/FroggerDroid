package com.x20.frogger.game.entities.waterentities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.game.entities.Entity;
import com.x20.frogger.game.entities.PointEntity;

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
        //this.position = position.cpy();
        this.velocity = new Vector2(speed, 0);
        this.points = points;
        this.hitbox = hitbox;
    }
}