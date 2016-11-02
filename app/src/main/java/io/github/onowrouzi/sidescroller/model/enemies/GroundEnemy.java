package io.github.onowrouzi.sidescroller.model.enemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.states.AliveGroundEnemy;
import io.github.onowrouzi.sidescroller.model.states.DoneEnemy;
import io.github.onowrouzi.sidescroller.model.states.DyingGroundEnemy;

public class GroundEnemy extends Enemy {
    
    public final static int WALK_LEFT = 0;
    public final static int END_WALK_LEFT = 7;
    public final static int WALK_RIGHT = 8;
    public final static int END_WALK_RIGHT = 15;
    public final static int DEAD_LEFT = 16;
    public final static int END_DEAD_LEFT = 20;
    public final static int DEAD_RIGHT = 21;
    public final static int END_DEAD_RIGHT = 25;
    public char type;
    
    public GroundEnemy(float x, float y, int width, int height, char type, Context context) {
        super(x,y,width,height);
        
        sprites = new Bitmap[26];
        this.type = type;

        getSprites(context);
        
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
        return new RectF(x+width*.1f, y, x+width*.9f, y+height);
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
        sprites[0] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_enemy_a1): super.extractImage(context.getResources(), R.drawable.ground_enemy_a1);
        sprites[1] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_enemy_a2): super.extractImage(context.getResources(), R.drawable.ground_enemy_a2);
        sprites[2] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_enemy_a3): super.extractImage(context.getResources(), R.drawable.ground_enemy_a3);
        sprites[3] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_enemy_a4): super.extractImage(context.getResources(), R.drawable.ground_enemy_a4);
        sprites[4] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_enemy_a5): super.extractImage(context.getResources(), R.drawable.ground_enemy_a5);
        sprites[5] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_enemy_a6): super.extractImage(context.getResources(), R.drawable.ground_enemy_a6);
        sprites[6] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_enemy_a7): super.extractImage(context.getResources(), R.drawable.ground_enemy_a7);
        sprites[7] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_enemy_a8): super.extractImage(context.getResources(), R.drawable.ground_enemy_a8);
        sprites[8] = super.flipImage(sprites[0]);
        sprites[9] = super.flipImage(sprites[1]);
        sprites[10] = super.flipImage(sprites[2]);
        sprites[11] = super.flipImage(sprites[3]);
        sprites[12] = super.flipImage(sprites[4]);
        sprites[13] = super.flipImage(sprites[5]);
        sprites[14] = super.flipImage(sprites[6]);
        sprites[15] = super.flipImage(sprites[7]);
        sprites[16] = sprites[21] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_explode1): super.extractImage(context.getResources(), R.drawable.ground_explode1);
        sprites[17] = sprites[22] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_explode2): super.extractImage(context.getResources(), R.drawable.ground_explode2);
        sprites[18] = sprites[23] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_explode3): super.extractImage(context.getResources(), R.drawable.ground_explode3);
        sprites[19] = sprites[24] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_explode4): super.extractImage(context.getResources(), R.drawable.ground_explode4);
        sprites[20] = sprites[25] = type == 'A' ? super.extractImage(context.getResources(), R.drawable.ground_explode5): super.extractImage(context.getResources(), R.drawable.ground_explode5);

        for (int i = 0; i < sprites.length; i++){
            sprites[i] = Bitmap.createScaledBitmap(sprites[i], width, height, false);
        }
    }

}
