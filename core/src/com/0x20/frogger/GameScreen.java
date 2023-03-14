package com.badlogic.drop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    private final Drop game;

    private Texture dropImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainMusic;
    private OrthographicCamera camera;
    private Rectangle bucket;
    private Vector3 touchPos = Vector3.Zero;
    private Array<Rectangle> raindrops;
    private long lastDropTime; // in nanoseconds
    private boolean paused;
    private int dropsGathered;

    public GameScreen(final Drop game) {
        this.game = game;

        // textures are 64x64
        dropImage = game.getAssetManager().get("drop.png", Texture.class);
        bucketImage = game.getAssetManager().get("bucket.png", Texture.class);

        // placeholder sounds
        dropSound = game.getAssetManager().get("drop.wav", Sound.class);
        rainMusic = game.getAssetManager().get("rain.wav", Music.class);
        rainMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    @Override
    public void show() {
        // start rain music when screen is shown
        rainMusic.play();
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            update();

            // clear the screen with a dark blue background
            ScreenUtils.clear(0, 0, 0.2f, 1);
            camera.update();
            // use the coordinate system provided by the camera
            game.getBatch().setProjectionMatrix(camera.combined);
            // parallel draw calls
            game.getBatch().begin();
            game.getBatch().draw(bucketImage, bucket.x, bucket.y);
            for (Rectangle raindrop: raindrops) {
                game.getBatch().draw(dropImage, raindrop.x, raindrop.y);
            }
            game.getBatch().end();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    public void update() {
        // touch input snapping
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            // transform screen coordinates into camera-space coordinates
            camera.unproject(touchPos);
            bucket.x = touchPos.x - bucket.width / 2;
        }

        // keyboard input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }

        // bounds restriction
        if (bucket.x < 0) {
            bucket.x = 0;
        }
        if (bucket.x > 800 - bucket.width) {
            bucket.x = 800 - bucket.width;
        }

        // rectangle update
        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + raindrop.height < 0) {
                iter.remove();
            }

            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }

        // raindrop spawn
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrop();
        }
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.width = 64;
        raindrop.height = 64;
        raindrop.x = MathUtils.random(0, 800 - raindrop.width);
        raindrop.y = 480;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        // now handled in Drop.java by the AssetManager
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