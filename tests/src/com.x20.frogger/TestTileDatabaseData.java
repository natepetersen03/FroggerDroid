package com.x20.frogger;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.x20.frogger.game.tiles.Tile;
import com.x20.frogger.game.tiles.TileDatabase;
import com.x20.frogger.game.tiles.TileMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestTileDatabaseData {
    private static HeadlessApplication application;

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
        // todo 2: ask for permission if i can copy-paste his runner class
        //AssetManagerSingleton.getInstance().loadAssets();
        //AssetManagerSingleton.getInstance().getAssetManager().finishLoading();

        // the following snippet will be barebones enough
        // https://stackoverflow.com/questions/42252209/is-there-any-way-to-create-integration-test-for-libgdx-application
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        config.updatesPerSecond = 60;
        application = new HeadlessApplication(new FroggerDroid(), config);
    }

    @Test
    public void checkInitialization() {
        TileDatabase.initDatabase();
        try {
            Tile roadTile = TileDatabase.getDatabase().get("road");
            assertEquals(roadTile.getTileData().isSolid(), false);
        } catch (NullPointerException e) {
            Assert.fail("Database failed to initialize");
        }

        try {
            Tile waterTile = TileDatabase.getDatabase().get("water");
            assertEquals(waterTile.getTileData().isDamaging(), true);
        } catch (NullPointerException e) {
            Assert.fail("Database failed to initialize");
        }

        try {
            Tile ghostTile = TileDatabase.getDatabase().get("iron");
            assertEquals(ghostTile.getTileData().isDamaging(), true);
            Assert.fail("Ghost tile is in database");
        } catch (NullPointerException e) {
            System.out.println("Ghost tile not in database");
        }
    }

    @Test
    public void testStringToMap() {
        TileDatabase.initDatabase();
        TileMap map = new TileMap();
        map.generateTileMapFromStringArray(new String[] {
            "rrrrr",
            "wwwww",
            "sssss",
            "ggggg"
        });
        assertEquals(map.getTile(0, 0).getTileData().getName(), "goal");
        assertEquals(map.getTile(3, 2).getTileData().getName(), "water");
    }

    @Test
    public void testMapToString() {
        TileDatabase.initDatabase();
        TileMap map = new TileMap();
        String[] strMap = new String[] {
            "rrrrr",
            "wwwww",
            "swsws",
            "rgrrr"
        };
        map.generateTileMapFromStringArray(strMap);
        String[] generated = map.generateStringArrayFromTileMap();
        assertEquals(map.getTile(1, 0).getTileData().getName(), "goal");
        System.out.println(map.toString());
        Assert.assertArrayEquals(strMap, generated);
    }
}