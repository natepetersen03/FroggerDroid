package com.x20.frogger.data;

import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.Tile;
import com.x20.frogger.graphics.TileRenderData;

import java.util.HashMap;
import java.util.Map;

public class TileDatabase {

    private static TileDatabase instance;
    private static Map<String, TileStruct> database;
    {
        database = new HashMap<String, TileStruct>();
        database.put("road",
            new TileStruct(
                new Tile("road", false),
                new TileRenderData(0, 0)
            ));
        database.put("water",
            new TileStruct(
                new Tile("water", false, true, 0.1f),
                new TileRenderData(0, 0)
            ));
        database.put("safe",
            new TileStruct(
                new Tile("safe", false, false, 0.1f),
                new TileRenderData(0, 0)
            ));
        database.put("safe",
            new TileStruct(
                new Tile("safe", false, false, 0.1f),
                new TileRenderData(0, 0)
            ));
    }

    private TileDatabase() {
        System.out.println("Tile database initialized");
    }

    public static synchronized TileDatabase getInstance() {
        if (instance == null) {
            instance = new TileDatabase();
        }
        return instance;
    }
}
