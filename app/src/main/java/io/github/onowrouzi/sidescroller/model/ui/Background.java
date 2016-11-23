package io.github.onowrouzi.sidescroller.model.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.GameFigure;
import io.github.onowrouzi.sidescroller.model.Player;


public class Background extends GameFigure {
    
    public static Bitmap b1, b2;
    public int b1X, b2X;
    public final int WIDTH;
    
    public Background(Context context) {
        super(0,0,GameActivity.screenWidth*2,GameActivity.screenHeight);

        b1 = b2 = super.extractImage(context.getResources(), R.drawable.background1);
        b1X = 0;
        b2X = b1.getWidth();
        WIDTH = b2X - GameActivity.screenWidth;
    }

    @Override
    public void render(Canvas c) {
        c.drawBitmap(b1, b1X, (int)y, null);
        if (b2X < b1.getWidth()) c.drawBitmap(b2, b2X, (int)y, null);
    }
    
    public void moveBackground(){
        b1X -= 20;
        b2X -= 20;
        
        if (b2X/10 == 0){
            b1X = 0;
            b2X = b1.getWidth();
        }
        
        for (GameFigure e : GameActivity.gameData.enemyFigures) {
            e.x -= 20;
        }

        for (GameFigure d : GameActivity.gameData.droppableFigures) {
            d.x -= 20;
        }

        for (GameFigure f : GameActivity.gameData.friendFigures) {
            if (!(f instanceof Player)) f.x-=20;
        }

        for (GameFigure p : GameActivity.gameData.powerUpFigures) {
            p.x -= 20;
        }
    }
    
}