package com.x20.frogger.tests;

import static org.junit.Assert.assertEquals;

import com.x20.frogger.game.tiles.TileDatabase;
import com.x20.frogger.game.tiles.Tile;
import com.x20.frogger.game.tiles.TileMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestTileDatabaseData {
    @Test
    public void checkInitialization() {
        TileDatabase.initDatabase();
        try {
            Tile roadTile = TileDatabase.getDatabase().get("road");
            assertEquals(roadTile.getTile().isSolid(), false);
        } catch (NullPointerException e) {
            Assert.fail("Database failed to initialize");
        }

        try {
            Tile waterTile = TileDatabase.getDatabase().get("water");
            assertEquals(waterTile.getTile().isDamaging(), true);
        } catch (NullPointerException e) {
            Assert.fail("Database failed to initialize");
        }

        try {
            Tile ghostTile = TileDatabase.getDatabase().get("iron");
            assertEquals(ghostTile.getTile().isDamaging(), true);
            Assert.fail("Ghost tile is in database");
        } catch (NullPointerException e) {
            System.out.println("Ghost tile not in database");
        }
    }

    @Test
    public void testStringToMap() {
        TileMap map = new TileMap();
        map.generateTileMapFromStringArray(new String[] {
            "rrrrr",
            "wwwww",
            "sssss",
            "ggggg"
        });
        assertEquals(map.getTileStruct(0, 0).getTile().getName(), "goal");
        assertEquals(map.getTileStruct(3, 2).getTile().getName(), "water");
    }

    @Test

    public void testMapToString() {
        TileMap map = new TileMap();
        String[] strMap = new String[] {
            "rrrrr",
            "wwwww",
            "swsws",
            "rgrrr"
        };
        map.generateTileMapFromStringArray(strMap);
        String[] generated = map.generateStringArrayFromTileMap();
        assertEquals(map.getTileStruct(1, 0).getTile().getName(), "goal");
        System.out.println(map.toString());
        Assert.assertArrayEquals(strMap, generated);
    }
}