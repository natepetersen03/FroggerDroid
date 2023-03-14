package com.badlogic.drop.GUI;
import com.badlogic.drop.data.DataEnums.Difficulty;

public class DifficultyRadio implements EnumAction<Difficulty> {

    @Override
    public void act(Difficulty e) {
        System.out.println("Difficulty is " + e.toString());
        GameConfigViewModel.setDifficulty(e);
    }
}
