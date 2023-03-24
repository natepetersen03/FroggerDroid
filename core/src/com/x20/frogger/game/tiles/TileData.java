package com.x20.frogger.game.tiles;
public class TileData {
    private boolean solid = false;
    private String name;
    private float velocity = 0f;
    private boolean damaging = false;

    public TileData(String tileName) {
        this(tileName, false, false, 0f);
    }

    public TileData(String tileName, boolean isSolid) {
        this(tileName, isSolid, false, 0f);
    }

    public TileData(String tileName, boolean isSolid, boolean isDamaging) {
        this(tileName, isSolid, isDamaging, 0f);
    }

    public TileData(String tileName, boolean isSolid, boolean isDamaging, float xVelocity) {
        this.solid = isSolid;
        this.name = tileName;
        this.velocity = xVelocity;
        this.damaging = isDamaging;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public boolean isDamaging() {
        return damaging;
    }

    public void setDamaging(boolean damaging) {
        this.damaging = damaging;
    }
}
