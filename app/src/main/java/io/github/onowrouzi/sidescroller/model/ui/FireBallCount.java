package io.github.onowrouzi.sidescroller.model.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.GameFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class FireBallCount extends GameFigure implements Observer {

    Bitmap fireBall;
    private int fireBalls;
    public FireBallCount(float x, float y, int width, int height, Player player, Context context) {
        super(x, y, width, height);

        player.attach(this);

        fireBall = super.extractImage(context.getResources(), R.drawable.fire);
        fireBalls = 5;
    }

    @Override
    public void render(Canvas c) {
        for (int i = 0; i < fireBalls; i++)
            c.drawBitmap(fireBall, (int)x, (int)y+i*height, null);
    }

    @Override
    public void updateObserver(int count, int timer) { fireBalls = count; }
}
