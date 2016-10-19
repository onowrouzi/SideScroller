package io.github.onowrouzi.sidescroller.model.enemies;

import android.content.res.Resources;
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
    public static final int DEAD_RIGHT = 4;
    public static final int END_RIGHT = 7;
    public static final int START_FLAP_LEFT = 8;
    public static final int END_FLAP_LEFT = 11;
    public static final int DEAD_LEFT = 12;
    public static final int END_LEFT = 15;
    
    public FlyingEnemy(float x, float y, int width, int height, Resources resources) {
        super(x,y,width,height);
        
        fireHere = (int) (Math.random()*8) * 100;
        sprites = new Bitmap[16];

        sprites[0] = super.extractImage(resources, R.drawable.flyingenemy1);
        sprites[1] = super.extractImage(resources, R.drawable.flyingenemy2);
        sprites[2] = super.extractImage(resources, R.drawable.flyingenemy3);
        sprites[3] = super.extractImage(resources, R.drawable.flyingenemy4);
        sprites[4] = super.extractImage(resources, R.drawable.flyingenemy5);
        sprites[5] = super.extractImage(resources, R.drawable.flyingenemy6);
        sprites[6] = super.extractImage(resources, R.drawable.flyingenemy7);
        sprites[7] = super.extractImage(resources, R.drawable.flyingenemy8);
        sprites[8] = super.flipImage(sprites[0]);
        sprites[9] = super.flipImage(sprites[1]);
        sprites[10] = super.flipImage(sprites[2]);
        sprites[11] = super.flipImage(sprites[3]);
        sprites[12] = super.flipImage(sprites[4]);
        sprites[13] = super.flipImage(sprites[5]);
        sprites[14] = super.flipImage(sprites[6]);
        sprites[15] = super.flipImage(sprites[7]);
        
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
                Color.RED, Color.BLUE, this);

        synchronized (GameActivity.gameData.enemyFigures) {
            GameActivity.gameData.enemyFigures.add(m);
        }
    }

    @Override
    public RectF getCollisionBox() {
        return new RectF(x+10, y+10, super.width*.8f, super.height*.8f);
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

}
