package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.x20.frogger.data.IntervalUpdatable;
import com.x20.frogger.data.Updatable;

import java.util.LinkedList;

public class IntervalTimer implements Updatable {
    private float interval;
    private float timeCounter;
    private boolean running = false;
    LinkedList<IntervalUpdatable> listeners;


    /**
     * Constructor
     * @param interval time in seconds
     */
    public IntervalTimer(float interval)
    {
        this.interval = interval;
        listeners = new LinkedList<>();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public void addListener(IntervalUpdatable listener) {
        listeners.add(listener);
    }

    /**
     * Start/resume countdown. Does not reset time left on countdown
     */
    public void start() {
        running = true;
    }

    /**
     * Pause countdown. Does not reset time left on countdown.
     */
    public void pause() {
        running = false;
    }

    /**
     * Stop interval timer and reset accumulated time
     */
    public void stop() {
        pause();
        reset();
    }

    /**
     * Reset the accumulated time without pausing/resuming
     */
    public void reset() {
        timeCounter = 0;
    }

    /**
     * Reset the accumulated time and start the timer
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
            timeCounter += delta;
            while (timeCounter >= interval) {
                timeCounter -= interval;
                for (IntervalUpdatable listener : listeners) {
                    listener.intervalUpdate();
                }
            }
        }
    }
}
