package com.x20.frogger.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * nils honermann (zeg)
 * - no warranty, free to use
 */
public class AnimatedActor extends Actor {

    private Animation animation;

    public void dispose() {
        for (Object tr : animation.getKeyFrames()) {
            ((TextureRegion) tr).getTexture().dispose();
        }
        animation = null;
    }

    public AnimatedActor(Animation animation) {
        this.animation = animation;
        TextureRegion first = (TextureRegion) animation.getKeyFrame(0f);
        setBounds(
            first.getRegionX(), first.getRegionY(), first.getRegionWidth(), first.getRegionHeight()
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // todo: Should be multiplied with the actor's alpha,
        //  allowing a parent's alpha to affect all children.
        // stateTime is millis into seconds float
        TextureRegion current = (TextureRegion) animation.getKeyFrame(elapsed / 1000.0f);
        batch.draw(
            current,
            getX(), getY(),
            getOriginX(), getOriginY(),
            getWidth(), getHeight(),
            getScaleX(), getScaleY(),
            getRotation()
        );

        if (renderedOnce) {
            elapsed += System.currentTimeMillis() - millisLastRender;
        }
        millisLastRender = System.currentTimeMillis();
        renderedOnce = true;
    }

    private float elapsed = 0;
    private boolean renderedOnce;
    private long millisLastRender = 0;
}