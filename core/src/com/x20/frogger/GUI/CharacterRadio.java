package com.x20.frogger.GUI;
import com.x20.frogger.data.DataEnums.Character;

public class CharacterRadio implements EnumAction<Character> {

    @Override
    public void act(Character e) {
        System.out.println("Player is " + (e).toString());
        GameConfigViewModel.setCharacter(e);
    }
}
