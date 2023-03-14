package com.x20.frogger.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.x20.frogger.data.DataEnums;

public class GameConfigViewModel {

    // these fields would go in a data class somewhere else
    private static DataEnums.Difficulty difficulty;
    private static DataEnums.Character character;
    private static String name;
    private static Button goButton;
    public void setGoButton(Button button) {
        goButton = button;
    }

    public static void setDifficulty(DataEnums.Difficulty difficulty) {
        GameConfigViewModel.difficulty = difficulty;
        checkValid();
    }

    public static void setCharacter(DataEnums.Character character) {
        GameConfigViewModel.character = character;
        checkValid();
    }

    public static void setName(String name) {
        GameConfigViewModel.name = name;
        checkValid();
    }

    private static void checkValid() {
        boolean valid = false;
        if (character != null && difficulty != null && isValidName(name)) {
            valid = true;
        }
        try {
            goButton.setDisabled(!valid);
        } catch (NullPointerException exception) {
            System.out.println("goButton is not assigned");
        }
    }

    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        return !(name.replaceAll("\\s", "").equals(""));
    }
}
