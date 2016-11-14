package io.github.onowrouzi.sidescroller.model.PowerUps;

import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Travel;

public abstract class PowerUp extends MovableFigure implements Travel {
    public int originX;

    public PowerUp(float x, float y, int width, int height) {
        super(x, y, width, height);
        originX = (int)x;
    }

    @Override
    public void update(){
        if (originX < 0) travelRight();
        else travelLeft();
    }

    @Override
    public void travelLeft(){ x -= width/12; }

    @Override
    public void travelRight(){ x += width/12; }

    @Override
    public void render(Canvas c){ c.drawBitmap(sprites[0], x, y, null); }

    @Override
    public RectF getCollisionBox() { return new RectF(x, y, x+width, y+height); }
}
