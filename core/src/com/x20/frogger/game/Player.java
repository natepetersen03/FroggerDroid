package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.Controls;
import com.x20.frogger.data.Renderable;
import com.x20.frogger.data.Updatable;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Player extends Entity implements Renderable {

    // todo: animated player sprites
    private TextureRegion playerSprite;
    // todo: player hitbox
    private Vector2 moveDir;
    private Countdown moveTimer;

    private Controls.MOVE lastMoveDirection = Controls.MOVE.RIGHT;


    public Player(Vector2 spawnPosition) {
        super();
        position = spawnPosition.cpy();
        moveTimer = new Countdown(0.25f);
        moveDir = Vector2.Zero;

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

    @Override
    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
        position = clampToBounds(
                position,
                Vector2.Zero,
                new Vector2(
                        GameLogic.getInstance().getTileMap().getWidth() - 1,
                        GameLogic.getInstance().getTileMap().getHeight() - 1
                )
        );
    }

    @Override
    protected void updatePos() {
        super.updatePos();
        position = clampToBounds(
                position,
                Vector2.Zero,
                new Vector2(
                        GameLogic.getInstance().getTileMap().getWidth() - 1,
                        GameLogic.getInstance().getTileMap().getHeight() - 1
                )
        );
    }

    private void updateVel() {
        if (moveTimer.isRunning()) {
            velocity = moveDir.cpy().scl(1/moveTimer.getDuration());
        } else {
            velocity = Vector2.Zero;
        }
    }


    // todo: use with logs/turtles to see if player would go offscreen when moving
    public boolean checkBounds(Vector2 location, float xMin, float xMax, float yMin, float yMax) {
        // todo: update these with the proper calls from the tile board
        if (location.x < xMin || location.x > xMax || location.y < yMin || location.y > yMax) {
            return false;
        }
        return true;
    }

    /**
     * Limits coordinates within a rectangular boundary defined by mins, maxes.
     * DOES NOT ACCOUNT FOR WIDTH/HEIGHT OF AN ENTITY, if using an entity's position
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

    private void processInput() {
        if (!InputController.QUEUE_MOVEMENTS.isEmpty()) {
            // only process if we're not currently moving from a previous input
            if (!moveTimer.isRunning()) {
                lastMoveDirection = InputController.QUEUE_MOVEMENTS.peek();
                moveDir = lastMoveDirection.getDirection();
                moveTimer.reset();
                moveTimer.start();
            }
            InputController.QUEUE_MOVEMENTS.clear();
        }
    }

    @Override
    public void update() {
        moveTimer.update();
        processInput();
        updateVel();
        updatePos();
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
    public void render(Batch batch) {
        animate();
        batch.draw(playerSprite, position.x, position.y, 1, 1);
    }

    private class Mover implements Updatable {
        private float distance = 0f;
        private float targetDistance = 0f;
        private Countdown countdown;

        public Mover(float targetDistance, float moveTime) {
            this.targetDistance = targetDistance;
            this.countdown = new Countdown(moveTime);
            countdown.start();

        }

        /**
         * Determine how much of the move distance to actually use when updating position
         * @param delta distance moved
         * @return amount of delta needed to not go over the target distance
         */
        public float move(float delta) {
            delta = Math.abs(delta);
            distance += delta;
            if (distance <= targetDistance) {
                return delta;
            } else {
                countdown.pause();
                return (delta - (distance - targetDistance));
            }
        }

        @Override
        public void update() {
            countdown.update();
        }
    }

}
