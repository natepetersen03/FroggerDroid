package com.x20.frogger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

public class Vehicle {
    private int velocity;

    private Texture vehicleImage;

    public  Texture getVehicleImage() { return vehicleImage; }
    private Rectangle hitbox;

    public Rectangle getHitbox() { return hitbox; }
    public Vehicle(int xPos, int yPos, int height, int width, int velocity, Texture vehicleImage) {
        this.velocity = velocity;
        this.vehicleImage = vehicleImage;

        this.hitbox = buildHitbox(height, width, xPos, yPos);

    }

    public Rectangle buildHitbox(int height, int width, int xPos, int yPos) {
        Rectangle newHitbox = new Rectangle();
        hitbox.height = height;
        hitbox.width = width;

        hitbox.x = xPos;
        hitbox.y = yPos;
        return newHitbox;
    }

    public void updatePosition() {
        hitbox.x -= velocity * Gdx.graphics.getDeltaTime();
        if (hitbox.x + hitbox.width < 0) {
            hitbox.x = Gdx.graphics.getWidth();
        }
    }
}
