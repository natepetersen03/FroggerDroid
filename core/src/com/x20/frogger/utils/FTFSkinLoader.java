package com.x20.frogger.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectSet;

public class FTFSkinLoader {
    public static Skin loadFTFSkin(String skinName) {
        return loadFTFSkin(skinName, Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    public static Skin loadFTFSkin(String skinName, Texture.TextureFilter filter) {
        return loadFTFSkin(skinName, filter, filter);
    }

    public static Skin loadFTFSkin(String skinName,
                                   Texture.TextureFilter minFilter,
                                   Texture.TextureFilter magFilter
    ) {
        Skin skin = new FTFSkin(Gdx.files.internal(skinName));

        // Set the right filter
        ObjectSet<Texture> textures = skin.getAtlas().getTextures();
        System.out.println("Loaded " + textures.size + " textures from skin " + skinName);
        for (Texture t: textures) {
            t.setFilter(minFilter, magFilter);
        }

        return skin;
    }
}
