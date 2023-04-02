package com.x20.frogger;

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
import com.x20.frogger.data.Controls;
import com.x20.frogger.data.DataEnums;
import com.x20.frogger.game.Entity;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.InputController;

import com.x20.frogger.game.Vehicle;
import com.x20.frogger.game.tiles.TileMap;

import com.x20.frogger.game.tiles.TileRenderer;

public class GameScreen implements Screen {
    // Game state
    private final FroggerDroid game;
    private GameLogic gameLogic;
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

    private String name;

    public GameScreen(final FroggerDroid game) {
        Gdx.app.log("GameScreen", "Initializing...");

        this.game = game;

        /// Initialize game logic
        gameLogic = GameLogic.getInstance();
        gameLogic.setLives(getLives(GameConfig.getDifficulty()));


        // init GUI
        Gdx.app.log("GameScreen", "Loading GUI...");

        this.skin = game.getSkinGUI();
        // todo: get these width and height values from a variable somewhere
        this.guiViewport = new ExtendViewport(500, 480);
        constructUI();

        /// New Game Viewport
        Gdx.app.log("GameScreen", "Loading world viewport...");

        // Init
        this.gameCamera = new OrthographicCamera(gameLogic.getTileMap().getWidth(), gameLogic.getTileMap().getHeight());
        this.gameViewport = new FitViewport(gameLogic.getTileMap().getWidth(), gameLogic.getTileMap().getHeight(), gameCamera);
        //this.gameViewport = new ExtendViewport(worldString[0].length(), worldString.length, gameCamera);
        this.tileRenderer = new TileRenderer(this.game.getBatch(), gameLogic.getTileMap());


        gameLogic.getTileMap().generateMobs();

        // set label fields
        this.name = GameConfig.getName();

    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "Initializing done");
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

            // Render player
            gameLogic.getPlayer().render(game.getBatch());

            for (int i = 0; i < gameLogic.getTileMap().getEntities().size(); i++) {
                for (Entity entity:
                        gameLogic.getTileMap().getEntities().get(i)) {
                    entity.render(game.getBatch());
                }
            }

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
    "Screen resized to dimensions: " +
            gameViewport.getScreenWidth() +
            " x " +
            gameViewport.getScreenHeight()
        );
    }

    public void updateScoreLives() {
        // TODO: bad performance, need to store name/
        String text = "([#00FF00]" + name
            + "[#FFFFFF])  Lives: [#ADD8E6]" + gameLogic.getLives()
            + "  [#FFFFFF]Score: [#A020F0]" + gameLogic.getScore();
        scoreLabel.setText(text);
    }

    public void update() {
        gameLogic.update();
        updateScoreLives();

        if (gameLogic.isDead()) {
            switchToGameOverScreen();
        }
//        if (gameLogic.checkGoal()) {
//            switchToGameOverScreen();
//        }
    }

    private void switchToGameOverScreen() {
        game.setScreen(new GameOverScreen(game));
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
        scoreLabel = new Label("([#00FF00]" + name
                + "[#FFFFFF])  Lives: [#ADD8E6]" + getLives(GameConfig.getDifficulty())
                + "  [#FFFFFF]Score: [#A020F0]" + GameLogic.getInstance().getScore(), skin, "dark-bg");
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

    // determine number of starting lives from specific difficulty
    // todo: rewrite Difficulty enum to hold value of starting lives directly
    // todo: figure out why this is a string
    private int getLives(DataEnums.Difficulty difficulty) {
        switch (difficulty) {
        case EASY:
            return 10;
        case HARD:
            return 1;
        case NORMAL:
            return 5;
        default:
            return -1;
        }
    }

    @Override
    public void dispose() {
        // now handled in FroggerDroid.java by the AssetManager
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