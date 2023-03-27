package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.Renderable;

public class Vehicle extends Entity implements Renderable {

    private TextureRegion vehicleSprite;
    private float velocity;
    private Rectangle hitbox;

    public Vehicle(Vector2 spawnPosition) {
        position = spawnPosition.cpy();
        
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
    public Vehicle(int xPos, int yPos, int height, int width, int velocity) {
        this.velocity = velocity;
        this.hitbox = buildHitbox(height, width, xPos, yPos);

    }

    public Rectangle buildHitbox(int height, int width, int xPos, int yPos) {
        Rectangle newHitbox = new Rectangle();
        newHitbox.height = height;
        newHitbox.width = width;

        newHitbox.x = xPos;
        newHitbox.y = yPos;
        return newHitbox;
    }

    @Override
    public void update() {
        position.x += velocity * Gdx.graphics.getDeltaTime();

        // not sure what this does
        //hitbox.x -= velocity * Gdx.graphics.getDeltaTime();
        //if (hitbox.x + hitbox.width < 0) {
        //    hitbox.x = Gdx.graphics.getWidth();
        //}
    }

    @Override
    public void render(Batch b) {
        b.draw(vehicleSprite, position.x, position.y, 1, 1);
    }
}
