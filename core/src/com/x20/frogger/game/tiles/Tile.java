package com.x20.frogger.game.tiles;

public class Tile {
    private TileData tileData;
    private TileRenderData renderData;

    public Tile(TileData tileData, TileRenderData renderData) {
        this.tileData = tileData;
        this.renderData = renderData;
    }

    public TileData getTileData() {
        return tileData;
    }

    public TileRenderData getRenderData() {
        return renderData;
    }
}