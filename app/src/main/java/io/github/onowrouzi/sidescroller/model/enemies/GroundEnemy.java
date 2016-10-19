package io.github.onowrouzi.sidescroller.model.enemies;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.states.AliveGroundEnemy;
import io.github.onowrouzi.sidescroller.model.states.DoneEnemy;
import io.github.onowrouzi.sidescroller.model.states.DyingGroundEnemy;

public class GroundEnemy extends Enemy {
    
    public final static int STAND_LEFT = 0;
    public final static int WALK_LEFT = 1;
    public final static int DEAD_LEFT = 2;
    public final static int STAND_RIGHT = 3;
    public final static int WALK_RIGHT = 4;
    public final static int DEAD_RIGHT = 5;
    public char type;
    
    public GroundEnemy(float x, float y, int width, int height, char type, Resources resources) {
        super(x,y,width,height);
        
        sprites = new Bitmap[6];
        this.type = type;

        sprites[0] = type == 'A' ? super.extractImage(resources, R.drawable.groundenemy_a1): super.extractImage(resources, R.drawable.groundenemy_b1);
        sprites[1] = type == 'A' ? super.extractImage(resources, R.drawable.groundenemy_a2): super.extractImage(resources, R.drawable.groundenemy_b2);
        sprites[2] = type == 'A' ? super.extractImage(resources, R.drawable.groundenemy_a3): super.extractImage(resources, R.drawable.groundenemy_b3);
        sprites[3] = super.flipImage(sprites[0]);
        sprites[4] = super.flipImage(sprites[1]);
        sprites[5] = super.flipImage(sprites[2]);
        
        alive = new AliveGroundEnemy(this);
        dying = new DyingGroundEnemy(this);
        done = new DoneEnemy(this);
        
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

    @Override
    public RectF getCollisionBox() {
        return new RectF(x+5, y+10, width*.9f, height);
    }

    @Override
    public void travelLeft() {
        if (spriteState == STAND_LEFT) {
            spriteState = WALK_LEFT;
        } else {
            spriteState = STAND_LEFT;
        }
        
        x -= 5;
    }

    @Override
    public void travelRight() {
        if (spriteState == STAND_RIGHT) {
            spriteState = WALK_RIGHT;
        } else {
            spriteState = STAND_RIGHT;
        }
        
        x += 5;
    }

}
