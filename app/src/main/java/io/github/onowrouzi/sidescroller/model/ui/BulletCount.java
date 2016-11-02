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

public class BulletCount extends GameFigure implements Observer {
    
    Bitmap bullet;
    private int bullets;
    private int reloadTimer;

    public BulletCount(float x, float y, int width, int height, Player player, Context context) {
        super(x,y,width,height);
        
        player.attach(this);
        
        bullet = super.extractImage(context.getResources(), R.drawable.bullet_count);
        //bullet = Bitmap.createBitmap(bullet,35, 0, 65, 127);
        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
        bullets = 10;

        paint = new Paint();
    }
    
    @Override
    public void render(Canvas c) {
        paint.setColor(Color.BLACK);
        if (bullets > 0) {
            for (int i = 0; i < bullets; i++) {
                c.drawBitmap(bullet, (int)x, (int)y+i*height, null);
            }
        } else if (reloadTimer % 4 == 0) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(GameActivity.screenWidth/20);
            c.drawText("RELOADING...", x, y+GameActivity.screenWidth/20, paint);
        }
    }

    @Override
    public void updateObserver(int count, int timer) {
        bullets = count;
        reloadTimer = timer;
    }
    
}
