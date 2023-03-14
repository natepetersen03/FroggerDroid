package com.x20.frogger.graphics;

public class TileRenderData {
    private int textureX = 0;
    private int textureY = 0;
    private int width = 16;
    private int height = 16;
    private int frames = 1;
    private int fps = 12;

    public TileRenderData(int textureX, int textureY) {
        this(textureX, textureY, 16, 16, 1, 0);
    }

    public TileRenderData(int textureX, int textureY, int frames, int fps) {
        this(textureX, textureY, 16, 16, frames, fps);
    }

    /**
     * Data about how to render a given tile
     * @param textureX x coordinate in the tile texture atlas to use
     * @param textureY y coordinate in the tile texture atlas to use
     * @param width width of the tile, should always be 16px
     * @param height height of the tile, should always be 16px
     * @param frames how many frames in a row this sprite has. set to 1 for a static image
     * @param fps how fast an animated tile is. set to 0 for a static image
     */
    public TileRenderData(int textureX, int textureY, int width, int height, int frames, int fps) {
        this.textureX = Math.max(textureX, 0);
        this.textureY = Math.max(textureY, 0);
        this.width = Math.max(width, 0);
        this.height = Math.max(height, 0);
        this.frames = Math.max(frames, 1);
        this.fps = Math.max(fps, 0);
    }
}
