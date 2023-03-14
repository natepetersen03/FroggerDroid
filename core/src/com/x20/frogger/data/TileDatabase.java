package com.x20.frogger.data;

import com.x20.frogger.game.Tile;
import com.x20.frogger.graphics.TileRenderData;

import java.util.HashMap;
import java.util.Map;

public class TileDatabase {

    private static boolean init = false;
    private static Map<String, TileStruct> database;
    private static Map<Character, String> charToKey;
    private static Map<String, Character> keyToChar;

    public static void initDatabase() {
        if (init) {
            return;
        }
        database = new HashMap<String, TileStruct>();
        charToKey = new HashMap<Character, String>();
        keyToChar = new HashMap<String, Character>();

        generateEntry(
            "road",'r',
            false,false,0,
            0,0,
            16,16,
            1,0
        );

        generateEntry(
            "water",'w',
            false,true,0.1f,
            0,0,
            16,16,
            1,0
        );

        generateEntry(
            "safe",'s',
            false,false,0f,
            0,0,
            16,16,
            1,0
        );

        generateEntry(
            "goal",'g',
            false,false,0f,
            0,0,
            16,16,
            1,0
        );

        init = true;
        System.out.println("Tile database initialized");
    }

    public static Map<String, TileStruct> getDatabase() {
        if (!init) {
            initDatabase();
        }
        return database;
    }
    public static Map<Character, String> getCharToKey() {
        if (!init) {
            initDatabase();
        }
        return charToKey;
    }

    public static Map<String, Character> getKeyToChar() {
        if (!init) {
            initDatabase();
        }
        return keyToChar;
    }

    private static void generateEntry(
        String name, char symbol,
        boolean isSolid, boolean isDamaging, float xVelocity,
        int textureX, int textureY, int width, int height, int frames, int fps
    ) {
        database.put(name,
            new TileStruct(
                new Tile(name, isSolid, isDamaging, xVelocity),
                new TileRenderData(textureX, textureY, width, height, frames, fps)
            ));
        charToKey.put(symbol, name);
        keyToChar.put(name, symbol);
    }

    private static void generateEntry(
        String name, char symbol,
        boolean isSolid, boolean isDamaging, float xVelocity,
        int textureX, int textureY
    ) {
        generateEntry(
            name,
            symbol,
            isSolid,
            isDamaging,
            xVelocity,
            textureX,
            textureY,
            16,
            16,
            1,
            0
        );
    }

    private static void generateEntry(
        String name, char symbol, boolean isSolid,
        int textureX, int textureY
    ) {
        generateEntry(
            name,
            symbol,
            isSolid,
            false,
            0,
            textureX,
            textureY,
            16,
            16,
            1,
            0
        );
    }
}
