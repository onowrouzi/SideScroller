package io.github.onowrouzi.sidescroller.model.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.GameFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class ShieldCount extends GameFigure implements Observer {

    Bitmap shield;
    public int shieldCount;

    public ShieldCount(float x, float y, int width, int height, Player player, Context context) {
        super(x, y, width, height);

        shield = super.extractImage(context.getResources(), R.drawable.shield);
        shield = Bitmap.createScaledBitmap(shield, width, height, false);
        shieldCount = 0;

        player.attach(this);
    }

    @Override
    public void render(Canvas c) {
        for (int i = 0; i < shieldCount; i++){
            c.drawBitmap(shield, (int)x+i*width/2+width, y, null);
        }
    }

    @Override
    public void updateObserver(int count, int timer) { shieldCount = count; }
}
