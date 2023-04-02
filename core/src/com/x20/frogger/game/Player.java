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
    private float speed = 4f;

    private Controls.MOVE lastMoveDirection = Controls.MOVE.RIGHT;

    // debug vars
    private Countdown debugTimer = new Countdown(.5f);

    public Player(Vector2 spawnPosition) {
        super();
        position = spawnPosition.cpy();
        moveDir = Vector2.Zero;
        mover = new Mover(1f/speed);
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

        // todo: DOESN'T WORK. Need to find root of floating point errors
        // If it's close to being on the next tile, round it up
        // (within 0.1 pixels (0.00625 units))
        // We need to deal with floating point error accumulation anyway
        // position.x = snapToInt(position.x, 0.00625f);
        // position.y = snapToInt(position.y, 0.00625f);

        position = clampToBounds(
            position,
            Vector2.Zero,
            new Vector2(
                GameLogic.getInstance().getTileMap().getWidth() - 1,
                GameLogic.getInstance().getTileMap().getHeight() - 1
            )
        );
    }

    private float snapToInt(float a, float threshold) {
        return Math.abs(Math.ceil(a) - a) <= threshold
            ? (float) Math.ceil(a)
            : (float) Math.floor(a);
    }

    private void processInput() {
        if (!InputController.QUEUE_MOVEMENTS.isEmpty()) {
            // only process if we're not currently moving from a previous input
            if (!mover.isMoving()) {
                moveDir = InputController.QUEUE_MOVEMENTS.peek().getDirection();
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

    public class Mover  {
        private Vector2 targetPos;
        private Vector2 originPos;
        private Countdown timing;

        public Mover(float moveDuration) {
            originPos = position.cpy();
            targetPos = position.cpy();
            timing = new Countdown(moveDuration);
        }

        public Vector2 getTargetPos() {
            return targetPos;
        }

        public void setTargetPos(Vector2 targetPos) {
            this.targetPos = targetPos.cpy();
        }

        public void deriveTargetPos(Vector2 moveDir) {
            targetPos = originPos.cpy().add(moveDir);
        }

        public Vector2 getOriginPos() {
            return originPos;
        }

        public void setOriginPos(Vector2 originPos) {
            this.originPos = originPos.cpy();
        }

        public Countdown getTiming() {
            return timing;
        }

        public boolean isMoving() {
            return timing.isRunning();
        }

        public void startMoving() {
            timing.start();
        }

        public void addDelta(Vector2 delta) {
            originPos.add(delta);
            targetPos.add(delta);
        }


        /**
         * Cancels the current move attempt
         * @param pos if not null, sets origin and target pos to this pos. else, resets target to origin
         */
        public void cancel(Vector2 pos) {
            if (pos != null) {
                originPos = pos.cpy();
                targetPos = pos.cpy();
            } else {
                targetPos = originPos.cpy();
            }
            timing.reset();
            timing.pause();
        }

        public void cancel() {
            cancel(null);
        }

        public Vector2 moveToTarget(float delta) {
            timing.update(delta);
            Vector2 fracPos;
            if (timing.getTimeLeft() == 0) {
                fracPos = targetPos.cpy();
                originPos = targetPos.cpy();
                timing.reset();
            } else {
                float frac = 1f - (timing.getTimeLeft() / timing.getDuration());
                fracPos = originPos.cpy().lerp(targetPos, frac);
            }
            return fracPos;
        }
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
