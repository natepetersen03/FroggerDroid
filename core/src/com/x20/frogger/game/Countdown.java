package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.x20.frogger.data.Updatable;

public class Countdown implements Updatable {
    private float duration;
    private float timeLeft;
    private boolean running = false;

    public Countdown(float duration)
    {
        this(duration, duration);
    }

    public Countdown(float duration, float startTime)
    {
        this.duration = duration;
        this.timeLeft = startTime;
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

    public boolean isRunning() {
        return running;
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
     * Start/resume countdown. Does not reset time left on countdown
     */
    public void start() {
        running = true;
    }

    /**
     * Stop/pause countdown. Does not reset time left on countdown.
     */
    public void pause() {
        running = false;
    }

    /**
     * Reset the time left on the countdown without pausing/resuming
     */
    public void reset() {
        timeLeft = duration;
    }

    /**
     * Reset and start countdown
     */
    public void restart() {
        reset();
        start();
    }

    @Override
    public void update() {
        update(Gdx.graphics.getDeltaTime());
    }

    public void update(float delta) {
        if (running) {
            timeLeft = Math.max(timeLeft - delta, 0);
            if (timeLeft == 0) {
                running = false;
            }
        }
    }
}
