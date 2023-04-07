package com.x20.frogger.game.tiles;

public class TileDatabaseInitializationException extends RuntimeException {
    public TileDatabaseInitializationException() {
        this("Tile Database has not been initialized");
    }

    public TileDatabaseInitializationException(String message) {
        super(message);
    }
}
