package com.x20.frogger.game.mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Skeleton extends Mob {
    public Skeleton(int xPos, int yPos) {
        
        sprite = new TextureRegion(
                AssetManagerSingleton.getInstance()
                        .getAssetManager().get("vehicles.png", Texture.class),
                0, 0, 16, 16
        );
        sprite.setRegion(sprite.getRegionX(), 1 * 16, 16, 16);

        this.position = new Vector2(xPos, yPos);
        this.hitbox = new Rectangle();
        hitbox.x = position.x;
        hitbox.y = position.y;

        // todo: these are the right pixel values, but we need to do math to position them properly
        hitbox.width = 6f/16f;
        hitbox.height = 15f/16f;

//        hitbox.width = 1f;
//        hitbox.height = 1f;

        this.speed = -2f;
        this.points = 30;
    }
}
