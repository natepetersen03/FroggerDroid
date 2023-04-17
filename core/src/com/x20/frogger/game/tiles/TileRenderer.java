package com.x20.frogger.game.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.x20.frogger.graphics.BatchException;

// todo: make use of the Renderable interface to call .render(spriteBatch) directly from TileMap
public class TileRenderer {
    private SpriteBatch spriteBatch;
    private TileMap tileMap;

    private float timeAccumulator;

    public TileRenderer(SpriteBatch spriteBatch, TileMap tileMap) {
        this.spriteBatch = spriteBatch;
        this.tileMap = tileMap;
    }

    /**
     * Call within main render loop
     * REQUIRES YOU TO BEGIN AND END A SPRITE BATCH YOURSELF
     */
    public void render() {
        try {
            // loop through entire tilemap structure and draw to the world
            for (int y = 0; y < tileMap.getHeight(); y++) {
                for (int x = 0; x < tileMap.getWidth(); x++) {
                    // draw left-to-right, bottom-to-top.
                    // bottom left is (0,0)

                    int frames = tileMap.getTile(x, y).getRenderData().getFrames();
                    int fps = tileMap.getTile(x, y).getRenderData().getFps();
                    int frame = (((int) timeAccumulator) * fps) % frames;

                    TextureRegion original = tileMap.getTile(x, y).getRenderData().getTextureRegion();
                    TextureRegion region = new TextureRegion(original);
                    region.setRegionX(original.getRegionX() + (16 * frame));
                    region.setRegionWidth(16);

                    spriteBatch.draw(
                        region,
                        x,
                        y,
                        1,
                        1
                    );
                }
            }
        } catch (IllegalStateException e) {
            throw new BatchException();
        }
        timeAccumulator += Gdx.graphics.getDeltaTime();
        timeAccumulator = Math.max(timeAccumulator, 0f);
    }
}
