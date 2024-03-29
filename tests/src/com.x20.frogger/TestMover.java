package com.x20.frogger;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Vector2;
import com.x20.frogger.game.Mover;
import com.x20.frogger.game.entities.Player;
import com.x20.frogger.utils.MiscUtils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// Headless backend implementation from
// https://stackoverflow.com/questions/42252209/is-there-any-way-to-create-integration-test-for-libgdx-application


public class TestMover {
    private Player player;
    private static HeadlessApplication application;

    @BeforeClass
    public static void initHeadless() {
        application = new HeadlessApplication(new FroggerDroid());
    }

    @Before
    public void init() {
        player = new Player(0, 0);
    }

    @Test
    public void test() {
        Mover mover = player.getMover();
        float moveSpeed = 1f;
        Vector2 moveDir = Vector2.X.scl(moveSpeed);
        float[] deltas = new float[] {0.0166666667f, 0.0181818182f, 0.0172413793f, 0.0161290323f};
        int i = 0;
        mover.deriveTargetPos(moveDir);
        mover.startMoving();
        while (player.getPosition().x < 1) {
            player.setPositionRaw(mover.moveToTarget(deltas[i]));
            i = (i + 1) % 4;
        }
        System.out.println(MiscUtils.getMaxPrecisionFormat().format(player.getPosition().x));
        assertEquals(player.getPosition().x, 1.0f, 0f);
    }
}
