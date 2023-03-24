package com.x20.frogger;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.x20.frogger.graphics.AssetManagerSingleton;
import com.x20.frogger.utils.FTFSkinLoader;


public class FroggerDroid extends Game {

    private SpriteBatch batch;
    private Skin skinGUI;

    public SpriteBatch getBatch() {
        return batch;
    }

    public Skin getSkinGUI() {
        return skinGUI;
    }

    public void create() {
        batch = new SpriteBatch();

        // game asset init
        AssetManagerSingleton.getInstance().loadAssets();
        AssetManagerSingleton.getInstance().getAssetManager().finishLoading();
        // end game init

        skinGUI = FTFSkinLoader.loadFTFSkin("mc-style.json");

        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        AssetManagerSingleton.getInstance().getAssetManager().dispose();
        skinGUI.dispose();
    }

}
