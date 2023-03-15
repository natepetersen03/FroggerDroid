package com.x20.frogger;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.x20.frogger.utils.FTFSkinLoader;


public class FroggerDroid extends Game {

    private SpriteBatch batch;
    private AssetManager assetManager = new AssetManager();
    private Skin skinGUI;

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Skin getSkinGUI() {
        return skinGUI;
    }

    public void create() {
        batch = new SpriteBatch();

        // game asset init
        TextureLoader.TextureParameter parameter = new TextureLoader.TextureParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.genMipMaps = false;

        assetManager.load("mc-style.atlas", TextureAtlas.class);
        assetManager.load("grass.png", Texture.class);
        assetManager.load("creeper.png", Texture.class);
        assetManager.load("ironGolem.png", Texture.class);
        assetManager.load("skeleton.png", Texture.class);
        // end game init

        skinGUI = FTFSkinLoader.loadFTFSkin("mc-style.json");

        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        assetManager.dispose();
        skinGUI.dispose();
    }

}
