package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Golem extends Mob {

    public Golem(int xPos, int yPos) {
        try {
            sprite = new TextureRegion(
                    AssetManagerSingleton.getInstance()
                            .getAssetManager().get("vehicles.png", Texture.class),
                    0, 0, 16, 16
            );
            sprite.setRegion(sprite.getRegionX(), 2 * 16, 16, 16);
        } catch (com.badlogic.gdx.utils.GdxRuntimeException exception) {
            Gdx.app.error(
                    "Golem",
                    "Sprite failed to load; Assuming headless launch",
                    exception
            );
        }

        this.position = new Vector2(xPos, yPos);
        this.hitbox = new Rectangle();
        hitbox.x = position.x;
        hitbox.y = position.y;

        // todo: these are the right pixel values, but we need to do math to position them properly
        hitbox.width = 12f / 16f;
        hitbox.height = 14f / 16f;
        // golem is 12 wide x 14 tall

        //hitbox.width = 1;
        //hitbox.height = 1;

        this.speed = 3.5f;
        this.points = 20;
    }
}
