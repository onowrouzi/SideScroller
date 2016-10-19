package io.github.onowrouzi.sidescroller.model.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import io.github.onowrouzi.sidescroller.model.GameFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class HealthBars extends GameFigure implements Observer {
    
    private int health;
    private int immuneTimer;
    public Bitmap heart;

    public HealthBars(float x, float y, int width, int height, Player player) {
        super(x,y,width,height);
        health = 5;
        heart = super.extractImage("images/heart.png");
        player.attach(this);
    }
    
    @Override
    public void render(Canvas c) {
//        g.setColor(Color.RED);
//        g.setFont(new Font("Courier New", Font.BOLD, 30));
//        g.drawString("HEALTH: ", x, y);
        for (int i = 0; i < health; i++){
            c.drawBitmap(heart, (int)x+i*30+110,0, null);
        }
    }

    @Override
    public void updateObserver(int count, int timer) {
        health = count;
        immuneTimer = timer;
    }
    
}
