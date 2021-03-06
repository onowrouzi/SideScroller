package io.github.onowrouzi.sidescroller.model.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.GameFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class HealthBars extends GameFigure implements Observer {
    
    private int health;
    private int immuneTimer;
    public Bitmap heart;

    public HealthBars(float x, float y, int width, int height, Player player, Context context) {
        super(x,y,width,height);
        health = 6;
        heart = super.extractImage(context.getResources(), R.drawable.heart);
        player.attach(this);
    }
    
    @Override
    public void render(Canvas c) {
        for (int i = 0; i < health; i++){
            c.drawBitmap(heart, (int)x+i*width/2+width,0, null);
        }
    }

    @Override
    public void updateObserver(int count, int timer) {
        health = count;
        immuneTimer = timer;
    }
    
}
