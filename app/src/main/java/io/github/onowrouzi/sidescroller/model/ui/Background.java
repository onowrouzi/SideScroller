package io.github.onowrouzi.sidescroller.model.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.GameFigure;


public class Background extends GameFigure {
    
    public static Bitmap b1, b2;
    public int b1X, b2X;
    public final int WIDTH;
    
    public Background() {
        super(0,0,800,600);
        
        b1 = b2 = BitmapFactory.decodeFile("images/background1.png");
        b1X = 0;
        b2X = b1.getWidth();
        WIDTH = b2X - 800;
    }

    @Override
    public void render(Canvas c) { // Need to reimplement scrolling.
        c.drawBitmap(b1, b1X, (int)y, null);
        if (b2X < b1.getWidth()) {
            c.drawBitmap(b2, b2X, (int)y, null);
        }
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
    }
    
    public void changeBackground(String image){
        b1 = b2 = super.extractImage(image);
        b1X = 0;
    }
    
}
