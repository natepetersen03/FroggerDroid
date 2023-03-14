package com.x20.frogger.game;
public class Tile {
    private boolean solid = false;
    private String name;
    private float velocity = 0f;
    private boolean damaging = false;

    public Tile(String tileName) {
        this(tileName, false, false, 0f);
    }

    public Tile(String tileName, boolean isSolid) {
        this(tileName, isSolid, false, 0f);
    }

    public Tile(String tileName, boolean isSolid, boolean isDamaging) {
        this(tileName, isSolid, isDamaging, 0f);
    }

    public Tile(String tileName, boolean isSolid, boolean isDamaging, float xVelocity) {
        this.solid = isSolid;
        this.name = tileName;
        this.velocity = xVelocity;
        this.damaging = isDamaging;
    }
}
