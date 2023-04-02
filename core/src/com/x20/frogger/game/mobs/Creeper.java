package com.x20.frogger.game.mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Creeper extends Mob {

    public Creeper(Vector2 position) {
        entitySprite = new TextureRegion(
                AssetManagerSingleton.getInstance()
                        .getAssetManager().get("vehicles.png", Texture.class),
                0, 0, 16, 16
        );
        entitySprite.setRegion(entitySprite.getRegionX(), 0 * 16, 16, 16);

        this.position = position;
        this.hitbox = new Rectangle();
        hitbox.x = position.x;
        hitbox.y = position.y;

        hitbox.width = entitySprite.getRegionWidth();
        hitbox.height = entitySprite.getRegionWidth();

        this.speed = 10;
    }
}
