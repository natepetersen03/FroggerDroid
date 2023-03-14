package com.badlogic.drop.GUI;
import com.badlogic.drop.data.DataEnums.Character;

public class CharacterRadio implements EnumAction<Character> {

    @Override
    public void act(Character e) {
        System.out.println("Player is " + (e).toString());
        GameConfigViewModel.setCharacter(e);
    }
}
