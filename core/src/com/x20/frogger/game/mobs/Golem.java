package com.x20.frogger.game.mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Golem extends Mob{

    public Golem(int xPos, int yPos) {
        sprite = new TextureRegion(
                AssetManagerSingleton.getInstance()
                        .getAssetManager().get("vehicles.png", Texture.class),
                0, 0, 16, 16
        );
        sprite.setRegion(sprite.getRegionX(), 2 * 16, 16, 16);

        this.position = new Vector2(xPos, yPos);
        this.hitbox = new Rectangle();
        hitbox.x = position.x;
        hitbox.y = position.y;

        hitbox.width = sprite.getRegionWidth();
        hitbox.height = sprite.getRegionWidth();

        this.speed = -2;
        this.points = 20;
    }
}
