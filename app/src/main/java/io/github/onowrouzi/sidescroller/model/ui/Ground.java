package io.github.onowrouzi.sidescroller.model.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.GameFigure;

public class Ground extends GameFigure {

    public Bitmap groundImage;
    
    public Ground(float x, float y, int width, int height, Context context) {
        super(x, y, width, height);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        groundImage = super.extractImage(context.getResources(), R.drawable.ground);
        groundImage = Bitmap.createScaledBitmap(groundImage, width, height, false);
    }

    @Override
    public void render(Canvas c) {
        c.drawBitmap(groundImage, (int)super.x, (int)super.y, null); //width, super.height, null);
    }
    
}
