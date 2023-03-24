package com.x20.frogger.game.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class TileRenderData {
    private int textureX = 0;
    private int textureY = 0;
    private int width = 16;
    private int height = 16;
    private int frames = 1;
    private int fps = 12;

    private TextureRegion tileTextureRegion;

    public TileRenderData(int textureX, int textureY) {
        this(textureX, textureY, 16, 16, 1, 0);
    }

    public TileRenderData(int textureX, int textureY, int frames, int fps) {
        this(textureX, textureY, 16, 16, frames, fps);
    }

    /**
     * Data about how to render a given tile
     * @oaram packedTileTexture image containing all tile textures in one
     * @param textureX x coordinate in the tile texture atlas to use
     * @param textureY y coordinate in the tile texture atlas to use
     * @param width width of the tile, should always be 16px
     * @param height height of the tile, should always be 16px
     * @param frames how many frames in a row this sprite has. set to 1 for a static image
     * @param fps how fast an animated tile is. set to 0 for a static image
     */
    public TileRenderData(int textureX, int textureY, int width, int height, int frames, int fps) {
        //this.tileTextureRegion = new TextureRegion(
        //        AssetManagerSingleton.getInstance().getAssetManager().get("tiles.png", Texture.class),
        //        textureX, textureY, width, height
        //);
        this.textureX = Math.max(textureX, 0);
        this.textureY = Math.max(textureY, 0);
        this.width = Math.max(width, 0);
        this.height = Math.max(height, 0);
        this.frames = Math.max(frames, 1);
        this.fps = Math.max(fps, 0);
    }

    public int getTextureX() {
        return textureX;
    }

    public void setTextureX(int textureX) {
        this.textureX = textureX;
        updateTextureRegion();
    }

    public int getTextureY() {
        return textureY;
    }

    public void setTextureY(int textureY) {
        this.textureY = textureY;
        updateTextureRegion();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        updateTextureRegion();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        updateTextureRegion();
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    private void updateTextureRegion() {
        tileTextureRegion.setRegion(textureX, textureY, width, height);
    }
    public TextureRegion getTextureRegion() {
        return tileTextureRegion;
    }
}
