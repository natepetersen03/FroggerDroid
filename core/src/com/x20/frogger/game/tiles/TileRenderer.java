package com.x20.frogger.game.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x20.frogger.game.tiles.TileMap;

public class TileRenderer {
    private SpriteBatch spriteBatch;
    private TileMap tileMap;
    private byte warningCount = 0;

    public TileRenderer(SpriteBatch spriteBatch, TileMap tileMap) {
        this.spriteBatch = spriteBatch;
        this.tileMap = tileMap;
    }

    /**
     * Call within main render loop
     * Make sure to end the sprite batch before calling this.
     * The method will warn you up to 10 times before just continuing on regardless.
     */
    public void render() {
        try {
            this.spriteBatch.begin();
        } catch (IllegalStateException e) {
            if (warningCount < 10) {
                System.out.println("Warning: " + e.getMessage() + "Continuing.");
                warningCount++;
            }
        }

        // loop through entire tilemap structure and draw to the world
        for (int y = 0; y < tileMap.getHeight(); y++) {
            for (int x = 0; x < tileMap.getWidth(); x++) {
                // draw left-to-right, bottom-to-top.
                // bottom left is (0,0)

                //spriteBatch.draw(
                //    tileMap.getTileStruct(x, y).getRenderData().getTextureRegion(),
                //    x,
                //    y,
                //    1,
                //    1
                //);
            }
        }


        this.spriteBatch.end();
    }
}
