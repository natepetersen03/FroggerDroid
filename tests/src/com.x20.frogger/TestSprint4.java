package com.x20.frogger;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.x20.frogger.data.DataEnums;
import com.x20.frogger.game.entities.Entity;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.entities.Player;
import com.x20.frogger.game.entities.mobs.Creeper;
import com.x20.frogger.game.entities.mobs.Golem;
import com.x20.frogger.game.entities.mobs.Mob;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;


public class TestSprint4 {
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

    //Nate's Tests
    @Test
    public void testScoreLifeLostMedium() {
        GameConfig.setDifficulty(DataEnums.Difficulty.NORMAL);
        gameLogic.playerFail();
        assertEquals(gameLogic.getScore(), 0);
    }

    @Test
    public void testScoreLifeLostEasy() {
        GameConfig.setDifficulty(DataEnums.Difficulty.NORMAL);
        int score = gameLogic.getScore();
        gameLogic.playerFail();
        assertEquals(gameLogic.getScore(), score / 2);
    }

    //Daniel's Tests
    @Test
    public void testCreeperVelocity() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(1);
        assertEquals(((Creeper) (testRow.get(0))).getVelocity().x, 1.5f, 0);
    }

    @Test
    public void testGolemVelocity() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(2);
        assertEquals(((Golem) (testRow.get(0))).getVelocity().x, 1f, 0);
    }

    //Owen's Tests
    @Test
    public void testScoreLifeLostHard() {
        GameConfig.setDifficulty(DataEnums.Difficulty.HARD);
        gameLogic.playerFail();
        assertEquals(gameLogic.getScore(), 0);
    }

    @Test
    public void testCreeperHitboxWidth() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(1);
        assertEquals(testRow.get(0).getHitbox().width, 8f / 16f, 0);
    }

    //Darren's Tests
    @Test
    public void testGolemHitboxWidth() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(2);
        assertEquals(testRow.get(0).getHitbox().width, 12f / 16f, 0);
    }

    @Test
    public void testSkeletonHitboxWidth() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(5);
        assertEquals(testRow.get(0).getHitbox().width, 6f / 16f, 0);
    }

    // Don's Tests
    @Test
    public void testSkeletonVelocity() {
        LinkedList<Entity> testRow = gameLogic.getTileMap().getEntitiesAtRow(5);
        assertEquals(((Mob) (testRow.get(0))).getVelocity().x, -2f, 0);
    }

    @Test
    public void testLifeLostOnDeath() {
        GameConfig.setDifficulty(DataEnums.Difficulty.NORMAL);
        gameLogic.setLives(5);
        gameLogic.playerFail();
        assertEquals(4, gameLogic.getLives());
    }

    @Test
    public void testXPositionResetOnDeath() {
        GameConfig.setDifficulty(DataEnums.Difficulty.NORMAL);
        gameLogic.setLives(5);
        Player player = gameLogic.getPlayer();
        gameLogic.playerFail();
        assertEquals(5.5f, player.getPosition().x, 0);
    }

    @Test
    public void testYPositionResetOnDeath() {
        GameConfig.setDifficulty(DataEnums.Difficulty.NORMAL);
        gameLogic.setLives(5);
        Player player = gameLogic.getPlayer();
        gameLogic.playerFail();
        assertEquals(0, player.getPosition().y, 0);
    }
}