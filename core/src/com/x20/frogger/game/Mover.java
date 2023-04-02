package com.x20.frogger.game;

import com.badlogic.gdx.math.Vector2;

public class Mover  {
    private Vector2 targetPos;
    private Vector2 originPos;
    private Countdown timing;

    public Mover(Vector2 initialPos, float speed) {
        originPos = initialPos.cpy();
        targetPos = initialPos.cpy();
        timing = new Countdown(1f / speed);
    }

    public void setMoveDurationFromSpeed(float speed) {
        timing.setDuration(1f / speed);
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