package com.x20.frogger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.DataEnums;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.entities.Entity;
import com.x20.frogger.game.entities.Player;
import com.x20.frogger.game.entities.mobs.Creeper;
import com.x20.frogger.game.entities.mobs.Golem;
import com.x20.frogger.game.entities.mobs.Skeleton;
import com.x20.frogger.game.entities.waterentities.Log;
import com.x20.frogger.utils.MiscUtils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

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

        // Create the mock object Graphics
        Graphics g = Mockito.spy(Graphics.class);

        // mock the behavior of stock service to return the value of various stocks
        Mockito.when(g.getDeltaTime()).thenReturn(1f / 60f);

        Gdx.graphics = g;
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
        for (int i = 0; i < gameLogic.getTileMap().getHeight(); i++) {
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
            default:
                break;
            }
        }
    }

    @Test
    public void startup() {
        assertEquals("Test suite launched failed", 0, gameLogic.getScore());
        for (int i = 0; i < 600; i++) {
            float dt = Gdx.graphics.getDeltaTime();
            String formatted = MiscUtils.getMaxPrecisionFormat().format(dt);
            System.out.println("Delta time is: " + formatted);
        }
    }

    @Test
    public void testLogSpeedUnequal() {
        LinkedList<Entity> e1 = gameLogic.getTileMap().getEntitiesAtRow(9);
        LinkedList<Entity> e2 = gameLogic.getTileMap().getEntitiesAtRow(10);
        assertNotEquals(e1.peek().getVelocity().x, e2.peek().getVelocity());
    }

    @Test
    public void testWinTileAward() {
        Player p = gameLogic.getPlayer();
        p.setPosition(0.5f, 11);
        gameLogic.update();
        assertEquals(30, gameLogic.getScore());
        p.setPosition(1.5f, 11);
        gameLogic.update();
        assertEquals(130, gameLogic.getScore());
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

    @Test
    public void testLog10Speed() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(10);
        assertEquals(((Log) (testRow.get(0))).getVelocity().x, 3.0f, 0);
    }

    @Test
    public void testLog9Speed() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(9);
        assertEquals(((Log) (testRow.get(0))).getVelocity().x, -2.0f, 0);
    }

    @Test
    public void testLog8Speed() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(8);
        assertEquals(((Log) (testRow.get(0))).getVelocity().x, 1.0f, 0);
    }

    @Test
    public void testLog9HitBoxWidth() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(9);
        assertEquals(((Log) (testRow.get(0))).getHitbox().getWidth(), 2.0f, 0);
        assertEquals(((Log) (testRow.get(1))).getHitbox().getWidth(), 2.0f, 0);

    }

    @Test
    public void testLog8HitBoxWidth() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(8);
        assertEquals(((Log) (testRow.get(0))).getHitbox().getWidth(), 3.0f, 0);
        assertEquals(((Log) (testRow.get(1))).getHitbox().getWidth(), 3.0f, 0);

    }

    @Test
    public void testLog10HitBoxWidth() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(10);
        assertEquals(((Log) (testRow.get(0))).getHitbox().getWidth(), 1.0f, 0);
        assertEquals(((Log) (testRow.get(1))).getHitbox().getWidth(), 2.0f, 0);
        assertEquals(((Log) (testRow.get(2))).getHitbox().getWidth(), 1.0f, 0);

    }

}
