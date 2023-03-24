package com.x20.frogger.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Singleton wrapper for shared AssetManager instance.
 * Note: not using static as per warning from https://libgdx.com/wiki/managing-your-assets
 * Call init() to enqueue all assets to load
 * Then call update() or finishLoading() from the asset manager itself to load
 */
public class AssetManagerSingleton {
    private static AssetManagerSingleton instance;
    private AssetManager assetManager;
    private AssetManagerSingleton() {
        assetManager = new AssetManager();
    }

    public static synchronized AssetManagerSingleton getInstance() {
        if (instance == null) {
            instance = new AssetManagerSingleton();
        }
        return instance;
    }

    public AssetManager getAssetManager() {
        return instance.assetManager;
    }

    public void init() {
        TextureParameter textureParameter = new TextureParameter();
        textureParameter.minFilter = Texture.TextureFilter.Linear;
        textureParameter.magFilter = Texture.TextureFilter.Nearest;
        textureParameter.genMipMaps = false;

        instance.assetManager.load("mc-style.atlas", TextureAtlas.class);
        instance.assetManager.load("grass.png", Texture.class, textureParameter);
        instance.assetManager.load("creeper.png", Texture.class, textureParameter);
        instance.assetManager.load("ironGolem.png", Texture.class, textureParameter);
        instance.assetManager.load("skeleton.png", Texture.class, textureParameter);
        instance.assetManager.load("tiles.png", Texture.class, textureParameter);
    }
}
