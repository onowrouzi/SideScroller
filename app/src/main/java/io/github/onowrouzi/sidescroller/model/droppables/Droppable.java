package io.github.onowrouzi.sidescroller.model.droppables;

import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.MovableFigure;

public abstract class Droppable extends MovableFigure{

    public Droppable(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update(){ if (y+height < GameActivity.groundLevel) y += height/2; }

    @Override
    public RectF getCollisionBox() { return new RectF(x,y,x+width,y+height); }

    @Override
    public void render(Canvas c) { c.drawBitmap(sprites[0], (int)x, (int)y, null); }
}
