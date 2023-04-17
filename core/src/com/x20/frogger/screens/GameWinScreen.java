package com.x20.frogger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.x20.frogger.FroggerDroid;
import com.x20.frogger.utils.GifDecoder;
import com.x20.frogger.game.AnimatedActor;
import com.x20.frogger.game.GameLogic;

public class GameWinScreen extends ScreenAdapter {
    private final FroggerDroid game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    private Label scoreLabel;
    private TextButton restartButton;
    private TextButton exitButton;

    public GameWinScreen(final FroggerDroid game) {
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
        // AssetManagerSingleton.getInstance().getAssetManager().finishLoading();
        // ? this doesn't behave right after the first time this screen is shown
        // Sfx.playWinSound();
        Gdx.app.debug("GameWinScreen", "Player won, loaded win screen");
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a dark blue background
        Gdx.gl.glClearColor(0, 0, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act();
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

        setHeaderLabel(table);
        setAnimation(table);
        setScoreLabel(table);
        setRestartButton(table);
        setExitButton(table);

        stage.addActor(table);
    }

    private void setHeaderLabel(Table table) {
        Label label = new Label("You Won!", skin, "dark-bg");
        table.add(label)
                .spaceBottom(8.0f)
                .fillX()
                .minWidth(300.0f)
                .minHeight(42.0f)
                .colspan(3);
        label.setAlignment(Align.center);
        table.row();
    }

    private void setAnimation(Table table) {
        Animation animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,
                Gdx.files.internal("animation.gif").read());
        AnimatedActor actor = new AnimatedActor(animation);
        // fix some scaling issues
        float scale = 300.0f / actor.getWidth();
        actor.setWidth(actor.getWidth() * scale);
        actor.setHeight(actor.getHeight() * scale);
        table.add(actor)
                .spaceTop(8.0f)
                .spaceBottom(8.0f)
                .minWidth(300.0f)
                .minHeight(42.0f)
                .fillX()
                .colspan(3);
        table.row();
    }

    private void setScoreLabel(Table table) {
        int score = GameLogic.getInstance().getScore();
        scoreLabel = new Label(" Your Score: " + "[#00FF00]" + score + " ", skin);
        table.add(scoreLabel)
                .spaceTop(8.0f)
                .spaceBottom(32.0f)
                .fillX()
                .minHeight(42.0f)
                .colspan(3);
        scoreLabel.setAlignment(Align.center);
        table.row();
    }

    private void setRestartButton(Table table) {
        restartButton = new TextButton(" Restart ", skin, "centeredLabel");
        table.add(restartButton)
                .spaceBottom(8.0f)
                .fillX()
                .minWidth(300.0f)
                .minHeight(42.0f)
                .colspan(3);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToGameConfigScreen();
            }
        });
        table.row();
    }

    private void setExitButton(Table table) {
        exitButton = new TextButton(" Exit ", skin, "centeredLabel");
        table.add(exitButton)
                .spaceBottom(8.0f)
                .fillX()
                .minWidth(300.0f)
                .minHeight(42.0f)
                .colspan(3);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
                System.exit(0);
            }
        });
    }

}
