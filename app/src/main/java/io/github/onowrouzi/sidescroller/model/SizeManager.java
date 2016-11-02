package io.github.onowrouzi.sidescroller.model;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class SizeManager {

    Display display;
    final Point size;

    public SizeManager(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
    }

    public int getX(){
        return size.x;
    }

    public int getY(){
        return size.y;
    }
}
