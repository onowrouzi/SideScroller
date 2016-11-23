package io.github.onowrouzi.sidescroller.model.projectiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemies.BossEnemy;

public class BeastFireBall extends Projectile {

    public BeastFireBall(float sx, float sy, float tx, float ty, int width, int height, Context context, MovableFigure owner, int streamId) {
        super(sx, sy, tx, ty, width, height, owner, streamId);

        sprites = new Bitmap[2];

        sprites[0] = super.extractImage(context.getResources(), R.drawable.beast_fireball);
        sprites[1] = super.flipImage(sprites[0]);

        x = sx;
        y = sy;
        width = sprites[0].getWidth();
        height = sprites[0].getHeight();

        BossEnemy be = (BossEnemy) owner;
        spriteState = (be.isFacingLeft()) ? 1 : 0;
    }

    @Override
    public void render(Canvas c) { c.drawBitmap(sprites[spriteState], super.x, super.y, super.paint); }

    @Override
    public void update(){ x = spriteState == 1 ? x-width/2 : x+width/2; }

    @Override
    public void handleCollision(MovableFigure mf){
        if (mf instanceof Player){
            Player p = (Player) mf;
            p.hurt();
            state = dying;
        }
    }

    @Override
    public RectF getCollisionBox() { return new RectF(x, y, x+width, y+height); }
}
