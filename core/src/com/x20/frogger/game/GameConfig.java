package com.x20.frogger.game;

import com.x20.frogger.data.DataEnums;
import com.x20.frogger.data.Serializable;

public class GameConfig implements Serializable {
    private static DataEnums.Difficulty difficulty;
    private static DataEnums.Character character;
    private static String name;

    public static DataEnums.Difficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(DataEnums.Difficulty difficulty) {
        GameConfig.difficulty = difficulty;
    }

    public static DataEnums.Character getCharacter() {
        return character;
    }

    public static void setCharacter(DataEnums.Character character) {
        GameConfig.character = character;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        GameConfig.name = name;
    }

    public static void reset() {
        character = null;
        name = null;
        difficulty = null;
    }
}
