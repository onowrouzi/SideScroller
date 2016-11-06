package io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.projectiles.Egg;
import io.github.onowrouzi.sidescroller.model.projectiles.Projectile;
import io.github.onowrouzi.sidescroller.model.states.AliveFlyingEnemy;
import io.github.onowrouzi.sidescroller.model.states.DoneEnemy;
import io.github.onowrouzi.sidescroller.model.states.DyingFlyingEnemy;

public abstract class FlyingEnemy extends Enemy {
     
    public int px;
    public int py;
    private Context context;
    public int fireHere;

    public FlyingEnemy(float x, float y, int width, int height, Context context) {
        super(x,y,width,height);
        
        fireHere = (int) (Math.random()*GameActivity.screenWidth);
        
        alive = new AliveFlyingEnemy(this);
        dying = new DyingFlyingEnemy(this);
        done = new DoneEnemy(this, context);
        
        state = alive;
        this.context = context;
    }
    
    @Override
    public void render(Canvas c) {
        c.drawBitmap(sprites[spriteState], (int)x, (int)y, null);
    }

    @Override
    public void update() {
        state.update(); 
    }
    
    public abstract void fireProjectile();

    public abstract boolean isFacingLeft();

    public abstract boolean isFacingRight();

    public abstract boolean isDyingLeft();

    public abstract boolean isDyingRight();

    public abstract void getSprites(Context context);

}
