package com.x20.frogger.game;

import com.x20.frogger.game.tiles.TileDatabase;
import com.x20.frogger.game.tiles.TileMap;

public class GameLogic {
    private static GameLogic instance;

    private Player player = new Player();
    private int score = 0;
    private TileMap tileMap;
    private String[] worldString;

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    private GameLogic() {
        System.out.println("GameLogic singleton initialized");

        // init TileDatabase
        TileDatabase.initDatabase();

        /// Generate tiles
        // todo: random level generation/selection from pre-made levels based on difficulty?
        // possibly add vertical scrolling if the level is very tall
        worldString = new String[] {
            "sgsgsgsgsgs",
            "wwwwwwwwwww",
            "wwwwwwwwwww",
            "wwwwwwwwwww",
            "sssssssssss",
            "rrrrrrrrrrr",
            "rrrrrrrrrrr",
            "rrrrrrrrrrr",
            "sssssssssss",
            "rrrrrrrrrrr",
            "rrrrrrrrrrr",
            "sssssssssss"
        };
        tileMap = new TileMap();
        tileMap.generateTileMapFromStringArray(worldString);

        /// Player init
        this.player = new Player();
    }

    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public void update() {
        // steps:
        // 1. update input (handled by GUI in GameScreen.java)
        // 2. update player
        // 3. update world (entities)


    }
}
