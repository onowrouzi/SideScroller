package io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.projectiles.Projectile;

public class Walker extends GroundEnemy {
    public final static int WALK_LEFT = 0;
    public final static int END_WALK_LEFT = 7;
    public final static int WALK_RIGHT = 8;
    public final static int END_WALK_RIGHT = 15;
    public final static int DEAD_LEFT = 16;
    public final static int END_DEAD_LEFT = 20;
    public final static int DEAD_RIGHT = 21;
    public final static int END_DEAD_RIGHT = 25;
    
    public Walker(float x, float y, int width, int height, Context context){
        super(x,y,width,height,context);

        sprites = new Bitmap[26];

        getSprites(context);
    }

    @Override
    public RectF getCollisionBox() {
        return new RectF(x+width*.1f, y, x+width*.9f, y+height);
    }

    @Override
    public void handleCollision(MovableFigure mf){
        if (mf instanceof Projectile) state = dying;
        if (mf instanceof Player) {
            Player p = (Player) mf;
            if ((p.isMeleeLeft() && p.x > x) || (p.isMeleeRight() && p.x+p.width/2 < x)) {
                state = dying;
            } else if (p.isJumpLeft() || p.isJumpRight()) {
                state = dying;
                p.bounceOff();
            } else {
                p.hurt();
            }
        }
    }

    @Override
    public void travelLeft() {
        if (spriteState < END_WALK_LEFT) {
            spriteState++;
        } else {
            spriteState = WALK_LEFT;
        }

        x -= width/48;
    }

    @Override
    public void travelRight() {
        if (spriteState < END_WALK_RIGHT) {
            spriteState++;
        } else {
            spriteState = WALK_RIGHT;
        }

        x += width/48;
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
        sprites[0] = super.extractImage(context.getResources(), R.drawable.walker1);
        sprites[1] = super.extractImage(context.getResources(), R.drawable.walker2);
        sprites[2] = super.extractImage(context.getResources(), R.drawable.walker3);
        sprites[3] = super.extractImage(context.getResources(), R.drawable.walker4);
        sprites[4] = super.extractImage(context.getResources(), R.drawable.walker5);
        sprites[5] = super.extractImage(context.getResources(), R.drawable.walker6);
        sprites[6] = super.extractImage(context.getResources(), R.drawable.walker7);
        sprites[7] = super.extractImage(context.getResources(), R.drawable.walker8);
        sprites[8] = super.flipImage(sprites[0]);
        sprites[9] = super.flipImage(sprites[1]);
        sprites[10] = super.flipImage(sprites[2]);
        sprites[11] = super.flipImage(sprites[3]);
        sprites[12] = super.flipImage(sprites[4]);
        sprites[13] = super.flipImage(sprites[5]);
        sprites[14] = super.flipImage(sprites[6]);
        sprites[15] = super.flipImage(sprites[7]);
        sprites[16] = sprites[21] = super.extractImage(context.getResources(), R.drawable.ground_explode1);
        sprites[17] = sprites[22] = super.extractImage(context.getResources(), R.drawable.ground_explode2);
        sprites[18] = sprites[23] = super.extractImage(context.getResources(), R.drawable.ground_explode3);
        sprites[19] = sprites[24] = super.extractImage(context.getResources(), R.drawable.ground_explode4);
        sprites[20] = sprites[25] = super.extractImage(context.getResources(), R.drawable.ground_explode5);

        for (int i = 0; i < sprites.length; i++){
            sprites[i] = Bitmap.createScaledBitmap(sprites[i], width, height, false);
        }
    }
}
