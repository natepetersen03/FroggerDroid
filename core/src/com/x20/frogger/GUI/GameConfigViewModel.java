package com.x20.frogger.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.x20.frogger.data.DataEnums;
import com.x20.frogger.game.GameConfig;

public class GameConfigViewModel {

    private static Button goButton;
    public void setGoButton(Button button) {
        goButton = button;
    }

    public static void setDifficulty(DataEnums.Difficulty difficulty) {
        GameConfig.setDifficulty(difficulty);
        checkValid();
    }

    public static void setCharacter(DataEnums.Character character) {
        GameConfig.setCharacter(character);
        checkValid();
    }

    public static void setName(String name) {
        GameConfig.setName(name);
        checkValid();
    }

    private static void checkValid() {
        boolean valid = false;
        if (GameConfig.getCharacter() != null
                && GameConfig.getDifficulty() != null
                && isValidName(GameConfig.getName())) {
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
