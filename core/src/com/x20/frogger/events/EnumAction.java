package com.x20.frogger.events;

public interface EnumAction<E extends Enum> {
    void act(E enumVal);
}
