package com.x20.frogger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x20.frogger.FroggerDroid;
import com.x20.frogger.audio.Sfx;
import com.x20.frogger.data.Controls;
import com.x20.frogger.events.GameStateListener;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.InputController;
import com.x20.frogger.game.entities.Entity;
import com.x20.frogger.game.tiles.TileRenderer;

public class GameScreen implements Screen {
    // Game state
    private final FroggerDroid game;
    private GameLogic gameLogic;
    private GameStateListener gameStateListener;
    private boolean paused;

    // GUI
    private Skin skin;
    private Viewport guiViewport;
    private Stage stage;
    private Label scoreLabel;


    // Game rendering
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private TileRenderer tileRenderer;

    public GameScreen(final FroggerDroid game) {
        Gdx.app.log("GameScreen", "Initializing...");

        this.game = game;

        /// Initialize game logic
        gameLogic = GameLogic.getInstance();
        if (!gameLogic.isRunning()) {
            gameLogic.newGame();
        }

        // init GUI
        Gdx.app.log("GameScreen", "Loading GUI...");

        this.skin = game.getSkinGUI();
        // todo: get these width and height values from a variable somewhere
        this.guiViewport = new ExtendViewport(500, 480);
        constructUI();

        // attach UI GameState listener
        gameStateListener = new GameStateListener() {
            @Override
            public void onScoreUpdate(ScoreEvent e) {
                Gdx.app.debug("GameScreen", "ScoreEvent received");
                updateScoreLives();
            }

            @Override
            public void onLivesUpdate(LivesEvent e) {
                Gdx.app.debug("GameScreen", "LivesEvent received");
                updateScoreLives();
                if (e.didHurt()) {
                    Sfx.playDeathSound();
                }
            }

            @Override
            public void onGameEnd(GameEndEvent e) {
                Gdx.app.debug("GameScreen", "GameEndEvent received");
                if (e.didPlayerWin()) {
                    Sfx.playWinSound();
                    switchToGameWinScreen();
                } else {
                    switchToGameOverScreen();
                }
            }
        };
        gameLogic.addGameStateListener(gameStateListener);

        /// New Game Viewport
        Gdx.app.log("GameScreen", "Loading world viewport...");

        // Init
        this.gameCamera
            = new OrthographicCamera(gameLogic.getTileMap().getWidth(),
            gameLogic.getTileMap().getHeight());
        this.gameViewport
            = new FitViewport(gameLogic.getTileMap().getWidth(),
                gameLogic.getTileMap().getHeight(), gameCamera);
        this.tileRenderer
            = new TileRenderer(this.game.getBatch(), gameLogic.getTileMap());

    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "Initializing done");
        Sfx.playMusic();
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            // game update call
            update();

            // clear screen
            Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

            // Render game viewport first
            gameViewport.apply(true);
            game.getBatch().setProjectionMatrix(gameViewport.getCamera().combined);

            game.getBatch().begin();

            // Render tilemap
            tileRenderer.render();

            for (int i = 0; i < gameLogic.getTileMap().getHeight(); i++) {
                for (Entity entity : gameLogic.getTileMap().getEntitiesAtRow(i)) {
                    entity.render(game.getBatch());
                }
            }


            // Render player
            gameLogic.getPlayer().render(game.getBatch());

            game.getBatch().end();

            // Then render GUI viewport
            guiViewport.apply(true);
            game.getBatch().setProjectionMatrix(guiViewport.getCamera().combined);
            stage.act();
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        // update UI viewport on window resize
        gameViewport.update(width, height, true);
        guiViewport.update(width, height, true);

        // debug log
        Gdx.app.debug("Application",
            "Screen resized to dimensions: "
            + gameViewport.getScreenWidth() + " x "
            + gameViewport.getScreenHeight()
        );
    }

    public void updateScoreLives() {
        String text = "([#00FF00]" + GameConfig.getName()
            + "[#FFFFFF])  Lives: [#ADD8E6]" + gameLogic.getLives()
            + "  [#FFFFFF]Score: [#A020F0]" + gameLogic.getScore();
        scoreLabel.setText(text);
    }

    public void update() {
        gameLogic.update();
    }

    private void switchToGameOverScreen() {
        game.setScreen(new GameOverScreen(game));
        this.dispose();
    }

    private void switchToGameWinScreen() {
        game.setScreen(new GameWinScreen(game));
        this.dispose();
    }

    private void constructUI() {
        stage = new Stage(guiViewport);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.align(Align.top);
        table.setFillParent(true);

        Label label = new Label("Frogger! ", skin, "dark-bg");
        label.setAlignment(Align.top);
        table.add(label).growX();

        table.row();
        skin.getFont("Pixelify").getData().markupEnabled = true;
        scoreLabel = new Label("([#00FF00]" + GameConfig.getName()
                + "[#FFFFFF])  Lives: [#ADD8E6]" + GameConfig.getDifficulty().getLives()
                + "  [#FFFFFF]Score: [#A020F0]" + gameLogic.getScore(),
            skin, "dark-bg");
        scoreLabel.setAlignment(Align.top);
        table.add(scoreLabel).growX();
        stage.addActor(table);

        Table moveTable = new Table();
        setButtons(moveTable);
        stage.addActor(moveTable);

        Gdx.app.log("GameScreen", "Loaded GUI");

    }

    // player controls GUI
    // todo: refactor
    private void setButtons(Table table) {
        table.setColor(skin.getColor("white"));
        table.padLeft(0.0f);
        table.padRight(10.0f);
        table.padTop(0.0f);
        table.padBottom(10.0f);
        table.align(Align.bottomRight);
        table.setFillParent(true);

        table.add();

        ImageButton upButton = new ImageButton(skin, "up");
        table.add(upButton);

        table.add();

        table.row();
        ImageButton leftButton = new ImageButton(skin, "left");
        table.add(leftButton);

        ImageButton centerButton = new ImageButton(skin, "center");
        table.add(centerButton);

        ImageButton rightButton = new ImageButton(skin, "right");
        table.add(rightButton);

        table.row();
        table.add();

        ImageButton downButton = new ImageButton(skin, "down");
        table.add(downButton);

        table.add();
        addMovementListeners(upButton, leftButton, rightButton, downButton);
    }

    // binds player movement input to player movement
    // todo: rip and tear
    // status: callbacks updated
    private void addMovementListeners(
        Button up,
        Button left,
        Button right,
        Button down
    ) {
        up.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                InputController.queueMoveInput(Controls.MOVE.UP);
            }
        });

        right.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                InputController.queueMoveInput(Controls.MOVE.RIGHT);
            }
        });

        left.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                InputController.queueMoveInput(Controls.MOVE.LEFT);
            }
        });

        down.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                InputController.queueMoveInput(Controls.MOVE.DOWN);
            }
        });
    }

    @Override
    public void dispose() {
        Sfx.stopMusic();
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {

    }
}