package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.FroggerDroid;
import com.x20.frogger.data.Controls;
import com.x20.frogger.data.Debuggable;
import com.x20.frogger.data.Renderable;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Player extends Entity implements Renderable, Debuggable {

    // todo: animated player sprites
    private TextureRegion playerSprite;
    // todo: player hitbox
    private Vector2 moveDir;
    private Mover mover;
    private float speed = 10f;
    private float moveDist = 2f;

    private Controls.MOVE moveDirEnum = Controls.MOVE.RIGHT;

    // debug vars
    private Countdown debugTimer = new Countdown(.5f);

    public Player(Vector2 spawnPosition) {
        super();
        position = spawnPosition.cpy();
        moveDir = Vector2.Zero;
        mover = new Mover(position, 1f/speed);
        velocity = new Vector2(0, 0);

        // todo: make a more robust system for determining the player skin
        try {
            playerSprite = new TextureRegion(
                    AssetManagerSingleton.getInstance()
                            .getAssetManager().get("players.png", Texture.class),
                    0, 0, 16, 16
            );
            updatePlayerSprite();
        } catch (com.badlogic.gdx.utils.GdxRuntimeException exception) {
            System.err.println("Sprite failed to load; Assuming headless launch.");
        }

        if (FroggerDroid.isFlagDebug()) {
            debugTimer.start();
        }
    }

    public Player(float x, float y) {
        this(new Vector2(x, y));
    }

    public Player() {
        this(Vector2.Zero);
    }

    public Mover getMover() {
        return mover;
    }

    @Override
    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
    }

    @Override
    public void setPosition(Vector2 newPosition) {
        mover.cancel(newPosition);
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

    /**
     * Teleport to a given position without any additional processing or affecting the mover
     * @param newPosition new position
     */
    public void setPositionRaw(Vector2 newPosition) {
        position = newPosition.cpy();
    }

    @Override
    protected void updatePos() {
        Vector2 deltaPosFromVel = velocity.cpy().scl(Gdx.graphics.getDeltaTime());
        position.add(deltaPosFromVel);
        mover.addDelta(deltaPosFromVel);

        if (mover.isMoving()) {
            position = mover.moveToTarget(Gdx.graphics.getDeltaTime());
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
                moveDirEnum = InputController.QUEUE_MOVEMENTS.peek();
                moveDir = moveDirEnum.getDirection().cpy().scl(moveDist);
                mover.setOriginPos(position);
                mover.deriveTargetPos(moveDir);
                mover.startMoving();
            }
            InputController.QUEUE_MOVEMENTS.clear();
        }
    }

    @Override
    public void update() {
        debug();
        processInput();
        updatePos();
    }

    // todo: do this without a switch statement
    public void updatePlayerSprite() {
        switch (GameConfig.getCharacter()) {
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
        switch (moveDirEnum) {
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

    @Override
    public void debug() {
        if (FroggerDroid.isFlagDebug()) {
            if (!debugTimer.isRunning()) {
                debugTimer.restart();

            } else {
                debugTimer.update();
            }
        }
    }
}
