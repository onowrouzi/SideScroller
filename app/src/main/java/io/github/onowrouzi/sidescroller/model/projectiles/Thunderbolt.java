package io.github.onowrouzi.sidescroller.model.projectiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class Thunderbolt extends Projectile{

    public Thunderbolt(float sx, float sy, float tx, float ty, Context context, MovableFigure owner, int streamId) {
        super(sx, sy, tx, ty, owner, streamId);
        sprites = new Bitmap[2];

        sprites[0] = super.extractImage(context.getResources(), R.drawable.bolt);
        sprites[1] = super.flipImage(sprites[0]);

        spriteState = (dx < 0) ? 0 : 1;
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
