package io.github.onowrouzi.sidescroller.model.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.GameFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class ShurikenCount extends GameFigure implements Observer {
    
    Bitmap shuriken;
    private int shurikens;

    public ShurikenCount(float x, float y, int width, int height, Player player, Context context) {
        super(x,y,width,height);
        
        player.attach(this);

        shuriken = super.extractImage(context.getResources(), R.drawable.shuriken_count);
        shuriken = Bitmap.createScaledBitmap(shuriken, width, height, false);
        shurikens = 10;
    }
    
    @Override
    public void render(Canvas c) {
        for (int i = 0; i < shurikens; i++)
            c.drawBitmap(shuriken, (int)x, (int)y+i*height, null);
    }

    @Override
    public void updateObserver(int count, int timer) { shurikens = count; }
    
}