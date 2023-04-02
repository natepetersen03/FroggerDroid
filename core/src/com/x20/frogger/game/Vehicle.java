package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.DataEnums;
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
    public Vehicle(int xPos, int yPos, DataEnums.VehicleType type) {
        position.x = xPos;
        position.y = yPos;
        setVehicleType(type);
    }

    public void setVehicleType(DataEnums.VehicleType type) {
        switch(type) {
            case CREEPER:
                vehicleSprite.setRegion(vehicleSprite.getRegionX(), 0 * 16, 16, 16);
                velocity = -20;
                break;
            case IRON_GOLEM:
                vehicleSprite.setRegion(vehicleSprite.getRegionX(), 1 * 16, 16, 16);
                velocity = -10;
                break;
            case SKELETON:
                vehicleSprite.setRegion(vehicleSprite.getRegionX(), 2 * 16, 16, 16);
                velocity = -30;
                break;
            default:
                throw new IllegalStateException(
                        "Invalid vehicle type (" + GameConfig.getCharacter().toString() + ") provided"
                );
        }
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
