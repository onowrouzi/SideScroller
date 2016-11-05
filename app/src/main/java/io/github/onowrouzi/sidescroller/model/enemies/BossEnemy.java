package io.github.onowrouzi.sidescroller.model.enemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.projectiles.Projectile;
import io.github.onowrouzi.sidescroller.model.projectiles.Shuriken;
import io.github.onowrouzi.sidescroller.model.states.AliveBoss;
import io.github.onowrouzi.sidescroller.model.states.DescendingBoss;
import io.github.onowrouzi.sidescroller.model.states.DoneEnemy;
import io.github.onowrouzi.sidescroller.model.states.DyingBoss;
import io.github.onowrouzi.sidescroller.model.states.FigureState;
import io.github.onowrouzi.sidescroller.model.states.HurtBoss;

public class BossEnemy extends Enemy {
    
    public FigureState drop;
    public int attackTimer;
    public float px;
    public float py;
    public float ex;
    public float ey;
    public int eSize;
    public final int MAX_EXP_SIZE = 50;
    public final int FACE_LEFT = 0;
    public final int HURT_LEFT = 1;
    public final int FACE_RIGHT = 2;
    public final int HURT_RIGHT = 3;
    public boolean hasAttacked;
    Context context;
    
    public BossEnemy(float x, float y, int width, int height, Context context){
        super(x,y,width,height);
        
        sprites = new Bitmap[4];
        sprites[0] = super.extractImage(context.getResources(), R.drawable.boss_enemy1);
        sprites[1] = super.extractImage(context.getResources(), R.drawable.boss_enemy2);
        sprites[2] = super.flipImage(sprites[0]);
        sprites[3] = super.flipImage(sprites[1]);
        
        health = 5; 
        immuneTimer = 0;
        attackTimer = 20;
        
        drop = new DescendingBoss(this);
        alive = new AliveBoss(this);
        hurt = new HurtBoss(this);
        dying = new DyingBoss(this);
        done = new DoneEnemy(this);

        paint = new Paint();
        this.context = context;

        state = drop;
    }
    
    @Override
    public void render(Canvas c) {
        if (immuneTimer % 2 == 0)
            c.drawBitmap(sprites[spriteState], (int)x, (int)y, null);
        if (state == dying){
            c.drawBitmap(sprites[spriteState], (int)x, (int)y, null);
            paint.setColor(Color.WHITE);
            c.drawCircle((int)ex, (int)ey, eSize, paint);
        }
    }

    @Override
    public RectF getCollisionBox() {
        return new RectF(x+5,y+5,width,height);
    }

    @Override
    public void travelLeft() {
        if (y > 100 && x > 400) {
            y -= 20;
        } else if (x > 20) {
            x -= 20;
        } else if (y+height < 450){
            y += 20;
        }
    }

    @Override
    public void travelRight() {
        if (y > 100 && x < 400) {
            y -= 20;
        } else if (x+width < 780) {
            x += 20;
        } else if (y+height < 450){
            y += 20;
        }
    }

    @Override
    public void update() {
        state.update();
        attackTimer--;
    }
    
    public void hurt() {
        if (immuneTimer == 0) {
            immuneTimer = 75 ;
            health--;
        }
    }
    
    public void dropAttack(){
        if (y+height < 450 && !hasAttacked){
            y += 40;
        } else {
            hasAttacked = true;
        } 
    }
    
    public void fireProjectile() {
        px = (int)GameActivity.gameData.player.x+(int)GameActivity.gameData.player.width/2;
        py = (int)GameActivity.gameData.player.y+GameActivity.gameData.player.height/2;
        float bx;
        if (spriteState <= HURT_LEFT) {
            bx = x+5;
        } else {
            bx = x+width-5;
        }
        
        Shuriken m1 = new Shuriken (bx, y+height/2, px, py, context, this, -1);
        
        Shuriken m2 = new Shuriken (bx, y+height/2, px+60, py+60, context, this, -1);
        
        Shuriken m3 = new Shuriken (bx, y+height/2, px-60, py-60, context, this, -1);
        
        synchronized (GameActivity.gameData.enemyFigures) {
            GameActivity.gameData.enemyFigures.add(m1);
            GameActivity.gameData.enemyFigures.add(m2);
            GameActivity.gameData.enemyFigures.add(m3);
        }
    }
    
    
}
