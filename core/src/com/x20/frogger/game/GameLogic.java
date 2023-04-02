package com.x20.frogger.game;

import com.x20.frogger.game.tiles.TileDatabase;
import com.x20.frogger.game.tiles.TileMap;

public class GameLogic {
    private static GameLogic instance;

    private Player player;
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
        // consider building a World class that holds spawn information for both player and vehicles
        // as well as score information
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
        this.player = new Player(tileMap.getWidth() / 2,0);
        // todo: specify a spawn tile position in the TileMap


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

        player.update();
        for (int i = 0; i < tileMap.getEntities().size(); i++) {
            for (Entity entity:
                 tileMap.getEntities().get(i)) {
                entity.update();
            }
        }
    }
}
