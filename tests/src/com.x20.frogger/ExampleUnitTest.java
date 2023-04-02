package com.x20.frogger;

import org.junit.Test;

import static org.junit.Assert.*;

import junit.runner.Version;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void checkVersion() {
        System.out.println("Running JUnit version " + Version.id());
        assertEquals(4, 2 + 2);
    }
}