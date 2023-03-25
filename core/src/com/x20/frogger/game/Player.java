package com.x20.frogger.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.x20.frogger.data.Renderable;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Player extends Entity implements Renderable {

    // todo: animated player sprites
    private TextureRegion playerSprite;

    public Player() {
        // todo: make a more robust system for determining the player skin
        playerSprite = new TextureRegion(
            AssetManagerSingleton.getInstance()
                .getAssetManager().get("players.png", Texture.class),
            0, 0, 16, 16
        );
        updatePlayerSprite();
    }

    // todo: do this without a switch statement
    public void updatePlayerSprite() {
        switch(GameConfig.getCharacter()) {
        case STEVE:
            playerSprite.setRegion(playerSprite.getRegionX(), 0 * 16, 16, 16);
            break;
        case ALEX:
            playerSprite.setRegion(playerSprite.getRegionX(), 1 * 16, 16, 16);
            break;
        case ENDERMAN:
            playerSprite.setRegion(playerSprite.getRegionX(), 2 * 16, 16, 16);
            break;
        default:
            throw new IllegalStateException(
                "Invalid player character (" + GameConfig.getCharacter().toString() + ") selected"
            );
        }
    }

    public boolean checkBounds(float xMin, float xMax, float yMin, float yMax) {
        // todo: update these with the proper calls from the tile board
        if (x < xMin || x > xMax || y < yMin || y > yMax) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        super.update();
    }

    // todo: move to Player.java
    public void processMovement() {
        if (!InputController.QUEUE_MOVEMENTS.isEmpty()) {
            // replace with EnumHandler
            switch (InputController.QUEUE_MOVEMENTS.poll()) {
                default:
                    break;
            }
            InputController.QUEUE_MOVEMENTS.clear();
        }
    }

    @Override
    public void render(Batch batch) {
        batch.draw(playerSprite, x, y, 1, 1);
    }
}
