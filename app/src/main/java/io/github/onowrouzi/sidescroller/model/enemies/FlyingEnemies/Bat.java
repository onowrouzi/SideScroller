package io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.projectiles.FireBall;
import io.github.onowrouzi.sidescroller.model.projectiles.Projectile;

public class Bat extends FlyingEnemy {

    public static final int START_FLAP_RIGHT = 0;
    public static final int END_FLAP_RIGHT = 5;
    public static final int START_FLAP_LEFT = 6;
    public static final int END_FLAP_LEFT = 11;
    public static final int DEAD_LEFT = 12;
    public static final int END_DEAD_LEFT = 16;
    public static final int DEAD_RIGHT = 17;
    public static final int END_DEAD_RIGHT = 21;
    Context context;
    
    public Bat(float x, float y, int width, int height, Context context) {
        super(x, y, width, height, context);
        this.context = context;

        sprites = new Bitmap[22];

        getSprites(context);
    }

    public void fireProjectile() {
        px = (int) GameActivity.gameData.player.x+GameActivity.gameData.player.width/2;
        py = (int) GameActivity.gameData.player.y+GameActivity.gameData.player.height;

        FireBall f = new FireBall(x+width/2, y+height, px, py, context, this, -1);

        synchronized (GameActivity.gameData.enemyFigures) {
            GameActivity.gameData.enemyFigures.add(f);
        }
    }

    @Override
    public void handleCollision(MovableFigure mf){
        if (mf instanceof Projectile) {
            Projectile p = (Projectile) mf;
            state = dying;
            p.state = p.dying;
        }
    }

    @Override
    public RectF getCollisionBox() {
        return new RectF(x, y, x+width, y+height*.9f);
    }

    @Override
    public void travelLeft() {
        spriteState = spriteState < END_FLAP_LEFT ? spriteState + 1: START_FLAP_LEFT;
        x -= width/32;
    }

    @Override
    public void travelRight() {
        spriteState = spriteState < END_FLAP_RIGHT ? spriteState + 1 : START_FLAP_RIGHT;
        x += width/32;
    }

    public boolean isFacingLeft(){ return (spriteState >= START_FLAP_LEFT && spriteState <= END_FLAP_LEFT); }

    public boolean isFacingRight() { return (spriteState >= START_FLAP_RIGHT && spriteState <= END_FLAP_RIGHT); }

    public boolean isDyingLeft() { return (spriteState >= DEAD_LEFT && spriteState <= END_DEAD_LEFT); }

    public boolean isDyingRight() { return (spriteState >= DEAD_RIGHT && spriteState <= END_DEAD_RIGHT); }

    public void getSprites(Context context){
        sprites[6] = super.extractImage(context.getResources(), R.drawable.bat1);
        sprites[7] = super.extractImage(context.getResources(), R.drawable.bat2);
        sprites[8] = super.extractImage(context.getResources(), R.drawable.bat3);
        sprites[9] = super.extractImage(context.getResources(), R.drawable.bat4);
        sprites[10] = super.extractImage(context.getResources(), R.drawable.bat5);
        sprites[11] = super.extractImage(context.getResources(), R.drawable.bat6);
        sprites[0] = super.flipImage(sprites[6]);
        sprites[1] = super.flipImage(sprites[7]);
        sprites[2] = super.flipImage(sprites[8]);
        sprites[3] = super.flipImage(sprites[9]);
        sprites[4] = super.flipImage(sprites[10]);
        sprites[5] = super.flipImage(sprites[11]);
        sprites[12] = sprites[17] = super.extractImage(context.getResources(), R.drawable.air_explode1);
        sprites[13] = sprites[18] = super.extractImage(context.getResources(), R.drawable.air_explode2);
        sprites[14] = sprites[19] = super.extractImage(context.getResources(), R.drawable.air_explode3);
        sprites[15] = sprites[20] = super.extractImage(context.getResources(), R.drawable.air_explode4);
        sprites[16] = sprites[21] = super.extractImage(context.getResources(), R.drawable.air_explode5);


        for (int i = 0; i < sprites.length; i++){
            sprites[i] = Bitmap.createScaledBitmap(sprites[i], width, height, false);
        }
    }
}
