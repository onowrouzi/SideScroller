package io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.projectiles.FireBall;

public class SpikyRoll extends GroundEnemy {
    public final static int WALK_LEFT = 0;
    public final static int END_WALK_LEFT = 8;
    public final static int WALK_RIGHT = 9;
    public final static int END_WALK_RIGHT = 17;
    public final static int DEAD_LEFT = 18;
    public final static int END_DEAD_LEFT = 22;
    public final static int DEAD_RIGHT = 23;
    public final static int END_DEAD_RIGHT = 27;

    public SpikyRoll(float x, float y, int width, int height, Context context){
        super(x,y,width,height,context);

        sprites = new Bitmap[28];

        getSprites(context);
    }

    @Override
    public void handleCollision(MovableFigure mf){
        if (mf instanceof FireBall) state = dying;
        if (mf instanceof Player) {
            Player p = (Player) mf;
            p.hurt();
            if (p.isJumpLeft() || p.isJumpRight()) p.bounceBack();
        }
    }

    @Override
    public RectF getCollisionBox() {
        return new RectF(x+width*.1f, y+height*.1f, x+width*.9f, y+height);
    }

    @Override
    public void travelLeft() {
        spriteState = spriteState < END_WALK_LEFT ? spriteState + 1 : WALK_LEFT;
        x -= width/24;
    }

    @Override
    public void travelRight() {
        spriteState = spriteState < END_WALK_RIGHT ? spriteState + 1 : WALK_RIGHT;
        x += width/24;
    }

    public boolean isFacingLeft(){ return spriteState <= END_WALK_LEFT; }

    public boolean isFacingRight(){ return (spriteState >= WALK_RIGHT && spriteState <= END_WALK_RIGHT); }

    public boolean isDyingLeft() { return (spriteState >= DEAD_LEFT && spriteState <= END_DEAD_LEFT); }

    public boolean isDyingRight() { return (spriteState >= DEAD_RIGHT && spriteState <= END_DEAD_RIGHT); }

    public void setDead() { spriteState = isFacingLeft() ? DEAD_LEFT : DEAD_RIGHT; }

    public void checkDone() {
        if (spriteState != END_DEAD_LEFT && spriteState != END_DEAD_RIGHT){
            spriteState++;
        } else {
            state = done;
        }
    }

    public void getSprites(Context context){
        sprites[9] = super.extractImage(context.getResources(), R.drawable.spiky_roll1);
        sprites[10] = super.extractImage(context.getResources(), R.drawable.spiky_roll2);
        sprites[11] = super.extractImage(context.getResources(), R.drawable.spiky_roll3);
        sprites[12] = super.extractImage(context.getResources(), R.drawable.spiky_roll4);
        sprites[13] = super.extractImage(context.getResources(), R.drawable.spiky_roll5);
        sprites[14] = super.extractImage(context.getResources(), R.drawable.spiky_roll6);
        sprites[15] = super.extractImage(context.getResources(), R.drawable.spiky_roll7);
        sprites[16] = super.extractImage(context.getResources(), R.drawable.spiky_roll8);
        sprites[17] = super.extractImage(context.getResources(), R.drawable.spiky_roll9);
        sprites[0] = super.flipImage(sprites[9]);
        sprites[1] = super.flipImage(sprites[10]);
        sprites[2] = super.flipImage(sprites[11]);
        sprites[3] = super.flipImage(sprites[12]);
        sprites[4] = super.flipImage(sprites[13]);
        sprites[5] = super.flipImage(sprites[14]);
        sprites[6] = super.flipImage(sprites[15]);
        sprites[7] = super.flipImage(sprites[16]);
        sprites[8] = super.flipImage(sprites[17]);
        sprites[18] = sprites[23] = super.extractImage(context.getResources(), R.drawable.ground_explode1);
        sprites[19] = sprites[24] = super.extractImage(context.getResources(), R.drawable.ground_explode2);
        sprites[20] = sprites[25] = super.extractImage(context.getResources(), R.drawable.ground_explode3);
        sprites[21] = sprites[26] = super.extractImage(context.getResources(), R.drawable.ground_explode4);
        sprites[22] = sprites[27] = super.extractImage(context.getResources(), R.drawable.ground_explode5);
    }
}
