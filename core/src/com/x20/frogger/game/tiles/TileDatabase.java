package com.x20.frogger.game.tiles;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

public class TileDatabase {

    private static boolean init = false;
    private static Map<String, Tile> database;
    private static Map<Character, String> charToKey;
    private static Map<String, Character> keyToChar;

    public static void initDatabase() {
        if (init) {
            return;
        }
        database = new HashMap<String, Tile>();
        charToKey = new HashMap<Character, String>();
        keyToChar = new HashMap<String, Character>();

        generateEntry(
            new TileID("road", 'r'),
            new TileProperties(false, false, 0),
            new TileSpriteData(0, 16, 16, 16, 1, 0)
        );

        generateEntry(
            new TileID("water", 'w'),
            new TileProperties(false, true, 0.1f),
            new TileSpriteData(2*16, 0, 16, 16, 2, 1)
        );

        generateEntry(
            new TileID("safe", 's'),
            new TileProperties(false, false, 0f),
            new TileSpriteData(16, 0, 16, 16, 1, 0)
        );

        generateEntry(
            new TileID("goal", 'g'),
            new TileProperties(false, false, 0f),
            new TileSpriteData(0, 0, 16, 16, 1, 0)
        );

        init = true;
        System.out.println("Tile database initialized");
    }

    public static Map<String, Tile> getDatabase() {
        if (!init) {
            throw new TileDatabaseInitializationException();
        }
        return database;
    }
    public static Map<Character, String> getCharToKey() {
        if (!init) {
            throw new TileDatabaseInitializationException();
        }
        return charToKey;
    }

    public static Map<String, Character> getKeyToChar() {
        if (!init) {
            throw new TileDatabaseInitializationException();
        }
        return keyToChar;
    }

    private static void generateEntry(
        TileID id,
        TileProperties properties,
        TileSpriteData spriteData
    ) {
        database.put(id.name,
            new Tile(
                new TileData(id.name, properties.isSolid, properties.isDamaging, properties.xVelocity),
                new TileRenderData(
                    spriteData.textureX, spriteData.textureY,
                    spriteData.width, spriteData.height,
                    spriteData.frames, spriteData.fps)
            ));
        charToKey.put(id.symbol, id.name);
        keyToChar.put(id.name, id.symbol);
    }

    private static class TileID {
        private String name;
        private char symbol;
        public TileID(String name, char symbol) {
            this.name = name;
            this.symbol = symbol;
        }
    }

    private static class TileProperties {
        private boolean isSolid;
        private boolean isDamaging;
        private float xVelocity;

        public TileProperties(boolean isSolid, boolean isDamaging, float xVelocity) {
            this.isSolid = isSolid;
            this.isDamaging = isDamaging;
            this.xVelocity = xVelocity;
        }
    }

    private static class TileSpriteData {
        private int textureX;
        private int textureY;
        private int width;
        private int height;
        private int frames;
        private int fps;

        public TileSpriteData(
            int textureX, int textureY, int width, int height, int frames, int fps
        ) {
            this.textureX = textureX;
            this.textureY = textureY;
            this.width = width;
            this.height = height;
            this.frames = frames;
            this.fps = fps;
        }
    }


}
