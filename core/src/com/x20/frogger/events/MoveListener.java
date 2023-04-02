package com.x20.frogger.events;

import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.events.EventListener;

public class MoveListener implements EventListener {

    public void onMove(MoveEvent moveEvent) {

    }

    public static class MoveEvent implements Event {
        private Vector2 currentPosition;

        /**
         * Event triggered when position changed
         * @param currentPosition new position as a result of a move
         */
        public MoveEvent(Vector2 currentPosition) {
            this.currentPosition = currentPosition.cpy();
        }
    }
}
