package com.x20.frogger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.DataEnums;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.entities.Player;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.entities.Entity;
import com.x20.frogger.game.entities.Player;
import com.x20.frogger.game.entities.mobs.Creeper;
import com.x20.frogger.game.entities.mobs.Golem;
import com.x20.frogger.game.entities.mobs.Skeleton;
import com.x20.frogger.game.entities.waterentities.Log;
import com.x20.frogger.game.tiles.TileMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedList;

public class TestSprint5 {
    private GameLogic gameLogic;
    private static HeadlessApplication app;
    private static HeadlessApplicationConfiguration appConfig;

    @BeforeClass
    public static void launchHeadless() {
        // the following snippet starts the headless backend so we can
        // still use gdx calls without launching the entire GUI app
        // https://stackoverflow.com/questions/42252209/is-there-any-way-to-create-integration-test-for-libgdx-application
        appConfig = new HeadlessApplicationConfiguration();
        appConfig.updatesPerSecond = 60;
        app = new HeadlessApplication(new FroggerDroid(), appConfig);
    }

    @Before
    public void setup() {
        gameLogic = GameLogic.getInstance();
        gameLogic.newGame();
        loadPregeneratedLevel();
    }

    public void loadPregeneratedLevel() {
        /// Generate tiles
        String[] worldString = new String[] {
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
        gameLogic.getTileMap().generateTileMapFromStringArray(worldString);

        // Populate entities
        populateEntities();

        /// Player init
        gameLogic.getPlayer().setPosition(gameLogic.getTileMap().getWidth() / 2, 0);

        // Lives and score init
        try {
            gameLogic.setLives(GameConfig.getDifficulty().getLives());
        } catch (NullPointerException e) {
            Gdx.app.error("GameLogic", "Difficulty is null; assuming unit test mode");
        }
        Gdx.app.log("TestSprint5", "Loaded pregenerated map");
    }

    public void populateEntities() {
        for (int i = 0; i < gameLogic.getTileMap().getHeight(); i++)
        {
            LinkedList<Entity> list = gameLogic.getTileMap().getEntitiesAtRow(i);
            list.clear();
            switch (i) {
            // mobs
            case 1:
                list.add(new Creeper(0, 1));
                list.add(new Creeper(5, 1));
                break;
            case 2:
                list.add(new Golem(0, 2));
                list.add(new Golem(5, 2));
                break;
            case 4:
                list.add(new Creeper(0, 4));
                list.add(new Creeper(5, 4));
                break;
            case 5:
                list.add(new Skeleton(0, 5));
                list.add(new Skeleton(5, 5));
                break;
            case 6:
                list.add(new Golem(0, 6));
                list.add(new Golem(10, 6));
                break;
            // water entities
            case 8:
                list.add(new Log(3, 8, 1, 3, 0));
                list.add(new Log(9, 8, 1, 3, 0));
                break;
            case 9:
                list.add(new Log(0, 9, -2, 2, 1));
                list.add(new Log(6, 9, -2, 2, 1));
                break;
            case 10:
                list.add(new Log(0, 10, 3, 1, 2));
                list.add(new Log(4, 10, 3, 2, 2));
                list.add(new Log(10, 10, 3, 1, 2));
                break;
            }
        }
    }

    @Test
    public void startup() {
        assertEquals("Test suite launched failed", 0, gameLogic.getScore());
    }

    @Test
    public void checkLogBypassWaterKillTrigger() {
        gameLogic.getPlayer().setPosition(3, 8);
        gameLogic.update();
        // todo
    }

    @Test
    public void testPlayerLivesOnLog() {
        GameConfig.setDifficulty(DataEnums.Difficulty.NORMAL);
        gameLogic.setLives(5);
        Player player = gameLogic.getPlayer();
        player.setPosition(0, 9); // set position onto pre-determined log
        gameLogic.update();
        assertEquals(5, gameLogic.getLives());
    }

    @Test
    public void testPlayerPositionOnLog() {
        GameConfig.setDifficulty(DataEnums.Difficulty.NORMAL);
        gameLogic.setLives(5);
        Player player = gameLogic.getPlayer();
        Vector2 resetPosition = new Vector2(gameLogic.getTileMap().getWidth() / 2
                + (player.getWidth() / 2), 0);
        player.setPosition(0, 9); // set position onto pre-determined log
        gameLogic.update();
        assertNotEquals(resetPosition, player.getPosition());
    }
}
