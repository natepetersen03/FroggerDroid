package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.graphics.AssetManagerSingleton;


public class Log extends WaterEntity {

    protected int length = 1;

    public Log(int xPos, int yPos, float speed, int length) {
        super(new Vector2(xPos, yPos), speed, 30, new Rectangle(xPos, yPos, (16f / 16f) * length, 16f / 16f));
        this.length = length;
        try {
            sprite = new TextureRegion(
                    AssetManagerSingleton.getInstance()
                            .getAssetManager().get("logs/oak_log.png", Texture.class),
                    0, 0, 16 * length, 16
            );
        } catch (com.badlogic.gdx.utils.GdxRuntimeException exception) {
            Gdx.app.error(
                    "Log",
                    "Sprite failed to load; Assuming headless launch"
            );
        }
    }
}
