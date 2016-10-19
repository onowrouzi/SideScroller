package io.github.onowrouzi.sidescroller.model.ui;

import android.graphics.Bitmap;

import io.github.onowrouzi.sidescroller.model.GameFigure;

public class Ground extends GameFigure {

    public Bitmap groundImage;
    
    public Ground(float x, float y, int width, int height) {
        super(x, y, width, height);
        
        groundImage = super.extractImage("images/ground.png");
        groundImage = Bitmap.createBitmap(groundImage, 0, 1327, 3072, 200);
    }

//    @Override
//    public void render(Graphics2D g) {
//        //g.drawImage(groundImage, (int)super.x, (int)super.y, width, super.height, null);
//    }
    
}
