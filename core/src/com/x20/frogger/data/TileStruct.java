package com.x20.frogger.data;

import com.x20.frogger.game.Tile;
import com.x20.frogger.graphics.TileRenderData;

public class TileStruct {
    private Tile tile;
    private TileRenderData renderData;

    public TileStruct(Tile tile, TileRenderData renderData) {
        this.tile = tile;
        this.renderData = renderData;
    }

    public Tile getTile() {
        return tile;
    }

    public TileRenderData getRenderData() {
        return renderData;
    }
}