package com.x20.frogger.game;

import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.GameScreen;
import com.x20.frogger.game.tiles.TileDatabase;
import com.x20.frogger.game.tiles.TileMap;

public class GameLogic {
    private static GameLogic instance;

    private Player player;
    private int score = 0;

    private int lives;
    private TileMap tileMap;
    private String[] worldString;

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public int getLives() { return lives; }

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
        checkWaterCollision();
    }
    public boolean checkGoal() {
        int checkX = (int) player.position.x;
        int checkY = (int) player.position.y - tileMap.getHeight() + 1;
        int goalRow = 0;
        System.out.println(checkX + " " + checkY);
        if (goalRow == checkY && worldString[goalRow].charAt(checkX) == 'g') {
            return true;
        }
        return false;
    }

    public void checkWaterCollision() {
        int checkX = (int) player.position.x;
        int checkY = (int) (tileMap.getHeight() - 1 - player.position.y);

        System.out.println(checkX + " " + checkY);
        if (worldString[checkY].charAt(checkX) == 'w') {
            this.lives -= 1;
            this.score = 0;
            player.setPosition(new Vector2(tileMap.getWidth() / 2,0));
        }
    }

    public boolean checkLives(){
        if (this.lives == 0) {
            return true;
        }
        return false;
    }

    public void setLives(String lives) {
        int temp = Integer.parseInt(lives);
        this.lives = temp;
    }
}
