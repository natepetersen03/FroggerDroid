package com.x20.frogger.tests;

import static org.junit.Assert.assertEquals;

import com.x20.frogger.game.tiles.Tile;
import com.x20.frogger.game.tiles.TileDatabase;
import com.x20.frogger.game.tiles.TileMap;
import com.x20.frogger.graphics.AssetManagerSingleton;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestTileDatabaseData {
    @BeforeClass
    public static void initAssets() {
        /// So it turns out that I can't use any libgdx-specific code (or at least, nothing
        /// related with asset loading, which likely has more to do with opengl) without
        /// somehow loading part/all of libgdx that deals with this stuff
        /// so I can't test this here.
        /// There is a way to test using the headless backend.
        /// It can be implemented using code provided freely from:
        /// https://github.com/TomGrill/gdx-testing
        /// todo: implement the above
        //AssetManagerSingleton.getInstance().loadAssets();
        //AssetManagerSingleton.getInstance().getAssetManager().finishLoading();
    }

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