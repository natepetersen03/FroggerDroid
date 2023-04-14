package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.FroggerDroid;
import com.x20.frogger.data.Controls;
import com.x20.frogger.data.IntervalUpdatable;
import com.x20.frogger.data.Renderable;
import com.x20.frogger.game.mobs.WaterEntity;
import com.x20.frogger.graphics.AssetManagerSingleton;
import com.x20.frogger.utils.MiscUtils;

public class Player extends Entity implements Renderable {
    private Vector2 moveDir;
    private Mover mover;
    private float speed = 10f;
    private float moveDist = 1f;

    private Controls.MOVE moveDirEnum = Controls.MOVE.RIGHT;

    // debug vars
    private IntervalTimer debugTimer;

    /**
     * Creates a new Player, HORIZONTALLY CENTERED at the provided spawn position
     * @param spawnPosition spawn position
     */
    public Player(Vector2 spawnPosition) {
        super();
        position = new Vector2(spawnPosition.x + (width / 2), spawnPosition.y);
        moveDir = Vector2.Zero;
        mover = new Mover(position, speed);
        velocity = new Vector2(0, 0);
        hitbox = new Rectangle(0, 0, 1, 1);
        hitbox.width = 6f / 16f;
        hitbox.height = 15f / 16f;

        // todo: make a more robust system for determining the player skin
        try {
            sprite = new TextureRegion(
                    AssetManagerSingleton.getInstance()
                            .getAssetManager().get("players.png", Texture.class),
                    0, 0, 16, 16
            );
            assignCharacterSprite();
        } catch (com.badlogic.gdx.utils.GdxRuntimeException exception) {
            Gdx.app.error(
                "Player",
                "Sprite failed to load; Assuming headless launch",
                exception
            );
        }

        debugTimer = new IntervalTimer(1f);
        if (FroggerDroid.isFlagDebug()) {
            debugTimer.addListener(new IntervalUpdatable() {
                @Override
                public void intervalUpdate() {
                    debug();
                }
            });
            debugTimer.start();
        }
    }

    public Player(float x, float y) {
        this(new Vector2(x, y));
    }

    /**
     * Get instance of Mover, which drives movement as a result of player input
     * @return Mover
     */
    public Mover getMover() {
        return mover;
    }

    /**
     * Set position, cancelling any moves-in-progress, and clamping it within bounds
     * @param x float
     * @param y float
     */
    @Override
    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
    }

    /**
     * Set position, cancelling any moves-in-progress, and clamping it within bounds
     * @param newPosition Vector2
     */
    @Override
    public void setPosition(Vector2 newPosition) {
        mover.cancel(newPosition);
        position = newPosition.cpy();
        stayInBounds();
        super.updateHitboxPosition();
    }

    /**
     * Teleport to a given position without any additional processing or affecting the mover
     * @param newPosition Vector2
     */
    public void setPositionRaw(Vector2 newPosition) {
        position = newPosition.cpy();
        super.updateHitboxPosition();
    }


    public void setMoveDist(float dist) {
        moveDist = dist;
    }

    /**
     * Change how fast the player moves due to player input. Also affects the move input cooldown.
     * @param speed float
     */
    public void setMoveSpeed(float speed) {
        this.speed = speed;
        mover.setMoveDurationFromSpeed(speed);
    }

    /**
     * Update player position based on base velocity and currently queued movement
     */
    @Override
    protected void updatePos() {
        // Base velocity
        Vector2 deltaPosFromVel = velocity.cpy().scl(Gdx.graphics.getDeltaTime());
        position.add(deltaPosFromVel);
        // Shift mover's origin and target to account for new position due to base velocity
        mover.addDelta(deltaPosFromVel);

        // Player input-based movement
        if (mover.isMoving()) {
            position = mover.moveToTarget(Gdx.graphics.getDeltaTime());
        }

        // Bounds restriction
        stayInBounds();

        super.updateHitboxPosition();
    }

    private void stayInBounds() {
        position = clampToBounds(
            position,
            new Vector2(width / 2, 0),
            new Vector2(
                GameLogic.getInstance().getTileMap().getWidth() - 1 + (width / 2),
                GameLogic.getInstance().getTileMap().getHeight() - 1
            )
        );
    }

    public void glueToLog(WaterEntity entity) {
        velocity = (entity.getVelocity());
    }

    public void processInput() {
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
        if (debugTimer.isRunning()) {
            debugTimer.update();
        }
        processInput();
        updatePos();
    }


    // todo: do this without a switch statement
    public void assignCharacterSprite() {
        switch (GameConfig.getCharacter()) {
        case STEVE:
            sprite.setRegion(sprite.getRegionX(), 0 * 16, 16, 16);
            break;
        case ALEX:
            sprite.setRegion(sprite.getRegionX(), 1 * 16, 16, 16);
            break;
        case ENDERMAN:
            sprite.setRegion(sprite.getRegionX(), 2 * 16, 16, 16);
            break;
        default:
            throw new IllegalStateException(
                "Invalid player character (" + GameConfig.getCharacter().toString() + ") selected"
            );
        }
    }

    // todo: figure out the right way of doing this
    @Override
    public void animate() {
        // flip x direction based on last horizontal move
        switch (moveDirEnum) {
        case RIGHT:
            if (sprite.isFlipX()) {
                sprite.flip(true, false);
            }
            break;
        case LEFT:
            if (!sprite.isFlipX()) {
                sprite.flip(true, false);
            }
            break;
        default:
            break;
        }
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
    }

    @Override
    public void debug() {
        Gdx.app.debug("Player", "Position: " + MiscUtils.maxPrecisionVector2(position));
    }

}
