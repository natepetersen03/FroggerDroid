package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Skeleton extends Mob {
    public Skeleton(int xPos, int yPos) {
        super(new Vector2(xPos, yPos), -2f, 20, new Rectangle(xPos, yPos, 6f / 16f, 15f / 16f));
        try {
            sprite = new TextureRegion(
                    AssetManagerSingleton.getInstance()
                            .getAssetManager().get("vehicles.png", Texture.class),
                    0, 0, 16, 16
            );
            sprite.setRegion(sprite.getRegionX(), 2 * 16, 16, 16);
        } catch (com.badlogic.gdx.utils.GdxRuntimeException exception) {
            Gdx.app.error(
                    "Skeleton",
                    "Sprite failed to load; Assuming headless launch",
                    exception
            );
        }
    }
}
