package io.github.onowrouzi.sidescroller.model.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.GameFigure;

public class DoubleScoreIcon extends GameFigure {

    Bitmap timesTwo;

    public DoubleScoreIcon(float x, float y, int width, int height, Context context) {
        super(x, y, width, height);

        timesTwo = super.extractImage(context.getResources(), R.drawable.times_two);
    }

    @Override
    public void render(Canvas c) { if (Score.dblScoreTimer > 0) c.drawBitmap(timesTwo, x, y, null); }

}
