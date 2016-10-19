package io.github.onowrouzi.sidescroller.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;

public abstract class GameFigure {
    
    public float x;
    public float y;
    public int width;
    public int height;
    public Paint paint;
    
    public GameFigure(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public abstract void render(Canvas c);

    public Bitmap extractImage(Resources resources, int id) {
        Bitmap img = null;
        try {
            img = BitmapFactory.decodeResource(resources, id);
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Error: Cannot open " + image);
            System.exit(-1);
        }
        return img;
    }
    //Below code retrieved and modifiied from http://stackoverflow.com/questions/7925278/drawing-mirrored-bitmaps-in-android
    public Bitmap flipImage(Bitmap image) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap flippedImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, false);
        flippedImage.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return Bitmap.createBitmap(flippedImage);
    }

    
}
