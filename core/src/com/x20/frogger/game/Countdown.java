package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.x20.frogger.data.Updatable;

public class Countdown implements Updatable {
    private float duration;
    private float timeLeft;
    private boolean running = false;

    public Countdown(float duration)
    {
        this.duration = duration;
    }

    /**
     * Get duration
     * @return seconds
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Get time remaining
     * @return seconds
     */
    public float getTimeLeft() {
        return timeLeft;
    }

    /**
     * Manually set the time left
     * @param time seconds
     */
    public void setTimeLeft(float time) {
        this.timeLeft = time;
    }

    /**
     * Change countdown duration
     * @param duration seconds
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * Reset timeLeft and resumes countdown
     */
    public void start() {
        timeLeft = duration;
        running = true;
    }

    /**
     * Stops countdown from running
     */
    public void pause() {
        running = false;
    }

    /**
     * Lets countdown continue to run
      */
    public void resume() {
        running = true;
    }

    /**
     * Reset the time left on the countdown without pausing/resuming
     */
    public void reset() {
        timeLeft = duration;
    }

    /**
     * Reset time left and pause the countdown
     */
    public void stop() {
        timeLeft = duration;
        running = false;
    }

    @Override
    public void update() {
        if (running) {
            timeLeft = Math.max(timeLeft - Gdx.graphics.getDeltaTime(), 0);
            if (timeLeft == 0) {
                running = false;
            }
        }
    }
}
