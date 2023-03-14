package com.x20.frogger.data;

import com.x20.frogger.game.Tile;
import com.x20.frogger.graphics.TileRenderData;

import java.util.HashMap;
import java.util.Map;

public class TileDatabase {

    private static Map<String, TileStruct> database;
    {
        database = new HashMap<String, TileStruct>();
        database.put("road",
            new TileStruct(
                new Tile("road", false),
                new TileRenderData(0, 0)
            ));
    }

    public class TileStruct {
        private Tile tile;
        private TileRenderData renderData;

        public TileStruct(Tile tile, TileRenderData renderData) {
            this.tile = tile;
            this.renderData = renderData;
        }
    }
}
