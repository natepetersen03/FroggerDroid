package com.x20.frogger.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.x20.frogger.graphics.AssetManagerSingleton;

public class Sfx {
    private static Sound winSound;
    private static Sound deathSound;
    private static Music music;

    public static void initializeSounds() {
        music = AssetManagerSingleton.getInstance().getAssetManager()
            .get("sfx/music.wav", Music.class);
    }

    public static void playWinSound() {
        if (winSound == null) {
            winSound = AssetManagerSingleton.getInstance().getAssetManager()
                .get("sfx/win.wav", Sound.class);
        }
        winSound.play();
    }

    public static void playDeathSound() {
        if (deathSound == null) {
            deathSound = AssetManagerSingleton.getInstance().getAssetManager()
                .get("sfx/hit1.wav", Sound.class);
        }
        deathSound.play();
    }

    public static void playMusic() {
        if (music == null) {
            music = AssetManagerSingleton.getInstance().getAssetManager()
                .get("sfx/music.wav", Music.class);
            music.setLooping(true);
        }
        music.play();
    }

    public static void stopMusic() {
        if (music == null) {
            music = AssetManagerSingleton.getInstance().getAssetManager()
                .get("sfx/music.wav", Music.class);
            music.setLooping(true);
        }
        music.stop();
    }

    public static void dispose() {
        music.dispose();
        deathSound.dispose();
        winSound.dispose();
    }
}
