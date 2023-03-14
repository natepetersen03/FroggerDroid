package com.badlogic.drop.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;

public class FTFSkin extends Skin {
    public FTFSkin(FileHandle internal) {
        super(internal);
    }

    // Modify JSON loader to also process FreeType fonts
    @Override
    protected Json getJsonLoader(final FileHandle skinFile) {
        Json json = super.getJsonLoader(skinFile);
        Skin skin = this;

        json.setSerializer(FreeTypeFontGenerator.class, new FTFSerializer(skinFile, skin));

        return json;
    }
}