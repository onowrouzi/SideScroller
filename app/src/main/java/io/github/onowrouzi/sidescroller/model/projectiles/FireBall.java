package io.github.onowrouzi.sidescroller.model.projectiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;

public class FireBall extends Projectile{

    public FireBall(float sx, float sy, float tx, float ty, Context context, MovableFigure owner) {
        super(sx, sy, tx, ty, owner);
        sprites = new Bitmap[2];

        sprites[0] = super.extractImage(context.getResources(), R.drawable.fire);
        sprites[1] = super.flipImage(sprites[0]);

        spriteState = (dx > 0) ? 0 : 1;
    }

    @Override
    public void render(Canvas c) { c.drawBitmap(sprites[spriteState], super.x, super.y, super.paint); }

}
