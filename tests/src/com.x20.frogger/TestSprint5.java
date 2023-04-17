package com.x20.frogger;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.data.DataEnums;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.entities.Player;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
    }

    @Test
    public void startup() {
        assertEquals("Test suite launched successfully", 0, gameLogic.getScore());
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
