package com.x20.frogger.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameView {
    private Viewport viewport;
    private OrthographicCamera camera;
    public void initGameView() {
        camera = new OrthographicCamera();
        // initialize viewport to support a default world size of 10x10
        viewport = new FitViewport(10, 10, camera);
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void updateViewSize(int x, int y) {
        viewport.setWorldSize(x, y);
    }

}
