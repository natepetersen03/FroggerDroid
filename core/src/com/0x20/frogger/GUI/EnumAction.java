package com.badlogic.drop.GUI;

public interface EnumAction<E extends Enum> {
    void act(E enumVal);
}
