package com.x20.frogger.events;

/**
 * Generic event listener interface
 */
public interface EventListener {
    default void onEvent(Event e) {
        throw new RuntimeException("Event " + e.toString() + " caught, but not handled");
    }
    static interface Event {

    }
}
