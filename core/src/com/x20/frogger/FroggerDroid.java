package com.x20.frogger;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.x20.frogger.data.DataEnums;
import com.x20.frogger.game.GameConfig;
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

    // debug flags
    private static boolean flagDebug = false;
    private static boolean flagSkipToGame = false;
    private static boolean flagInvulnerable = false;

    public FroggerDroid() {

    }

    public FroggerDroid(String[] args) {
        for (String arg : args) {
            String a = arg.toLowerCase();
            switch (a) {
            case "-debug":
                flagDebug = true;
                break;
            case "-skip":
                flagSkipToGame = true;
                break;
            case "-god":
                flagInvulnerable = true;
                break;
            default:
                break;
            }
        }
    }

    public static boolean isFlagDebug() {
        return flagDebug;
    }

    public static boolean isFlagSkipToGame() {
        return flagSkipToGame;
    }

    public static boolean isFlagInvulnerable() {
        return flagInvulnerable;
    }

    public void create() {
        batch = new SpriteBatch();

        // game asset init
        AssetManagerSingleton.getInstance().loadAssets();
        AssetManagerSingleton.getInstance().getAssetManager().finishLoading();
        Gdx.app.log("AssetManagerSingleton", "Assets loaded");

        // end game init

        skinGUI = FTFSkinLoader.loadFTFSkin("mc-style.json");

        // Flag-specific behaviors
        if (FroggerDroid.isFlagDebug()) {
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            Gdx.app.log("Application", "Debug mode enabled");
        }

        if (FroggerDroid.isFlagSkipToGame()) {
            Gdx.app.debug("FroggerDroid", "Skipping to GameScreen...");
            GameConfig.setCharacter(DataEnums.Character.STEVE);
            GameConfig.setName("Debugger");
            GameConfig.setDifficulty(DataEnums.Difficulty.EASY);
            this.setScreen(new GameScreen(this));
        } else {
            this.setScreen(new MainMenuScreen(this));
        }
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
