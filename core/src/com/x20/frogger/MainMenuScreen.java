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

public class MainMenuScreen extends ScreenAdapter {

    private final FroggerDroid game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    public MainMenuScreen(final FroggerDroid game) {
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
        table.setTouchable(enabled);
        table.setFillParent(true);

        Label label = new Label("WORK IN PROGRESS", skin, "dark-bg");
        setTitleLabel(table, label);

        table.row();
        TextButton textButton = new TextButton("Play", skin);
        setPlayButton(table, textButton);

        GameConfigViewModel.setGoButton(textButton);

        stage.addActor(table);
    }

    private void setTitleLabel(Table table, Label label) {
        label.setAlignment(Align.center);
        table.add(label)
                .spaceBottom(8.0f)
                .fillX()
                .minWidth(300.0f)
                .minHeight(42.0f)
                .colspan(3);
    }

    public void setPlayButton(Table table, TextButton textButton) {
        table.add(textButton)
                .spaceTop(32.0f)
                .spaceBottom(4.0f)
                .fillX()
                .minHeight(44.0f)
                .colspan(3);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToGameConfigScreen();
            }
        });
    }
}
