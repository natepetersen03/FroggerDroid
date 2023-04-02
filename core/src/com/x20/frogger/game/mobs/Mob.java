package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.x20.frogger.game.Entity;

public class Mob extends Entity implements PointEntity {
    int points;
    float speed;

    public int getPoints() { return points; }

    public void update() {
        position.x += speed * Gdx.graphics.getDeltaTime();
        hitbox.x = position.x;
    }
}
