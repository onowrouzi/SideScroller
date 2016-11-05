package io.github.onowrouzi.sidescroller.model.projectiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;

public class Shuriken extends Projectile{

    public final int BEGIN_ROTATION = 0;
    public final int END_ROTATION = 2;

    public Shuriken(float sx, float sy, float tx, float ty, Context context, MovableFigure owner, int streamId) {
        super(sx, sy, tx, ty, owner, streamId);
        sprites = new Bitmap[3];

        sprites[0] = super.extractImage(context.getResources(), R.drawable.shuriken1);
        sprites[1] = super.extractImage(context.getResources(), R.drawable.shuriken2);
        sprites[2] = super.extractImage(context.getResources(), R.drawable.shuriken3);
    }

    @Override
    public void render(Canvas c) {
        c.drawBitmap(sprites[spriteState], super.x, super.y, super.paint);
    }

    @Override
    public void update() {
        if (spriteState < END_ROTATION) {
            spriteState++;
        } else {
            spriteState = BEGIN_ROTATION;
        }
        state.update();
    }
}
