package com.x20.frogger.GUI;

public interface EnumAction<E extends Enum> {
    void act(E enumVal);
}
