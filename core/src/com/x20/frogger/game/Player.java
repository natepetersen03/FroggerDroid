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
    // todo: player hitbox
    private Vector2 moveDir;
    private Mover mover;
    private float speed = 4f;

    private Controls.MOVE lastMoveDirection = Controls.MOVE.RIGHT;


    public Player(Vector2 spawnPosition) {
        super();
        position = spawnPosition.cpy();
        moveDir = Vector2.Zero;
        mover = new Mover(1);
        velocity = new Vector2(0, 0);

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
    }

    @Override
    public void setPosition(Vector2 newPosition) {
        mover.setMoving(false);
        mover.resetAccumulator();
        position = newPosition.cpy();
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
        position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));

        if (mover.isMoving()) {
            float delta = speed * Gdx.graphics.getDeltaTime();
            delta = mover.accumulate(delta);
            Vector2 deltaVec = moveDir.cpy().scl(delta);
            position.add(deltaVec);
        }

        position = clampToBounds(
            position,
            Vector2.Zero,
            new Vector2(
                GameLogic.getInstance().getTileMap().getWidth() - 1,
                GameLogic.getInstance().getTileMap().getHeight() - 1
            )
        );
    }

    private void processInput() {
        if (!InputController.QUEUE_MOVEMENTS.isEmpty()) {
            // only process if we're not currently moving from a previous input
            if (!mover.isMoving()) {
                mover.resetAccumulator();
                lastMoveDirection = InputController.QUEUE_MOVEMENTS.peek();
                moveDir = lastMoveDirection.getDirection();
                mover.setMoving(true);
            }
            InputController.QUEUE_MOVEMENTS.clear();
        }
    }

    @Override
    public void update() {
        processInput();
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

    private class Mover  {
        private boolean moving = false;
        private float distance = 0;
        private float targetDistance = 1;

        public Mover(float targetDistance) {
            this.targetDistance = targetDistance;
        }

        public void setTargetDisplacement(float target) {
            targetDistance = target;
        }

        public void resetAccumulator() {
            distance = 0;
            moving = false;
        }

        public void setMoving(boolean moving) {
            this.moving = moving;
        }

        public boolean isMoving() {
            return this.moving;
        }

        public float accumulate(float delta) {
            moving = true;
            distance += delta;
            if (distance >= targetDistance) {
                moving = false;
            }
            if (distance <= targetDistance) {
                return delta;
            } else {
                return delta - (distance - targetDistance);
            }
        }
    }
}
