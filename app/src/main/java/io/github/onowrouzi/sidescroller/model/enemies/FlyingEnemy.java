package io.github.onowrouzi.sidescroller.model.enemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.Projectile;
import io.github.onowrouzi.sidescroller.model.states.AliveFlyingEnemy;
import io.github.onowrouzi.sidescroller.model.states.DoneEnemy;
import io.github.onowrouzi.sidescroller.model.states.DyingFlyingEnemy;

public class FlyingEnemy extends Enemy {
     
    private int px;
    private int py;
    public int fireHere;
    public static final int START_FLAP_RIGHT = 0;
    public static final int END_FLAP_RIGHT = 3;
    public static final int START_FLAP_LEFT = 4;
    public static final int END_FLAP_LEFT = 7;
    public static final int DEAD_LEFT = 8;
    public static final int END_DEAD_LEFT = 12;
    public static final int DEAD_RIGHT = 13;
    public static final int END_DEAD_RIGHT = 17;
    
    public FlyingEnemy(float x, float y, int width, int height, Context context) {
        super(x,y,width,height);
        
        fireHere = (int) (Math.random()*8) * 100;
        sprites = new Bitmap[18];

        getSprites(context);
        
        alive = new AliveFlyingEnemy(this);
        dying = new DyingFlyingEnemy(this);
        done = new DoneEnemy(this);
        
        state = alive;
        
    }
    
    @Override
    public void render(Canvas c) {
        c.drawBitmap(sprites[spriteState], (int)x, (int)y, null);
    }

    @Override
    public void update() {
        state.update(); 
    }
    
    public void fireProjectile() {
        px = (int) GameActivity.gameData.player.x+(int)GameActivity.gameData.player.width/2;
        py = (int)GameActivity.gameData.player.y+GameActivity.gameData.player.height;

        Projectile m = new Projectile (
                x+width/2, 
                y+height,
                px, py,
                Color.WHITE, Color.BLACK, this);

        synchronized (GameActivity.gameData.enemyFigures) {
            GameActivity.gameData.enemyFigures.add(m);
        }
    }

    @Override
    public RectF getCollisionBox() {
        return new RectF(x, y, x+width, y+height);
    }

    @Override
    public void travelLeft() {
        if (spriteState < END_FLAP_LEFT) {
            spriteState++;
        } else {
            spriteState =  START_FLAP_LEFT;
        }
        x -= 5;
    }

    @Override
    public void travelRight() {
        if (spriteState < END_FLAP_RIGHT){
            spriteState++;
        } else {
            spriteState = START_FLAP_RIGHT;
        }
        x += 5;
    }

    public boolean isFacingLeft(){ return (spriteState >= START_FLAP_LEFT && spriteState <= END_FLAP_LEFT); }

    public boolean isFacingRight() { return (spriteState >= START_FLAP_RIGHT && spriteState <= END_FLAP_RIGHT); }

    public boolean isDyingLeft() { return (spriteState >= DEAD_LEFT && spriteState <= END_DEAD_LEFT); }

    public boolean isDyingRight() { return (spriteState >= DEAD_RIGHT && spriteState <= END_DEAD_RIGHT); }

    public void getSprites(Context context){
        sprites[4] = super.extractImage(context.getResources(), R.drawable.flying_enemy1);
        sprites[5] = super.extractImage(context.getResources(), R.drawable.flying_enemy2);
        sprites[6] = super.extractImage(context.getResources(), R.drawable.flying_enemy3);
        sprites[7] = super.extractImage(context.getResources(), R.drawable.flying_enemy4);
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
