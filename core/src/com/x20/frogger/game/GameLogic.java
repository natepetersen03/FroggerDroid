package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.x20.frogger.game.mobs.PointEntity;
import com.x20.frogger.game.tiles.TileDatabase;
import com.x20.frogger.game.tiles.TileMap;
import com.x20.frogger.graphics.AssetManagerSingleton;

import java.util.LinkedList;
import java.util.List;

public class GameLogic {
    private static GameLogic instance;

    private Player player;
    private final int DEFAULT_POINTS = 5;
    private int score = 0;
    private int yMax = 0;

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
//        Gdx.app.log("GameLogic", "Initializing GameLogic...");

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

        // Populate entities
        tileMap.generateMobs();

        /// Player init
        this.player = new Player(tileMap.getWidth() / 2,0);
        // todo: specify a spawn tile position in the TileMap


    }

    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
            Gdx.app.log("GameLogic", "Singleton initialized");
        }
        return instance;
    }

    public void update() {
        // steps:
        // 1. update input (handled by GUI in GameScreen.java)
        // 2. update player
        // 3. update world (entities)

        player.update();
        for (int i = 0; i < tileMap.getHeight(); i++) {
            for (Entity entity : tileMap.getEntitiesAtRow(i)) {
                 entity.update();
            }
        }
        // todo: test extensively. possibility that floating point errors might cause this to fail
        checkForDamagingTile((int) player.position.x, (int) player.position.y);
        updateScore();
        checkForDamagingEntities((int) player.position.y);
    }

    // todo: this would probably be something the Player does in its own update method
    // we can use custom events that the GUI elements are subscribed to
    // then when we fire the events, we can notify the subscribers to update
    // see: https://programming.guide/java/create-a-custom-event.html

    public void updateScore() {
        int y = (int) (Math.floor(player.getPosition().y));
        if (y > yMax) {
            yMax = y;
            Entity rowEntity = tileMap.getEntitiesAtRow(yMax-1).peek();
            if (rowEntity instanceof PointEntity) {
                score += ((PointEntity) rowEntity).getPoints();
            } else {
                score += DEFAULT_POINTS;
            }
        }
    }

    public boolean checkGoal(int x, int y) {
        // todo: test extensively. possibility that floating point errors might cause this to fail
        if (tileMap.getTile(x, y).getTileData().getName().equals("goal")) {
            return true;
        }
        return false;
    }

    public void checkForDamagingTile(int x, int y) {
        // todo: test extensively. possibility that floating point errors might cause this to fail
        if (tileMap.getTile(x, y).getTileData().isDamaging()) {
            playerFail();
        }
    }
    public void checkForDamagingEntities (int y) {
        for (Entity entity : tileMap.getEntitiesAtRow(y)) {
            if (player.getHitbox().overlaps(entity.getHitbox())) {
                playerFail();
            }
        }
    }

    public boolean isDead() { return (this.lives == 0); }

    public void setLives(int lives) { this.lives = lives; }

    public void respawnPlayer() {
        player.setPosition(tileMap.getWidth() / 2, 0);
    }

    public void playerFail() {
        this.lives -= 1;
        this.yMax = 0;
        switch (GameConfig.getDifficulty()) {
            case HARD:
                this.score = 0;
                break;
            case NORMAL:
                this.score = 0;
                break;
            default:
                this.score = 0;
                break;
        }
        respawnPlayer();
    }
}
