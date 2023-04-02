package com.x20.frogger;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x20.frogger.GUI.GameConfigViewModel;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class GameOverScreen extends ScreenAdapter {
    private final FroggerDroid game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    public GameOverScreen(final FroggerDroid game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
        this.viewport = new ExtendViewport(800, 400, camera);
        this.stage = new Stage(viewport);

        this.skin = game.getSkinGUI();
        constructGUI();
    }

    private void switchToGameConfigScreen() {
        game.setScreen(new GameConfigScreen(game));
        this.dispose();
    }

    @Override
    public void show() {
        // load all assets before displaying ready
        //game.getAssetManager().finishLoading();
        AssetManagerSingleton.getInstance().getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a dark blue background
        // ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.gl.glClearColor(0, 0, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // update stage and all its actors
        stage.act();

        // draw stage and all its actors
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        // viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void constructGUI() {
        stage = new Stage(new ExtendViewport(500, 480));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Label label = new Label("Game Over", skin, "dark-bg");
        table.add(label).colspan(2);

        table.row();
        table.add().colspan(2);

        table.row();
        label = new Label(null, skin);
        table.add(label);

        label = new Label(null, skin);
        table.add(label);

        table.row();
        label = new Label("Your Score: ", skin);
        table.add(label).padTop(5.0f).padBottom(5.0f).colspan(2);

        table.row();
        TextButton textButton = new TextButton("Restart", skin, "centeredLabel");
        table.add(textButton).pad(5.0f);

        textButton = new TextButton("Exit", skin, "centeredLabel");
        table.add(textButton).pad(5.0f);
        stage.addActor(table);

    }

}
