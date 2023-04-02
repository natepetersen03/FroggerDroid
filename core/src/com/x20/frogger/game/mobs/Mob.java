package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.game.Entity;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.tiles.Tile;
import com.x20.frogger.game.tiles.TileMap;

public class Mob extends Entity implements pointEntity  {
    int points;
    double speed;

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
}