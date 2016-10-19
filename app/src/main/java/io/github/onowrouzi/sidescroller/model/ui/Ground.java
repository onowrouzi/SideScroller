package io.github.onowrouzi.sidescroller.model.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.GameFigure;

public class Ground extends GameFigure {

    public Bitmap groundImage;
    
    public Ground(float x, float y, int width, int height, Resources resources) {
        super(x, y, width, height);
        
        groundImage = super.extractImage(resources, R.drawable.ground);
        groundImage = Bitmap.createBitmap(groundImage, 0, 1327, 3072, 200);
    }

    @Override
    public void render(Canvas c) {
        c.drawBitmap(groundImage, (int)super.x, (int)super.y, null); //width, super.height, null);
    }
    
}
