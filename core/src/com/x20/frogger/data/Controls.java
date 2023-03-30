package com.x20.frogger.data;

import com.badlogic.gdx.math.Vector2;

public class Controls {
    public enum MOVE {
        RIGHT(new Vector2(1, 0)),
        UP(new Vector2(0, 1)),
        LEFT(new Vector2(-1, 0)),
        DOWN(new Vector2(0, 0-1));

        private final Vector2 direction;
        private MOVE(Vector2 direction) {
            this.direction = direction;
        }

        public Vector2 getDirection() {
            return direction;
        }
    }

    public enum GAME {
        GAME_PAUSE
    }
}
