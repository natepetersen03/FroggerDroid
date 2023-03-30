package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.Controls;
import com.x20.frogger.data.Renderable;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Player extends Entity implements Renderable {

    // todo: animated player sprites
    private TextureRegion playerSprite;
    private Vector2 lastPos;
    private Vector2 targetPos;
    private float lerpDuration = 0.25f; // in seconds
    private float lerpTimer = lerpDuration;

    private Controls.MOVE lastMoveDirection = Controls.MOVE.RIGHT;


    public Player(Vector2 spawnPosition) {
        position = spawnPosition.cpy();
        lastPos = position.cpy();
        targetPos = position.cpy();

        // todo: make a more robust system for determining the player skin
        playerSprite = new TextureRegion(
                AssetManagerSingleton.getInstance()
                        .getAssetManager().get("players.png", Texture.class),
                0, 0, 16, 16
        );
        updatePlayerSprite();
    }

    public Player(float x, float y) {
        this(new Vector2(x, y));
    }

    public Player() {
        this(Vector2.Zero);
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

    // todo: figure out the right way of doing this
    public void animate() {
        // flip x direction based on last horizontal move
        switch (lastMoveDirection) {
        case RIGHT:
            if (playerSprite.isFlipX()) {
                playerSprite.flip(true, false);
            }
            break;
        case LEFT:
            if (!playerSprite.isFlipX()) {
                playerSprite.flip(true, false);
            }
            break;
        default:
            break;
        }
    }

    @Override
    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
    }

    /**
     * Teleport player to a position. Resets targetPos, lastPos, and lerpTimer.
     * Will be clamped to the world
     * @param newPosition
     */
    @Override
    public void setPosition(Vector2 newPosition) {
        position = clampToBounds(
                newPosition,
                Vector2.Zero,
                GameLogic.getInstance().getTileMap().getDimensions()
        );
        lastPos = position.cpy();
        targetPos = position.cpy();
        lerpTimer = lerpDuration;
    }

    /**
     * Sets the target position for the player to interpolate to
     * @param newPosition the position to move to
     */
    public void setTargetPos(Vector2 newPosition) {
        lastPos = position.cpy();
        targetPos = clampToBounds(
                newPosition,
                Vector2.Zero,
                GameLogic.getInstance().getTileMap().getDimensions()
        );
        lerpTimer = 0f;
    }

    public boolean checkBounds(Vector2 location, float xMin, float xMax, float yMin, float yMax) {
        // todo: update these with the proper calls from the tile board
        if (location.x < xMin || location.x > xMax || location.y < yMin || location.y > yMax) {
            return false;
        }
        return true;
    }

    /**
     * Limits coordinates within a rectangular boundary defined by mins, maxes.
     * Does not modify the original vector.
     * @param location
     * @param mins
     * @param maxes
     * @return A new vector within the bounds
     */
    public Vector2 clampToBounds(Vector2 location, Vector2 mins, Vector2 maxes) {
        Vector2 clamped = location.cpy();
        // clamp x
        clamped.x = Math.max(Math.min(clamped.x, maxes.x), mins.x);
        // clamp y
        clamped.y = Math.max(Math.min(clamped.y, maxes.y), mins.y);
        return clamped;
    }

    @Override
    public void update() {
        processInput();
        moveToTarget();
    }

    // possibly replace with an EnumHandler holding the appropriate direction vectors
    public void processInput() {
        if (!InputController.QUEUE_MOVEMENTS.isEmpty()) {
            // only set a new target if we're done moving to the current target
            if (lerpTimer >= lerpDuration) {
                lastMoveDirection = InputController.QUEUE_MOVEMENTS.peek();
                switch (lastMoveDirection) {
                case UP:
                    setTargetPos(getPosition().cpy().add(Vector2.Y));
                    break;
                case DOWN:
                    setTargetPos(getPosition().cpy().sub(Vector2.Y));
                    break;
                case RIGHT:
                    setTargetPos(getPosition().cpy().add(Vector2.X));
                    break;
                case LEFT:
                    setTargetPos(getPosition().cpy().sub(Vector2.X));
                    break;
                default:
                    break;
                }
            }
            InputController.QUEUE_MOVEMENTS.clear();
        }
    }

    private void moveToTarget() {
        if (lerpTimer < lerpDuration) {
            lerpTimer = Math.min(lerpTimer + Gdx.graphics.getDeltaTime(), lerpDuration);
            // snap to target position if lerp timer has reached its duration
            if (lerpTimer == lerpDuration) {
                position = targetPos.cpy();
            } else {
                position = lastPos.cpy().lerp(targetPos, lerpTimer / lerpDuration);
            }
        }
    }

    @Override
    public void render(Batch batch) {
        animate();
        batch.draw(playerSprite, position.x, position.y, 1, 1);
    }
}
