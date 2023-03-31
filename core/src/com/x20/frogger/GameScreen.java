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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x20.frogger.data.Controls;
import com.x20.frogger.data.DataEnums;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.game.GameLogic;
import com.x20.frogger.game.InputController;
import com.x20.frogger.game.Vehicle;
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

    // Vehicles
    private Array<Vehicle> vehicles;
    private DataEnums.VehicleType[] vehicleTypes;

    public GameScreen(final FroggerDroid game) {
        if (FroggerDroid.isFlagDebug()) {
            System.out.println("Loading GameScreen...");
        }
        this.game = game;

        /// Initialize game logic
        if (FroggerDroid.isFlagDebug()) {
            System.out.println("Loading GameLogic...");
        }
        gameLogic = GameLogic.getInstance();


        // init GUI
        if (FroggerDroid.isFlagDebug()) {
            System.out.println("Loading GUI...");
        }
        this.skin = game.getSkinGUI();
        // todo: get these width and height values from a variable somewhere
        this.guiViewport = new ExtendViewport(500, 480);
        constructUI();

        /// New Game Viewport
        if (FroggerDroid.isFlagDebug()) {
            System.out.println("Loading World Renderer...");
        }
        // Init
        this.gameCamera = new OrthographicCamera(gameLogic.getTileMap().getWidth(), gameLogic.getTileMap().getHeight());
        this.gameViewport = new FitViewport(gameLogic.getTileMap().getWidth(), gameLogic.getTileMap().getHeight(), gameCamera);
        //this.gameViewport = new ExtendViewport(worldString[0].length(), worldString.length, gameCamera);
        this.tileRenderer = new TileRenderer(this.game.getBatch(), gameLogic.getTileMap());


        // vehicle types
        // todo: replace with proper vehicle classes
        // status: disabled
        //vehicleTypes = new DataEnums.VehicleType[] {
        //    DataEnums.VehicleType.IRON_GOLEM,
        //    DataEnums.VehicleType.CREEPER,
        //    DataEnums.VehicleType.SKELETON,
        //    DataEnums.VehicleType.IRON_GOLEM,
        //    DataEnums.VehicleType.SKELETON,
        //    DataEnums.VehicleType.CREEPER,
        //    DataEnums.VehicleType.CREEPER,
        //    DataEnums.VehicleType.CREEPER,
        //    DataEnums.VehicleType.CREEPER,
        //};

        // generate vehicle types in level
        // replaces hard coded array above
        // todo: replace with vehicle generator
        // status: disabled

        //for (int i = 0; i < 9; i++) {
        //    int rand = MathUtils.random(1, 3);
        //    vehicleTypes[i] = generateVehicleType(rand);
        //}
        //
        //this.vehicles = spawnVehicles(vehicleTypes);
    }

    @Override
    public void show() {
        if (FroggerDroid.isFlagDebug()) {
            System.out.println("Done loading GameScreen");
        }
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

            // render vehicles
            // todo: under reconstruction
            // status: disabled
            //game.getBatch().begin();
            //// render all vehicles in one batch
            //for (Iterator<Vehicle> iter = vehicles.iterator(); iter.hasNext();) {
            //    Vehicle vehicle = iter.next();
            //    game.getBatch().draw(
            //        vehicle.getVehicleImage(), vehicle.getHitbox().x, vehicle.getHitbox().y
            //    );
            //}

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
        if (FroggerDroid.isFlagDebug()) {
            System.out.println(
                "Screen resized to dimensions: " +
                    gameViewport.getScreenWidth() +
                    " x " +
                    gameViewport.getScreenHeight() +
                    "; World dimensions: " +
                    gameViewport.getWorldWidth() +
                    " x " +
                    gameViewport.getWorldHeight()
            );
        }
    }

    public void update() {
        gameLogic.update();

        // bounds restriction
        // todo: update all to work with world coordinates and not screen coordinates
        // status: disabled
        //if (character.getX() < 0) {
        //    character.setX(0);
        //}
        //if (character.getX() > Gdx.graphics.getWidth() - character.getWidth()) {
        //    character.setX(Gdx.graphics.getWidth() - character.getWidth());
        //}
        //
        //if (character.getY() < 0) {
        //    character.setY(0);
        //}
        //if (character.getY() > Gdx.graphics.getHeight() - character.getHeight() - tableHeight) {
        //    character.setY(Gdx.graphics.getHeight() - character.getHeight() - tableHeight);
        //}

        // score system
        // todo: overhaul and put in separate method
        // ? should this go here? in the player class?
        // status: disabled

        //if (maxY < character.getY()) {
        //    maxY = character.getY();
        //    // todo: replace with tile data based system
        //    /*
        //     * might try making a separate array of point values to store for this
        //     * maybe just store this in tile data? would be wasteful though since
        //     * each row of tiles awards the same points... except for the top row,
        //     * where some spots are goal tiles and others will straight up kill you
        //     * so we can't just reduce the tile data set to one tile representing the entire row
        //     */
        //    score += getPoints(maxY);
        //    // score += 1;
        //    updateLabel.setText("([#00FF00]" + GameConfig.getName()
        //            + "[#FFFFFF])  Lives: [#ADD8E6]" + getLives(GameConfig.getDifficulty())
        //            + "  [#FFFFFF]Score: [#A020F0]" + score);
        //}

        // update vehicle position
        // todo: move into separate method
        // status: disabled

        //for (Iterator<Vehicle> iter = vehicles.iterator(); iter.hasNext();) {
        //    Vehicle vehicle = iter.next();
        //    vehicle.updatePosition();
        //}
    }

    // deprecated point system
    // todo: delete this
    // yCoord is in screen space
    // status: disabled
//    private int getPoints(float yCoord) {
//        int points = 1;
//        for (int i = vehicleTypes.length - 1; i > -1; i--) {
//            System.out.println(i + ": checking position: " + 2 * tileSize + (i + 1));
//            if (yCoord >= 2 * tileSize + (i + 1) * tileSize) {
//                points = getVehicleVelocity(vehicleTypes[i]) / 10;
//            }
//        }
//        System.out.println("y-coord: " + yCoord + "points = " + points);
////        if (yCoord >= 2 * tileSize + (vehicleTypes.length) * tileSize) {
////            points = 1;
////        }
//        return points;
//    }

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
                + "[#FFFFFF])  Lives: [#ADD8E6]" + getLives(GameConfig.getDifficulty())
                + "  [#FFFFFF]Score: [#A020F0]" + GameLogic.getInstance().getScore(), skin, "dark-bg");
        scoreLabel.setAlignment(Align.top);
        table.add(scoreLabel).growX();
        stage.addActor(table);

        Table moveTable = new Table();
        setButtons(moveTable);
        stage.addActor(moveTable);

        if (FroggerDroid.isFlagDebug()) {
            System.out.println("Done loading GUI");
        }
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

    // vehicle spawning
    // todo: rip and tear
    /*
    Seems to also assign textures to it
    todo: pull textures from texture atlas
    is called only once on creation of GameScreen
    Spawns vehicles spaced out from each other
    and reuses them instead of spawning and despawning (due to Vehicle's updatePosition method)
     */
    // status: disabled
    //private Array<Vehicle> spawnVehicles(DataEnums.VehicleType[] vehicleTypes) {
    //    Array<Vehicle> spawnedVehicles = new Array<Vehicle>();
    //    for (int y = 0; y < vehicleTypes.length; y++) {
    //        int width = getVehicleWidth(vehicleTypes[y]);
    //        int velocity = getVehicleVelocity(vehicleTypes[y]);
    //        int spacing = getVehicleSpacing(vehicleTypes[y]);
    //        Texture sprite;
    //        switch (vehicleTypes[y]) {
    //        case IRON_GOLEM:
    //            sprite = AssetManagerSingleton.getInstance().getAssetManager().get("ironGolem.png", Texture.class);
    //            break;
    //        case CREEPER:
    //            sprite = AssetManagerSingleton.getInstance().getAssetManager().get("creeper.png", Texture.class);
    //            break;
    //        case SKELETON:
    //            sprite = AssetManagerSingleton.getInstance().getAssetManager().get("skeleton.png", Texture.class);
    //            break;
    //        default:
    //            sprite = AssetManagerSingleton.getInstance().getAssetManager().get("grass.png", Texture.class);
    //            break;
    //        }
    //
    //        for (int x = 0; x < Gdx.graphics.getWidth(); x += spacing * tileSize) {
    //            Vehicle vehicle = new Vehicle(
    //                x, 2 * tileSize + (y + 1) * tileSize, tileSize, width, velocity, sprite);
    //            spawnedVehicles.add(vehicle);
    //        }
    //    }
    //
    //    return spawnedVehicles;
    //}

    // used by the spawning algorithm to generate Vehicles
    // not sure what the purpose of separate width values is for
    // edit: for some reason this is used to build the hitbox of the vehicle.
    //       not sure why this isn't just a constant size or stored elsewhere
    // todo: remove
    public static int getVehicleWidth(DataEnums.VehicleType vehicleType) {
        switch (vehicleType) {
        case IRON_GOLEM:
            return 140;
        case CREEPER:
            return 60;
        case SKELETON:
            return 110;
        default:
            return 100;
        }
    }


    // todo: store vehicle velocity as a property of a vehicle instance
    public static int getVehicleVelocity(DataEnums.VehicleType vehicleType) {
        switch (vehicleType) {
        case IRON_GOLEM:
            return 30;
        case CREEPER:
            return 130;
        case SKELETON:
            return 80;
        default:
            return 100;
        }
    }

    // used by the vehicle spawner to space out vehicle spawns
    // note: probably will be replaced by the specific vehicle's spawn-spacing property
    public static int getVehicleSpacing(DataEnums.VehicleType vehicleType) {
        switch (vehicleType) {
        case IRON_GOLEM:
            return 7;
        case CREEPER:
            return 6;
        case SKELETON:
            return 4;
        default:
            return 10;
        }
    }

    // soon to be deprecated
    public static DataEnums.VehicleType generateVehicleType(int x) {
        switch (x) {
        case 1:
            return DataEnums.VehicleType.IRON_GOLEM;
        case 2:
            return DataEnums.VehicleType.CREEPER;
        case 3:
            return DataEnums.VehicleType.SKELETON;
        default:
            return DataEnums.VehicleType.SKELETON;
        }
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
    private String getLives(DataEnums.Difficulty difficulty) {
        switch (difficulty) {
        case EASY:
            return "10";
        case HARD:
            return "1";
        case NORMAL:
            return "5";
        default:
            return "NULL";
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