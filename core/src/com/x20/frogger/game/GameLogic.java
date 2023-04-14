package com.x20.frogger.game;

import com.badlogic.gdx.Gdx;
import com.x20.frogger.FroggerDroid;
import com.x20.frogger.events.GameStateListener;
import com.x20.frogger.game.mobs.PointEntity;
import com.x20.frogger.game.tiles.TileDatabase;
import com.x20.frogger.game.tiles.TileMap;

import java.util.LinkedList;

public class GameLogic {
    private static GameLogic instance;
    private boolean isRunning = false;

    private Player player;
    private final int defaultPoints = 5;
    private int lives; // todo: consider moving this to Player
    private int score = 0;
    private int yMax = 0;

    private LinkedList<GameStateListener> gameStateListeners = new LinkedList<>();

    private TileMap tileMap;
    private String[] worldString;

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public TileMap getTileMap() {
        return tileMap;
    }



    private GameLogic() {
        Gdx.app.log("GameLogic", "Initializing GameLogic...");

        // init TileDatabase
        TileDatabase.initDatabase();
    }

    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
            Gdx.app.log("GameLogic", "Singleton initialized");
        }
        return instance;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void newGame() {
        // todo: other logic for New Game initialization

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
        this.player = new Player(tileMap.getWidth() / 2, 0);
        // todo: specify a spawn tile position in the TileMap

        // Lives and score init
        setLives(GameConfig.getDifficulty().getLives());
        score = 0;
        yMax = 0;

        isRunning = true;
        Gdx.app.log("GameLogic", "Game started");
    }

    public void addGameStateListener(GameStateListener listener) {
        gameStateListeners.add(listener);
    }

    public void update() {
        if (!isRunning) {
            return;
        }

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

        updateScore(false);
        // todo: test extensively. possibility that floating point errors might cause this to fail
        if (!FroggerDroid.isFlagInvulnerable()) {
            checkForDamagingTile((int) player.position.x, (int) player.position.y);
            checkForDamagingEntities((int) player.position.y);
        }

        checkGoal((int) player.getPosition().x, (int) player.getPosition().y);
    }

    // todo: this would probably be something the Player does in its own update method
    // we can use custom events that the GUI elements are subscribed to
    // then when we fire the events, we can notify the subscribers to update
    // see: https://programming.guide/java/create-a-custom-event.html

    public void updateScore(boolean respawned) {
        int lastScore = score;
        if (!respawned) {
            int y = (int) (Math.floor(player.getPosition().y));
            if (y > yMax) {
                yMax = y;
                Entity rowEntity = tileMap.getEntitiesAtRow(yMax - 1).peek();
                if (rowEntity instanceof PointEntity) {
                    score += ((PointEntity) rowEntity).getPoints();
                } else {
                    score += defaultPoints;
                }
            }
        } else {
            yMax = 0;
            switch (GameConfig.getDifficulty()) {
            case HARD:
                score = 0;
                break;
            case NORMAL:
                score = 0;
                break;
            default:
                score = 0;
                break;
            }
        }

        // Only send the event if it actually changes, otherwise this is just as inefficient,
        // if not more, than polling for score every frame
        if (score != lastScore) {
            for (GameStateListener listener : gameStateListeners) {
                listener.onScoreUpdate(new GameStateListener.ScoreEvent());
            }
        }
    }

    public void checkGoal(int x, int y) {
        if (tileMap.getTile(x, y).getTileData().getName().equals("goal")) {
            playerWin();
        }
    }

    public void checkForDamagingTile(int x, int y) {
        if (tileMap.getTile(x, y).getTileData().isDamaging()) {
            playerFail();
        }
    }

    public void checkForDamagingEntities(int y) {
        for (Entity entity : tileMap.getEntitiesAtRow(y)) {
            if (player.getHitbox().overlaps(entity.getHitbox())) {
                playerFail();
            }
        }
    }

    public boolean isDead() {
        return (this.lives == 0);
    }

    public void setLives(int lives) {
        if (this.lives != lives) {
            this.lives = lives;
            // notify whoever wants to know about this
            for (GameStateListener listener : gameStateListeners) {
                listener.onLivesUpdate(new GameStateListener.LivesEvent());
            }
        }
    }

    public void respawnPlayer() {
        player.setPosition(tileMap.getWidth() / 2, 0);
    }

    public void playerFail() {
        setLives(this.lives - 1);
        if (this.lives > 0) {
            updateScore(true);
            respawnPlayer();
        } else {
            endGame(false);
        }
    }

    public void playerWin() {
        // for now, just end the game with a win
        endGame(true);
    }

    public void endGame(boolean playerWon) {
        for (GameStateListener listener : gameStateListeners) {
            listener.onGameEnd(new GameStateListener.GameEndEvent(playerWon));
        }
        isRunning = false;
        Gdx.app.log("GameLogic", "Game ended");
    }
}
