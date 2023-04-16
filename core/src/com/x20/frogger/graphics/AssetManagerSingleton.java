package com.x20.frogger.graphics;

import com.badlogic.gdx.Gdx;
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
        return assetManager;
    }

    public void loadAssets() {
        TextureParameter textureParameter = new TextureParameter();
        textureParameter.minFilter = Texture.TextureFilter.Linear; // todo see if better on nearest
        textureParameter.magFilter = Texture.TextureFilter.Nearest;
        textureParameter.genMipMaps = false;

        TextureParameter logParam = new TextureParameter();
        logParam.minFilter = Texture.TextureFilter.Linear;
        logParam.magFilter = Texture.TextureFilter.Nearest;
        logParam.genMipMaps = false;
        logParam.wrapU = Texture.TextureWrap.Repeat;
        logParam.wrapV = Texture.TextureWrap.Repeat;

        assetManager.load("mc-style.atlas", TextureAtlas.class);
        assetManager.load("tiles.png", Texture.class, textureParameter);
        assetManager.load("players.png", Texture.class, textureParameter);
        assetManager.load("vehicles.png", Texture.class, textureParameter);
        assetManager.load("logs/oak_log.png", Texture.class, logParam);
        assetManager.load("logs/birch_log.png", Texture.class, logParam);
        assetManager.load("logs/lily_pad.png", Texture.class, logParam);

        Gdx.app.log("AssetManagerSingleton", "Assets enqueued.");
    }
}
