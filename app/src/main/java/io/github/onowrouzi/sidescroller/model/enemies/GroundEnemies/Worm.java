package io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.R;

public class Worm extends GroundEnemy {
    public final static int WALK_LEFT = 0;
    public final static int END_WALK_LEFT = 5;
    public final static int WALK_RIGHT = 6;
    public final static int END_WALK_RIGHT = 10;
    public final static int DEAD_LEFT = 11;
    public final static int END_DEAD_LEFT = 15;
    public final static int DEAD_RIGHT = 16;
    public final static int END_DEAD_RIGHT = 20;

    public Worm(float x, float y, int width, int height, Context context){
        super(x,y,width,height,context);
        
        sprites = new Bitmap[21];

        getSprites(context);
    }

    @Override
    public RectF getCollisionBox() {
        return new RectF(x, y+height*.2f, x+width, y+height);
    }

    @Override
    public void travelLeft() {
        if (spriteState < END_WALK_LEFT) {
            spriteState++;
        } else {
            spriteState = WALK_LEFT;
        }

        x -= 2;
    }

    @Override
    public void travelRight() {
        if (spriteState < END_WALK_RIGHT) {
            spriteState++;
        } else {
            spriteState = WALK_RIGHT;
        }

        x += 2;
    }

    public boolean isFacingLeft(){ return spriteState <= END_WALK_LEFT; }

    public boolean isFacingRight(){ return (spriteState >= WALK_RIGHT && spriteState <= END_WALK_RIGHT); }

    public boolean isDyingLeft() { return (spriteState >= DEAD_LEFT && spriteState <= END_DEAD_LEFT); }

    public boolean isDyingRight() { return (spriteState >= DEAD_RIGHT && spriteState <= END_DEAD_RIGHT); }

    public void getSprites(Context context){
        sprites[0] = super.extractImage(context.getResources(), R.drawable.worm1);
        sprites[1] = super.extractImage(context.getResources(), R.drawable.worm2);
        sprites[2] = super.extractImage(context.getResources(), R.drawable.worm3);
        sprites[3] = super.extractImage(context.getResources(), R.drawable.worm4);
        sprites[4] = super.extractImage(context.getResources(), R.drawable.worm5);
        sprites[5] = super.extractImage(context.getResources(), R.drawable.worm6);
        sprites[6] = super.flipImage(sprites[0]);
        sprites[7] = super.flipImage(sprites[1]);
        sprites[8] = super.flipImage(sprites[2]);
        sprites[9] = super.flipImage(sprites[3]);
        sprites[10] = super.flipImage(sprites[4]);
        sprites[11] = sprites[16] = super.extractImage(context.getResources(), R.drawable.ground_explode1);
        sprites[12] = sprites[17] = super.extractImage(context.getResources(), R.drawable.ground_explode2);
        sprites[13] = sprites[18] = super.extractImage(context.getResources(), R.drawable.ground_explode3);
        sprites[14] = sprites[19] = super.extractImage(context.getResources(), R.drawable.ground_explode4);
        sprites[15] = sprites[20] = super.extractImage(context.getResources(), R.drawable.ground_explode5);

        for (int i = 0; i < sprites.length; i++){
            sprites[i] = Bitmap.createScaledBitmap(sprites[i], width, height, false);
        }
    }
}
