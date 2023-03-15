package com.x20.frogger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.MathUtils;
import com.x20.frogger.GUI.GameConfigViewModel;
import com.x20.frogger.data.DataEnums;
import com.x20.frogger.game.GameConfig;
import com.x20.frogger.graphics.GrassTile;
import java.util.Iterator;

public class GameScreen implements Screen {
    private final FroggerDroid game;

    private Sound dropSound;
    private Music rainMusic;
    private OrthographicCamera camera;
    private Rectangle bucket;
    private Vector3 touchPos = Vector3.Zero;
    private Array<Vehicle> vehicles;
    private long lastDropTime; // in nanoseconds
    private boolean paused;
    private int dropsGathered;

    private Stage stage;
    private Skin skin;
    private Viewport viewport;
    Label updateLabel;

    private int tileSize;
    private float tableHeight;
    private int score;
    private float maxY;
    private float unitScale;

    private Sprite character;
    private TiledMap tileMap;
    TiledMapRenderer renderer;

    public GameScreen(final FroggerDroid game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
        this.viewport = new ExtendViewport(800, 400, camera);
        this.stage = new Stage(viewport);


        this.skin = game.getSkinGUI();
        this.score = 0;
        this.maxY = 0;
        this.tileSize = 64;
        this.unitScale = 1 / tileSize;
        constructUI();

        this.tileMap = constructMap();

        DataEnums.VehicleType[] vehicleTypes = {
                DataEnums.VehicleType.IRON_GOLEM,
                DataEnums.VehicleType.CREEPER,
                DataEnums.VehicleType.SKELETON,
                DataEnums.VehicleType.IRON_GOLEM,
                DataEnums.VehicleType.SKELETON,
                DataEnums.VehicleType.CREEPER,
                DataEnums.VehicleType.CREEPER,
                DataEnums.VehicleType.CREEPER,
                DataEnums.VehicleType.CREEPER,
        };

        for (int i = 0; i < 9; i++) {
            int rand = MathUtils.random(1, 3);
            vehicleTypes[i] = generateVehicleType(rand);
        }

        this.vehicles = spawnVehicles(vehicleTypes);


        TextureAtlas atlas = game.getAssetManager().get("mc-style.atlas");
        TextureRegion region = atlas.findRegion(GameConfigViewModel.getCharacterAtlas());
        this.character = new Sprite(region);
        character.setPosition(Gdx.graphics.getWidth()/2 - character.getWidth()/2, 0);
    }

    @Override
    public void show() {
        game.getAssetManager().finishLoading();
        //TODO:
        // figure out what unit scale is
        renderer = new OrthogonalTiledMapRenderer(tileMap, 1);
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


            viewport.apply(true);
            renderer.setView(camera);
            renderer.render();

            stage.act();
            stage.draw();



            update();

            game.getBatch().begin();
            character.draw(game.getBatch());
            for (Iterator<Vehicle> iter = vehicles.iterator(); iter.hasNext();) {
                Vehicle vehicle = iter.next();
                game.getBatch().draw(vehicle.getVehicleImage(), vehicle.getHitbox().x, vehicle.getHitbox().y);
            }
            game.getBatch().end();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void update() {
        // bounds restriction
        if (character.getX() < 0) {
            character.setX(0);
        }
        if (character.getX() > Gdx.graphics.getWidth() - character.getWidth()) {
            character.setX(Gdx.graphics.getWidth() - character.getWidth());
        }

        if (character.getY() < 0) {
            character.setY(0);
        }
        if (character.getY() > Gdx.graphics.getHeight() - character.getHeight() - tableHeight) {
            character.setY(Gdx.graphics.getHeight() - character.getHeight() - tableHeight);
        }

        if (maxY < character.getY()) {
            maxY = character.getY();
            score += 1;
            updateLabel.setText("([#00FF00]" + GameConfig.getName()
                    + "[#FFFFFF])  Lives: [#ADD8E6]" + getLives(GameConfig.getDifficulty())
                    + "  [#FFFFFF]Score: [#A020F0]" + score);
        }

        for (Iterator<Vehicle> iter = vehicles.iterator(); iter.hasNext();) {
            Vehicle vehicle = iter.next();
            vehicle.updatePosition();
        }
    }

    private TiledMap constructMap() {
        int mapWidth = Gdx.graphics.getWidth()/tileSize;
        int mapHeight = Gdx.graphics.getHeight()/tileSize;

        MapGenerator mapGenerator = new MapGenerator(mapWidth, mapHeight);
        mapGenerator.createMap1();

        return mapGenerator.returnMap();
    }

    private void constructUI() {

        stage = new Stage(new ExtendViewport(500, 480));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.align(Align.top);
        table.setFillParent(true);

        Label label = new Label("Frogger! ", skin, "dark-bg");
        label.setAlignment(Align.top);
        table.add(label).growX();

        table.row();
        skin.getFont("Pixelify").getData().markupEnabled = true;
        updateLabel = new Label("([#00FF00]" + GameConfig.getName()
                + "[#FFFFFF])  Lives: [#ADD8E6]" + getLives(GameConfig.getDifficulty())
                + "  [#FFFFFF]Score: [#A020F0]" + score, skin, "dark-bg");
        updateLabel.setAlignment(Align.top);
        this.tableHeight = 200;
        table.add(updateLabel).growX();
        stage.addActor(table);

        Table moveTable = new Table();
        setButtons(moveTable);
        stage.addActor(moveTable);

    }

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

    private Array<Vehicle> spawnVehicles(DataEnums.VehicleType[] vehicleTypes) {
        Array<Vehicle> spawnedVehicles = new Array<Vehicle>();
        for (int y = 0; y < vehicleTypes.length; y++) {
            int width = getVehicleWidth(vehicleTypes[y]);
            int velocity = getVehicleVelocity(vehicleTypes[y]);
            int spacing = getVehicleSpacing(vehicleTypes[y]);
            Texture sprite;
            switch (vehicleTypes[y]) {
                case IRON_GOLEM:
                    sprite = game.getAssetManager().get("ironGolem.png", Texture.class);
                    break;
                case CREEPER:
                    sprite = game.getAssetManager().get("creeper.png", Texture.class);
                    break;
                case SKELETON:
                    sprite = game.getAssetManager().get("skeleton.png", Texture.class);
                    break;
                default:
                    sprite = game.getAssetManager().get("grass.png", Texture.class);
                    break;

            }

            for (int x = 0; x < Gdx.graphics.getWidth(); x += spacing*tileSize) {
                Vehicle vehicle = new Vehicle(x, 2*tileSize + (y + 1)*tileSize, tileSize, width, velocity, sprite);
                spawnedVehicles.add(vehicle);
            }
        }

        return spawnedVehicles;
    }

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


    public static int getVehicleVelocity(DataEnums.VehicleType vehicleType){
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

    public static int getVehicleSpacing(DataEnums.VehicleType vehicleType){
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
    public static DataEnums.VehicleType generateVehicleType(int x) {
        switch (x) {
            case 1:
                return DataEnums.VehicleType.IRON_GOLEM;
            case 2:
                return DataEnums.VehicleType.CREEPER;
            case 3:
                return DataEnums.VehicleType.SKELETON;
        }
        return DataEnums.VehicleType.SKELETON;
    }

    private void addMovementListeners(ImageButton up, ImageButton left, ImageButton right, ImageButton down) {
        up.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                character.setY(character.getY() + tileSize);
            }
        });

        right.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                character.setX(character.getX() + tileSize);
            }
        });

        left.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                character.setX(character.getX() - tileSize);
            }
        });

        down.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                character.setY(character.getY() - tileSize);
            }
        });
    }

    private String getLives(DataEnums.Difficulty difficulty) {
        switch(difficulty){
            case EASY:
                return "10";
            case HARD:
                return "1";
            case NORMAL:
                return "5";
        }
        return "NULL";
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