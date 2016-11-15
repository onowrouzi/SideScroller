package io.github.onowrouzi.sidescroller.model.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.MainActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.GameFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class InvincibilityCount extends GameFigure implements Observer {

    Bitmap star;
    public int invincibilityCount;

    public InvincibilityCount(float x, float y, int width, int height, Player player, Context context) {
        super(x, y, width, height);

        star = super.extractImage(context.getResources(), R.drawable.star);
        star = Bitmap.createScaledBitmap(star, width, height, false);
        invincibilityCount = 0;

        player.attach(this);

        paint = new Paint();
    }

    @Override
    public void render(Canvas c) {
        if (invincibilityCount > 0) {
            paint.setColor(Color.WHITE);
            paint.setTypeface(MainActivity.font);
            paint.setTextSize(height/2);
            c.drawBitmap(star, x, y, null);
            c.drawText(Integer.toString(invincibilityCount/10 + 1), x+width, (int)(y+height*.7), paint);
        }
    }

    @Override
    public void updateObserver(int count, int timer) { invincibilityCount = count; }

}
