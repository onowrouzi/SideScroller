package io.github.onowrouzi.sidescroller.model.projectiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class Egg extends Projectile{

    public Egg(float sx, float sy, float tx, float ty, int width, int height, Context context, MovableFigure owner, int streamId) {
        super(sx, sy, tx, ty, width, height, owner, streamId);
        sprites = new Bitmap[2];

        sprites[0] = super.extractImage(context.getResources(), R.drawable.egg);
        sprites[1] = super.flipImage(sprites[0]);

        spriteState = (dx > 0) ? 0 : 1;
    }

    @Override
    public void render(Canvas c) { c.drawBitmap(sprites[spriteState], super.x, super.y, super.paint); }

    @Override
    public void handleCollision(MovableFigure mf){
        if (mf instanceof Player){
            Player p = (Player) mf;
            p.hurt();
            state = dying;
        }
    }
}
