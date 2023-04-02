package com.x20.frogger.events;

import com.x20.frogger.events.EnumAction;

public class EnumHandler<E extends Enum> {
    private EnumAction<E> action;

    public EnumHandler(EnumAction<E> action) {
        this.action = action;
    }

    public void act(E enumVal) {
        action.act(enumVal);
    }
}
