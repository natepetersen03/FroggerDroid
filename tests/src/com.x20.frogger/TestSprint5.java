package com.x20.frogger;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.x20.frogger.game.GameLogic;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
