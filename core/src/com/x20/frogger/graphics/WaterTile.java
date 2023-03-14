package com.x20.frogger.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

public class WaterTile implements TiledMapTile {
    private int id;
    private TextureRegion textureRegion;

    public WaterTile() {
        this.id = 2;
        Texture texture = new Texture(Gdx.files.internal("water.png"));
        this.textureRegion = new TextureRegion(texture);
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public BlendMode getBlendMode() {
        return null;
    }

    @Override
    public void setBlendMode(BlendMode blendMode) {

    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    @Override
    public float getOffsetX() {
        return 0;
    }

    @Override
    public void setOffsetX(float offsetX) {

    }

    @Override
    public float getOffsetY() {
        return 0;
    }

    @Override
    public void setOffsetY(float offsetY) {

    }

    @Override
    public MapProperties getProperties() {
        return null;
    }

    @Override
    public MapObjects getObjects() {
        return null;
    }
}
