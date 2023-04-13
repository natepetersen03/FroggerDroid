package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Creeper extends Mob {

    public Creeper(int xPos, int yPos) {
        super(new Vector2(xPos, yPos), 1.5f, 10, new Rectangle(xPos, yPos, 8f / 16f, 13f / 16f));
        try {
            sprite = new TextureRegion(
                    AssetManagerSingleton.getInstance()
                            .getAssetManager().get("vehicles.png", Texture.class),
                    0, 0, 16, 16
            );
            sprite.setRegion(sprite.getRegionX(), 0 * 16, 16, 16);
        } catch (com.badlogic.gdx.utils.GdxRuntimeException exception) {
            Gdx.app.error(
                    "Creeper",
                    "Sprite failed to load; Assuming headless launch",
                    exception
            );
        }

    }
}