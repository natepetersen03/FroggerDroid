package com.x20.frogger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Vehicle {
    private int velocity;

    private Texture vehicleImage;
    private Rectangle hitbox;

    public Rectangle getHitbox() { return hitbox; }
    public Vehicle(int xPos, int yPos, int height, int width, int velocity) {
        this.velocity = velocity;
        //this.vehicleImage = vehicleImage;

        this.hitbox = new Rectangle();
        hitbox.height = height;
        hitbox.width = width;

        hitbox.x = xPos;
        hitbox.y = yPos;

    }

    public void updatePosition() {
        hitbox.x -= velocity * Gdx.graphics.getDeltaTime();
        if (hitbox.x + hitbox.width < 0) {
            hitbox.x = Gdx.graphics.getWidth();
        }
    }
}
