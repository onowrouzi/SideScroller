package io.github.onowrouzi.sidescroller.model.enemies.BossEnemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.projectiles.BeastFireBall;
import io.github.onowrouzi.sidescroller.model.states.AliveBoss;
import io.github.onowrouzi.sidescroller.model.states.DescendingBoss;
import io.github.onowrouzi.sidescroller.model.states.DoneEnemy;
import io.github.onowrouzi.sidescroller.model.states.DyingBoss;
import io.github.onowrouzi.sidescroller.model.states.FigureState;
import io.github.onowrouzi.sidescroller.model.states.HurtBoss;
import io.github.onowrouzi.sidescroller.model.states.RagingBoss;

public class BossEnemy extends Enemy {
    
    public FigureState drop, raging;
    public int attackTimer;
    public static final int FLY_RIGHT = 0;
    public static final int END_FLY_RIGHT = 7;
    public static final int FLY_LEFT = 8;
    public static final int END_FLY_LEFT = 15;
    public static final int SHOOT_RIGHT = 16;
    public static final int END_SHOOT_RIGHT = 18;
    public static final int SHOOT_LEFT = 19;
    public static final int END_SHOOT_LEFT = 21;
    public static final int HURT_RIGHT = 22;
    public static final int END_HURT_RIGHT = 23;
    public static final int HURT_LEFT = 24;
    public static final int END_HURT_LEFT = 25;
    public static final int DIZZY_RIGHT = 26;
    public static final int END_DIZZY_RIGHT = 33;
    public static final int DIZZY_LEFT = 34;
    public static final int END_DIZZY_LEFT = 41;
    public static final int RAGE_RIGHT = 42;
    public static final int END_RAGE_RIGHT = 44;
    public static final int RAGE_LEFT = 45;
    public static final int END_RAGE_LEFT = 47;
    public static final int EXPLODE = 48;
    public static final int END_EXPLODE = 52;
    public boolean descending;
    Context context;
    
    public BossEnemy(float x, float y, int width, int height, Context context){
        super(x,y,width,height);
        
        sprites = new Bitmap[53];
        this.context = context;
        getSprites();
        
        health = 5; 
        immuneTimer = 0;
        attackTimer = 20;
        
        drop = new DescendingBoss(this);
        alive = new AliveBoss(this);
        hurt = new HurtBoss(this);
        raging = new RagingBoss(this);
        dying = new DyingBoss(this);
        done = new DoneEnemy(this, context);

        state = drop;
        descending = true;
        spriteState = FLY_LEFT;
    }
    
    @Override
    public void render(Canvas c) {
        if (immuneTimer % 2 == 0)
            c.drawBitmap(sprites[spriteState], (int)x, (int)y, null);
    }

    @Override
    public RectF getCollisionBox() {
        return new RectF(x,y,x+sprites[spriteState].getWidth(),y+sprites[spriteState].getHeight());
    }

    @Override
    public void travelLeft() {
        x -= width/6;
        spriteState = spriteState < END_FLY_LEFT ? spriteState+1 : FLY_LEFT;
    }

    @Override
    public void travelRight() {
        x += width/6;
        spriteState = spriteState < END_FLY_RIGHT ? spriteState+1 : FLY_RIGHT;
    }

    @Override
    public void update() {
        state.update();
        attackTimer--;
    }

    @Override
    public void handleCollision(MovableFigure mf) {
        if (immuneTimer > 0) return;
        if (mf instanceof Player) {
            Player p = (Player) mf;
            if (p.isMeleeLeft() || p.isMeleeRight()) hurt();
            else p.hurt();
        } else if (!isRageRight() && !isRageLeft()) {
            hurt();
        }
    }

    public boolean isFacingLeft() { return isFlyingLeft() || isShootLeft() || isHurtLeft() || isDizzyLeft() || isRageLeft(); }

    public boolean isFacingRight() { return isFlyingRight() || isShootRight() || isHurtRight() || isDizzyRight() || isRageRight(); }

    public boolean isFlyingLeft() { return spriteState <= END_FLY_LEFT && spriteState >= FLY_LEFT; }

    public boolean isFlyingRight() { return spriteState <= END_FLY_RIGHT && spriteState >= FLY_RIGHT; }

    public boolean isShootLeft() { return spriteState <= END_SHOOT_LEFT && spriteState >= SHOOT_LEFT; }

    public boolean isShootRight() { return spriteState <= END_SHOOT_RIGHT && spriteState >= SHOOT_RIGHT; }

    public boolean isHurtLeft() { return spriteState <= END_HURT_LEFT && spriteState >= HURT_LEFT; }

    public boolean isHurtRight() { return spriteState <= END_HURT_RIGHT && spriteState >= HURT_RIGHT; }

    public boolean isDizzyLeft() { return spriteState <= END_DIZZY_LEFT && spriteState >= DIZZY_LEFT; }

    public boolean isDizzyRight() { return spriteState <= END_DIZZY_RIGHT && spriteState >= DIZZY_RIGHT; }

    public boolean isRageLeft() { return spriteState <= END_RAGE_LEFT && spriteState >= RAGE_LEFT; }

    public boolean isRageRight() { return spriteState <= END_RAGE_RIGHT && spriteState >= RAGE_RIGHT; }

    public boolean isDying() { return spriteState <= END_EXPLODE && spriteState >= EXPLODE; }

    public void hurt() {
        if (immuneTimer == 0) {
            immuneTimer = 50 ;
            health--;
        }
    }
    
    public void fireProjectile() {
        int streamId = GameActivity.soundsManager.play("fireball");
        spriteState = isFacingRight() ? SHOOT_RIGHT : SHOOT_LEFT;

        float sx = isFacingRight() ? x+width/2 : x-width;
        BeastFireBall bfb = new BeastFireBall(sx, y+height/2, 0, 0, width, height, context, this, streamId);
        
        synchronized (GameActivity.gameData.enemyFigures) {
            GameActivity.gameData.enemyFigures.add(bfb);
        }
    }

    public void getSprites(){
        //Fly Right
        sprites[0] = super.extractImage(context.getResources(), R.drawable.beast1);
        sprites[1] = super.extractImage(context.getResources(), R.drawable.beast2);
        sprites[2] = super.extractImage(context.getResources(), R.drawable.beast3);
        sprites[3] = super.extractImage(context.getResources(), R.drawable.beast4);
        sprites[4] = super.extractImage(context.getResources(), R.drawable.beast5);
        sprites[5] = super.extractImage(context.getResources(), R.drawable.beast6);
        sprites[6] = super.extractImage(context.getResources(), R.drawable.beast7);
        sprites[7] = super.extractImage(context.getResources(), R.drawable.beast8);
        //Fly Left
        sprites[8] = super.flipImage(sprites[0]);
        sprites[9] = super.flipImage(sprites[1]);
        sprites[10] = super.flipImage(sprites[2]);
        sprites[11] = super.flipImage(sprites[3]);
        sprites[12] = super.flipImage(sprites[4]);
        sprites[13] = super.flipImage(sprites[5]);
        sprites[14] = super.flipImage(sprites[6]);
        sprites[15] = super.flipImage(sprites[7]);
        //Shoot Right
        sprites[16] = super.extractImage(context.getResources(), R.drawable.beast_shoot1);
        sprites[17] = super.extractImage(context.getResources(), R.drawable.beast_shoot2);
        sprites[18] = super.extractImage(context.getResources(), R.drawable.beast_shoot3);
        //Shoot Left
        sprites[19] = super.flipImage(sprites[16]);
        sprites[20] = super.flipImage(sprites[17]);
        sprites[21] = super.flipImage(sprites[18]);
        //Hurt Right
        sprites[22] = super.extractImage(context.getResources(), R.drawable.beast_hurt1);
        sprites[23] = super.extractImage(context.getResources(), R.drawable.beast_hurt2);
        //Hurt Left
        sprites[24] = super.flipImage(sprites[22]);
        sprites[25] = super.flipImage(sprites[23]);
        //Dizzy Right
        sprites[26] = super.extractImage(context.getResources(), R.drawable.beast_dizzy1);
        sprites[27] = super.extractImage(context.getResources(), R.drawable.beast_dizzy2);
        sprites[28] = super.extractImage(context.getResources(), R.drawable.beast_dizzy3);
        sprites[29] = super.extractImage(context.getResources(), R.drawable.beast_dizzy4);
        sprites[30] = super.extractImage(context.getResources(), R.drawable.beast_dizzy5);
        sprites[31] = super.extractImage(context.getResources(), R.drawable.beast_dizzy6);
        sprites[32] = super.extractImage(context.getResources(), R.drawable.beast_dizzy7);
        sprites[33] = super.extractImage(context.getResources(), R.drawable.beast_dizzy8);
        //Dizzy Left
        sprites[34] = super.flipImage(sprites[26]);
        sprites[35] = super.flipImage(sprites[27]);
        sprites[36] = super.flipImage(sprites[28]);
        sprites[37] = super.flipImage(sprites[29]);
        sprites[38] = super.flipImage(sprites[30]);
        sprites[39] = super.flipImage(sprites[31]);
        sprites[40] = super.flipImage(sprites[32]);
        sprites[41] = super.flipImage(sprites[33]);
        //Rage Right
        sprites[42] = super.extractImage(context.getResources(), R.drawable.beast_rage1);
        sprites[43] = super.extractImage(context.getResources(), R.drawable.beast_rage2);
        sprites[44] = super.extractImage(context.getResources(), R.drawable.beast_rage3);
        //Rage Left
        sprites[45] = super.flipImage(sprites[42]);
        sprites[46] = super.flipImage(sprites[43]);
        sprites[47] = super.flipImage(sprites[44]);
        //Explode
        sprites[48] = super.extractImage(context.getResources(), R.drawable.beast_explode1);
        sprites[49] = super.extractImage(context.getResources(), R.drawable.beast_explode2);
        sprites[50] = super.extractImage(context.getResources(), R.drawable.beast_explode3);
        sprites[51] = super.extractImage(context.getResources(), R.drawable.beast_explode4);
        sprites[52] = super.extractImage(context.getResources(), R.drawable.beast_explode5);

        for (int i = RAGE_RIGHT; i <= END_RAGE_LEFT; i++){
            sprites[i] = Bitmap.createScaledBitmap(sprites[i], width*3, height*2, false);
        }
    }
}