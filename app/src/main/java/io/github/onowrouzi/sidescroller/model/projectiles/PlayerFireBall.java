package io.github.onowrouzi.sidescroller.model.projectiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class PlayerFireBall extends Projectile{

    public static final int START_LEFT = 0;
    public static final int END_LEFT = 4;
    public static final int START_RIGHT = 5;
    public static final int END_RIGHT = 9;

    public PlayerFireBall(float sx, float sy, float tx, float ty, Context context, MovableFigure owner, int streamId) {
        super(sx, sy, tx, ty, owner, streamId);
        sprites = new Bitmap[12];

        sprites[0] = super.extractImage(context.getResources(), R.drawable.fireball1);
        sprites[1] = super.extractImage(context.getResources(), R.drawable.fireball2);
        sprites[2] = super.extractImage(context.getResources(), R.drawable.fireball3);
        sprites[3] = super.extractImage(context.getResources(), R.drawable.fireball4);
        sprites[4] = super.extractImage(context.getResources(), R.drawable.fireball5);
        sprites[5] = super.flipImage(sprites[0]);
        sprites[6] = super.flipImage(sprites[1]);
        sprites[7] = super.flipImage(sprites[2]);
        sprites[8] = super.flipImage(sprites[3]);
        sprites[9] = super.flipImage(sprites[4]);

        x = sx;
        y = sy;
        width = sprites[0].getWidth();
        height = sprites[0].getHeight();

        Player p = (Player) owner;
        spriteState = (p.isFacingLeft()) ? 0 : 6;
    }

    @Override
    public void render(Canvas c) { c.drawBitmap(sprites[spriteState], super.x, super.y, super.paint); }

    @Override
    public void update(){
        if (isFacingLeft()) {
            spriteState = spriteState < END_LEFT ? spriteState + 1 : START_LEFT;
            x -= width/4;
        } else {
            spriteState = spriteState < END_RIGHT ? spriteState + 1 : START_RIGHT;
            x += width/4;
        }
    }

    @Override
    public RectF getCollisionBox() { return new RectF(x, y, x+width, y+height); }

    public boolean isFacingLeft() { return spriteState < START_RIGHT; }

    public boolean isFacingRight() { return spriteState > END_LEFT; }
}