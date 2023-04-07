package com.x20.frogger;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

import junit.runner.Version;

/**
 * Example local unit test, which will execute on the development machine (host).
 * Shows how to start headless LibGDX backend
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

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


    @Test
    public void checkVersion() {
        System.out.println("Running JUnit version " + Version.id());
        assertEquals(4, 2 + 2);
    }

}