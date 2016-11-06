package io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.states.AliveGroundEnemy;
import io.github.onowrouzi.sidescroller.model.states.DoneEnemy;
import io.github.onowrouzi.sidescroller.model.states.DyingGroundEnemy;

public abstract class GroundEnemy extends Enemy {
    
    public GroundEnemy(float x, float y, int width, int height, Context context) {
        super(x,y,width,height);
        
        alive = new AliveGroundEnemy(this);
        dying = new DyingGroundEnemy(this);
        done = new DoneEnemy(this, context);
        
        state = alive;
    }
    
    @Override
    public void render(Canvas c) {
        c.drawBitmap(sprites[spriteState], (int)super.x, (int)super.y, null);
    }

    @Override
    public void update() {
        state.update();
    }

    public abstract boolean isFacingLeft();

    public abstract boolean isFacingRight();

    public abstract boolean isDyingLeft();

    public abstract boolean isDyingRight();

    public abstract void setDead();

    public abstract void checkDone();

    public abstract void getSprites(Context context);

}
