package com.x20.frogger.events;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.x20.frogger.events.EnumAction;

public class EnumeratedClickListener extends ClickListener {
    private Enum e;
    private EnumAction action;

    public EnumeratedClickListener(EnumAction action, Enum enumVal) {
        super();
        e = enumVal;
        this.action = action;
    }

    @Override
    public void clicked(InputEvent event,
                        float x,
                        float y) {
        action.act(e);
    }
}
