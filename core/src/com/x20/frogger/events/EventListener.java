package com.x20.frogger.events;

/**
 * Generic event listener interface
 */
public interface EventListener {
    default void onEvent(Event e) {

    }
    static interface Event {

    }
}
