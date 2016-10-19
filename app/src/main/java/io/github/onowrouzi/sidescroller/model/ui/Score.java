package io.github.onowrouzi.sidescroller.model.ui;

import android.graphics.Color;

import io.github.onowrouzi.sidescroller.model.GameFigure;

public class Score extends GameFigure {

    public static int score;
    
    public Score(float x, float y, int width, int height){
        super(x,y,width,height);
        score = 0;
    }

//    @Override
//    public void render(Graphics2D g) {
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("Courier New", Font.BOLD, 30));
//        g.drawString("SCORE: " + Integer.toString(score), x, y);
//    }
    
}
