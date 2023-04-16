package com.x20.frogger.game.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.graphics.AssetManagerSingleton;


public class Log extends WaterEntity {

    protected int length = 1;
    private final static int LOG_SPRITE_HEIGHT = 12;

    /*
    Note: I made a weird decision that the logs are "Centered" at the bottom-center of the left-most
    "tile" of the log. This means hitbox calculations have to be made differently, as do position wrapping calculations.
    It's all quite nasty but it makes spawning them in the tilemap a lot nicer.
     */

    public Log(int xPos, int yPos, float speed, int length) {
        super(new Vector2(xPos, yPos), speed, 30, new Rectangle(xPos, yPos, length, 1));
        this.length = length;
        updateHitboxPosition();
        try {
            sprite = new TextureRegion(
                    AssetManagerSingleton.getInstance()
                            .getAssetManager().get("logs/oak_log.png", Texture.class),
                    0, 0, 16 * length, LOG_SPRITE_HEIGHT
            );
        } catch (com.badlogic.gdx.utils.GdxRuntimeException exception) {
            Gdx.app.error(
                    "Log",
                    "Sprite failed to load; Assuming headless launch"
            );
        }
    }

    @Override
    protected void updateHitboxPosition() {
        float x = position.x - (width / 2);
        float y = position.y;
        hitbox.setPosition(x, y);
    }

    @Override
    public void render(Batch batch) {
        animate();
        float x = position.x - (width / 2);
        //float y = position.y + ((16f - LOG_SPRITE_HEIGHT) / 32f);
        float y = position.y;
        batch.draw(sprite, x, y, width * length, LOG_SPRITE_HEIGHT / 16f);
    }

    @Override
    public void update() {
        updatePos();
        if (position.x < (0 - length) && velocity.x < 0) {
            position.x = GameLogic.getInstance().getTileMap().getWidth() + 1;
        } else if (position.x > GameLogic.getInstance().getTileMap().getWidth() + 1
                && velocity.x > 0
        ) {
            position.x = (0 - length);
        }
    }
}
