package io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.projectiles.Egg;


public class Bird extends FlyingEnemy {

    public static final int START_FLAP_RIGHT = 0;
    public static final int END_FLAP_RIGHT = 3;
    public static final int START_FLAP_LEFT = 4;
    public static final int END_FLAP_LEFT = 7;
    public static final int DEAD_LEFT = 8;
    public static final int END_DEAD_LEFT = 12;
    public static final int DEAD_RIGHT = 13;
    public static final int END_DEAD_RIGHT = 17;
    Context context;
    
    public Bird(float x, float y, int width, int height, Context context) {
        super(x, y, width, height, context);

        this.context = context;

        sprites = new Bitmap[18];

        getSprites(context);
    }

    public void fireProjectile() {
        px = (int) GameActivity.gameData.player.x+GameActivity.gameData.player.width/2;
        py = (int) GameActivity.gameData.player.y+GameActivity.gameData.player.height;

        Egg e = new Egg (x+width/2, y+height, px, py, context, this, -1);

        synchronized (GameActivity.gameData.enemyFigures) {
            GameActivity.gameData.enemyFigures.add(e);
        }
    }

    @Override
    public RectF getCollisionBox() {
        return new RectF(x, y, x+width, y+height*.9f);
    }

    @Override
    public void travelLeft() {
        spriteState = spriteState < END_FLAP_LEFT ? spriteState + 1: START_FLAP_LEFT;
        x -= 5;
    }

    @Override
    public void travelRight() {
        spriteState = spriteState < END_FLAP_RIGHT ? spriteState + 1 : START_FLAP_RIGHT;
        x += 5;
    }

    public boolean isFacingLeft(){ return (spriteState >= START_FLAP_LEFT && spriteState <= END_FLAP_LEFT); }

    public boolean isFacingRight() { return (spriteState >= START_FLAP_RIGHT && spriteState <= END_FLAP_RIGHT); }

    public boolean isDyingLeft() { return (spriteState >= DEAD_LEFT && spriteState <= END_DEAD_LEFT); }

    public boolean isDyingRight() { return (spriteState >= DEAD_RIGHT && spriteState <= END_DEAD_RIGHT); }

    public void getSprites(Context context){
        sprites[4] = super.extractImage(context.getResources(), R.drawable.bird1);
        sprites[5] = super.extractImage(context.getResources(), R.drawable.bird2);
        sprites[6] = super.extractImage(context.getResources(), R.drawable.bird3);
        sprites[7] = super.extractImage(context.getResources(), R.drawable.bird4);
        sprites[0] = super.flipImage(sprites[4]);
        sprites[1] = super.flipImage(sprites[5]);
        sprites[2] = super.flipImage(sprites[6]);
        sprites[3] = super.flipImage(sprites[7]);
        sprites[8] = sprites[13] = super.extractImage(context.getResources(), R.drawable.air_explode1);
        sprites[9] = sprites[14] = super.extractImage(context.getResources(), R.drawable.air_explode2);
        sprites[10] = sprites[15] = super.extractImage(context.getResources(), R.drawable.air_explode3);
        sprites[11] = sprites[16] = super.extractImage(context.getResources(), R.drawable.air_explode4);
        sprites[12] = sprites[17] = super.extractImage(context.getResources(), R.drawable.air_explode5);


        for (int i = 0; i < sprites.length; i++){
            sprites[i] = Bitmap.createScaledBitmap(sprites[i], width, height, false);
        }
    }
}