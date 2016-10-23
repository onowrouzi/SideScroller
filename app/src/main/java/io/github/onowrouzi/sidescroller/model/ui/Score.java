package io.github.onowrouzi.sidescroller.model.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import io.github.onowrouzi.sidescroller.model.GameFigure;

public class Score extends GameFigure {

    public static int score;
    
    public Score(float x, float y, int width, int height){
        super(x,y,width,height);
        score = 0;
        paint = new Paint();
    }

    @Override
    public void render(Canvas c) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        c.drawText("SCORE: " + Integer.toString(score), x, y, paint);
    }
    
}
