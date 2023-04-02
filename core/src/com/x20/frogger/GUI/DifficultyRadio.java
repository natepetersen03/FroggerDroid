package com.x20.frogger.GUI;
import com.x20.frogger.data.DataEnums.Difficulty;
import com.x20.frogger.events.EnumAction;

public class DifficultyRadio implements EnumAction<Difficulty> {

    @Override
    public void act(Difficulty e) {
        System.out.println("Difficulty is " + e.toString());
        GameConfigViewModel.setDifficulty(e);
    }
}
