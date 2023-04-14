package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.game.Entity;
import com.x20.frogger.game.GameLogic;

// todo: update to use Entity's velocity Vector instead of speed
// for individual mob types,
// speed is determined by concrete Mob implementations
// concrete Mob implementations should take a moveDir vector with their constructor
// to determine velocity of the Mob
public abstract class Mob extends Entity implements PointEntity {
    protected int points;

    public int getPoints() {
        return points;
    }

    /**
     * Creates a new Mob, HORIZONTALLY CENTERED at the provided spawn position
     * @param spawnPosition
     */
    public Mob(Vector2 spawnPosition, float speed, int points, Rectangle hitbox) {
        this.position = new Vector2(spawnPosition.x + (width / 2), spawnPosition.y);
        this.velocity = new Vector2(speed, 0);
        this.points = points;
        this.hitbox = hitbox;
    }

    public void update() {
        super.updatePos();

        if ((position.x + width / 2) < 0 && velocity.x < 0) {
            position.x = GameLogic.getInstance().getTileMap().getWidth() + (width / 2);
        } else if ((position.x - (width / 2)) > GameLogic.getInstance().getTileMap().getWidth() && velocity.x > 0) {
            position.x = 0 - (width / 2);
        }
    }
}