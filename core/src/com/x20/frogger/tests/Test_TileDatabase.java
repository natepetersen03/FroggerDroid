package com.x20.frogger.tests;

import static org.junit.Assert.assertEquals;

import com.x20.frogger.data.TileDatabase;
import com.x20.frogger.data.TileStruct;

import junit.runner.Version;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Test_TileDatabase {
    @Test
    public void checkInitialization() {
        TileDatabase database = TileDatabase.getInstance();
        try {
            TileStruct roadTile = database.getDatabase().get("road");
            assertEquals(roadTile.getTile().isSolid(), false);
        } catch (NullPointerException e) {
            Assert.fail("Database failed to initialize");
        }

        try {
            TileStruct waterTile = database.getDatabase().get("water");
            assertEquals(waterTile.getTile().isDamaging(), true);
        } catch (NullPointerException e) {
            Assert.fail("Database failed to initialize");
        }

        try {
            TileStruct ghostTile = database.getDatabase().get("iron");
            assertEquals(ghostTile.getTile().isDamaging(), true);
            Assert.fail("Ghost tile is in database");
        } catch (NullPointerException e) {
            System.out.println("Ghost tile not in database");
        }
    }
}