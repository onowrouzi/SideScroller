package io.github.onowrouzi.sidescroller.controller;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;

public class SoundsManager {

    public static SoundPool sounds;
    private boolean loaded;
    int shoot, slash, jump, hurt, explode;

    public SoundsManager(Context context){
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        sounds.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        shoot = sounds.load(context, R.raw.shoot, 3);
        slash = sounds.load(context, R.raw.slash, 1);
        jump = sounds.load(context, R.raw.jump, 4);
        hurt = sounds.load(context, R.raw.hurt, 5);
        explode = sounds.load(context, R.raw.explode, 2);
    }

    public void play(String sound){
        if (loaded && GameActivity.soundsEnabled) {
            switch (sound) {
                case "shoot": sounds.play(shoot, 100, 100, 1, 0, 1f);
                            break;
                case "slash": sounds.play(slash, 100, 100, 1, 0, 1f);
                            break;
                case "jump": sounds.play(jump, 100, 100, 1, 0, 1f);
                            break;
                case "hurt": sounds.play(hurt, 100, 100, 1, 0, 1f);
                            break;
                case "explode": sounds.play(explode, 100, 100, 1, 0, 1f);
                            break;
            }
        }
    }

}