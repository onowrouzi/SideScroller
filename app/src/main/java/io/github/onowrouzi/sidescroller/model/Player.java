package io.github.onowrouzi.sidescroller.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Vibrator;

import java.util.ArrayList;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.helpers.PlayerActionHandler;
import io.github.onowrouzi.sidescroller.model.projectiles.FireBall;
import io.github.onowrouzi.sidescroller.model.projectiles.Shuriken;
import io.github.onowrouzi.sidescroller.model.ui.InvincibilityCount;
import io.github.onowrouzi.sidescroller.model.ui.ShieldCount;
import io.github.onowrouzi.sidescroller.model.ui.ShurikenCount;
import io.github.onowrouzi.sidescroller.model.ui.FireBallCount;
import io.github.onowrouzi.sidescroller.model.ui.HealthBars;
import io.github.onowrouzi.sidescroller.model.ui.Observer;

public class Player extends MovableFigure implements Travel {
    
    public static final int STAND_RIGHT = 0;
    public static final int END_STAND_RIGHT = 7;
    public static final int STAND_LEFT = 8;
    public static final int END_STAND_LEFT = 15;
    public static final int RUN_RIGHT = 16;
    public static final int END_RUN_RIGHT = 18;
    public static final int RUN_LEFT = 19;
    public static final int END_RUN_LEFT = 21;
    public static final int JUMP_RIGHT = 22;
    public static final int FALL_RIGHT = 23;
    public static final int JUMP_LEFT = 24;
    public static final int FALL_LEFT = 25;
    public static final int MELEE_RIGHT = 26;
    public static final int END_MELEE_RIGHT = 28;
    public static final int MELEE_LEFT = 29;
    public static final int END_MELEE_LEFT = 31;
    public static final int THROW_RIGHT = 32;
    public static final int END_THROW_RIGHT = 34;
    public static final int THROW_LEFT = 35;
    public static final int END_THROW_LEFT = 37;
    public static final int DUCK_RIGHT = 38;
    public static final int DUCK_LEFT = 39;

    public boolean ascend;
    public boolean descend;
    public boolean jumpLeft;
    public boolean jumpRight;
    public int shurikenCount;
    public int fireBallCount;
    public int invincibilityTimer;
    public int shieldTimer;
    public int shielding;
    public int bubbleState;
    public Bitmap[] bubble;
    private final ArrayList<Observer> observers = new ArrayList<>();
    Vibrator v;
    Context context;
    PlayerActionHandler pah;
    
    public Player(float x, float y, int width, int height, Context context){
        super(x,y,width,height);
        health = 6;
        immuneTimer = 0;
        invincibilityTimer = 0;
        shieldTimer = 0;
        shurikenCount = 10;
        fireBallCount = 5;
        
        sprites = new Bitmap[40];
        bubble = new Bitmap[5];
        paint = new Paint();

        this.context = context;
        pah = new PlayerActionHandler(this);
        bubbleState = 0;
        getSprites();

        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    
    @Override
    public void render(Canvas c) {
        if (invincibilityTimer > 0) paint.setAlpha(50);
        else paint.setAlpha(255);

        if (immuneTimer % 2 == 0)
            c.drawBitmap(sprites[spriteState], (int)x, (int)y, paint);
        if (shielding > 0) {
            int bx = isMeleeLeft() ? (int)(x+width*.4) : (int)(x-width*.1);
            c.drawBitmap(bubble[bubbleState], bx, (int) (y - height * .1), null);
        }
    }

    @Override
    public void update() {
        pah.handle();
        notifyObservers();
        if (shielding > 0) bubbleState = bubbleState < 4 ? bubbleState + 1 : 0;
    }
    
    @Override
    public void travelLeft() {
        if (isDuck()) unduck();

        if (!isJumpLeft())
            spriteState = (spriteState >= RUN_LEFT && spriteState < END_RUN_LEFT) ? spriteState+1 : RUN_LEFT;

        if (x > 0) x -= width/8;
    }
    
    @Override
    public void travelRight() {
        if (isDuck()) unduck();

        if (!isJumpRight())
            spriteState = (spriteState >= RUN_RIGHT && spriteState < END_RUN_RIGHT) ? spriteState+1 : RUN_RIGHT;
        
        if (x + width < width * 5) x += width/8;
        else GameData.background.moveBackground();
    }
    
    public void jump(){
        if (isDuck()) unduck();

        if (!ascend && !descend) {
            GameActivity.soundsManager.play("jump");
            ascend = true;

            if (isStandingLeft()) spriteState = JUMP_LEFT;

            if (isRunningLeft()) {
                spriteState = JUMP_LEFT;
                jumpLeft = true;
            }

            if (isStandingRight()) spriteState = JUMP_RIGHT;

            if (isRunningRight()) {
                spriteState = JUMP_RIGHT;
                jumpRight = true;
            }
        }
    }

    public void bounceBack(){
        descend = jumpLeft = jumpRight = false;
        if (spriteState >= STAND_LEFT) {
            jumpRight = true;
        } else {
            jumpLeft = true;
        }
        jump();
    }

    public void bounceOff(){
        descend = jumpLeft = jumpRight = false;
        if (spriteState < STAND_LEFT) {
            jumpRight = true;
        } else {
            jumpLeft = true;
        }
        jump();
    }

    public void melee(){
        if (isDuck()) unduck();

        if (!isJumpLeft() && !isJumpRight() && !isMeleeLeft() && !isMeleeRight()) {
            GameActivity.soundsManager.play("slash");
            if (isStandingLeft() || isRunningLeft()) {
                spriteState = MELEE_LEFT;
                x -= width * .5;
            } else if (isStandingRight() || isRunningRight()) {
                spriteState = MELEE_RIGHT;
            }
        }
    }

    public void duck(){
        spriteState = isFacingLeft() ? DUCK_LEFT : DUCK_RIGHT;
        y += height/3;
    }

    public void unduck(){
        spriteState = isDuckLeft() ? STAND_LEFT : STAND_RIGHT;
        y -= height/3;
    }

    public void fireProjectile(float ex, float ey) {
        if (isDuck()) unduck();

        int streamId = GameActivity.soundsManager.play("shoot");
        spriteState = isFacingRight() ? THROW_RIGHT : THROW_LEFT;

        float sx = isFacingRight() ? x+width : x;
        Shuriken s = new Shuriken (sx, y+height/2, ex, ey, width/4, height/4, context, this, streamId);

        shurikenCount--;

        synchronized (GameActivity.gameData.friendFigures) {
            GameActivity.gameData.friendFigures.add(s);
        }
    }

    public void throwFireBall(){
        if (isDuck()) unduck();

        int streamId = GameActivity.soundsManager.play("fireball");
        spriteState = isFacingRight() ? THROW_RIGHT : THROW_LEFT;

        float sx = isFacingRight() ? x+width/2 : x-width;
        FireBall fb = new FireBall(sx, y+height/4, 0, 0, width*2, height*3/4, context, this, streamId);

        fireBallCount--;

        synchronized (GameActivity.gameData.friendFigures){
            GameActivity.gameData.friendFigures.add(fb);
        }
    }

    public void hurt(){
        if (immuneTimer == 0 && invincibilityTimer == 0 && shieldTimer == 0) {
            if (shielding > 0) {
                shielding--;
                shieldTimer = 20;
            } else {
                v.vibrate(50);
                GameActivity.soundsManager.play("hurt");
                health--;
                immuneTimer = 20;
            }
        }
        if (health == 0) GameThread.gameOver = true;
    }

    @Override
    public void handleCollision(MovableFigure mf){
        //Let others handle collision
    }

    public void resetPlayer(){
        health = 6;
        immuneTimer = 0;
        shurikenCount = 10;
        fireBallCount = 5;
        invincibilityTimer = 0;
        shielding = 0;
        x = GameActivity.screenWidth/2;
        y = GameActivity.screenHeight*3/4;
        spriteState = STAND_RIGHT;
        ascend = descend = false;
    }

    public boolean isStandingLeft(){
        return spriteState >= STAND_LEFT && spriteState <= END_STAND_LEFT;
    }

    public boolean isStandingRight(){
        return spriteState >= STAND_RIGHT && spriteState <= END_STAND_RIGHT;
    }

    public boolean isRunningLeft(){
        return spriteState >= RUN_LEFT && spriteState <= END_RUN_LEFT;
    }

    public boolean isRunningRight(){
        return spriteState >= RUN_RIGHT && spriteState <= END_RUN_RIGHT;
    }

    public boolean isMeleeLeft(){
        return spriteState >= MELEE_LEFT && spriteState <= END_MELEE_LEFT;
    }

    public boolean isMeleeRight(){
        return spriteState >= MELEE_RIGHT && spriteState <= END_MELEE_RIGHT;
    }

    public boolean isThrowLeft(){
        return spriteState >= THROW_LEFT && spriteState <= END_THROW_LEFT;
    }

    public boolean isThrowRight(){
        return spriteState >= THROW_RIGHT && spriteState <= END_THROW_RIGHT;
    }

    public boolean isJumpLeft(){
        return spriteState == JUMP_LEFT || spriteState == FALL_LEFT;
    }

    public boolean isJumpRight(){
        return spriteState == JUMP_RIGHT || spriteState == FALL_RIGHT;
    }

    public boolean isDuckLeft() { return spriteState == DUCK_LEFT; }

    public boolean isDuckRight() { return spriteState == DUCK_RIGHT; }

    public boolean isDuck() { return isDuckLeft() || isDuckRight(); }

    public boolean isFacingLeft(){
        return (isStandingLeft() || isRunningLeft() || isMeleeLeft() || isThrowLeft() || isJumpLeft() || isDuckLeft());
    }

    public boolean isFacingRight(){
        return (isStandingRight() || isRunningRight() || isMeleeRight() || isThrowRight() || isJumpRight() || isDuckRight());
    }

    @Override
    public RectF getCollisionBox() {
        if (isMeleeRight()) return new RectF(x+width*.3f,y,x+width*1.3f,y+height);
        if (isFacingRight()) return new RectF(x+width*.3f,y,x+width*.9f,y+height);
        return new RectF(x,y,x+width*.7f,y+height);
    }
    
    public void attach(Observer observer){ observers.add(observer); }
    
    public void notifyObservers(){
        for (Observer o : observers){
            if (o instanceof ShurikenCount){
                o.updateObserver(shurikenCount, 0);
            } else if (o instanceof HealthBars){
                o.updateObserver(health, immuneTimer);
            } else if (o instanceof FireBallCount) {
                o.updateObserver(fireBallCount, 0);
            } else if (o instanceof ShieldCount){
                o.updateObserver(shielding, 0);
            } else if (o instanceof InvincibilityCount){
                o.updateObserver(invincibilityTimer, 0);
            }
        }
    }

    public void getSprites(){
        //Idle Right
        sprites[0] = super.extractImage(context.getResources(), R.drawable.player1);
        sprites[1] = super.extractImage(context.getResources(), R.drawable.player2);
        sprites[2] = super.extractImage(context.getResources(), R.drawable.player3);
        sprites[3] = super.extractImage(context.getResources(), R.drawable.player4);
        sprites[4] = super.extractImage(context.getResources(), R.drawable.player5);
        sprites[5] = super.extractImage(context.getResources(), R.drawable.player6);
        sprites[6] = super.extractImage(context.getResources(), R.drawable.player7);
        sprites[7] = super.extractImage(context.getResources(), R.drawable.player8);
        //Idle Left
        sprites[8] = super.flipImage(sprites[0]);
        sprites[9] = super.flipImage(sprites[1]);
        sprites[10] = super.flipImage(sprites[2]);
        sprites[11] = super.flipImage(sprites[3]);
        sprites[12] = super.flipImage(sprites[4]);
        sprites[13] = super.flipImage(sprites[5]);
        sprites[14] = super.flipImage(sprites[6]);
        sprites[15] = super.flipImage(sprites[7]);
        //Run Right
        sprites[16] = super.extractImage(context.getResources(), R.drawable.player9);
        sprites[17] = super.extractImage(context.getResources(), R.drawable.player10);
        sprites[18] = super.extractImage(context.getResources(), R.drawable.player11);
        //Run Left
        sprites[19] = super.flipImage(sprites[16]);
        sprites[20] = super.flipImage(sprites[17]);
        sprites[21] = super.flipImage(sprites[18]);
        //Jump Right
        sprites[22] = super.extractImage(context.getResources(), R.drawable.player12);
        sprites[23] = super.extractImage(context.getResources(), R.drawable.player13);
        //Jump Left
        sprites[24] = super.flipImage(sprites[22]);
        sprites[25] = super.flipImage(sprites[23]);
        //Melee Right
        sprites[26] = super.extractImage(context.getResources(), R.drawable.player14);
        sprites[27] = super.extractImage(context.getResources(), R.drawable.player15);
        sprites[28] = super.extractImage(context.getResources(), R.drawable.player16);
        //Melee Left
        sprites[29] = super.flipImage(sprites[26]);
        sprites[30] = super.flipImage(sprites[27]);
        sprites[31] = super.flipImage(sprites[28]);
        //Melee Right
        sprites[32] = super.extractImage(context.getResources(), R.drawable.player17);
        sprites[33] = super.extractImage(context.getResources(), R.drawable.player18);
        sprites[34] = super.extractImage(context.getResources(), R.drawable.player19);
        //Melee Left
        sprites[35] = super.flipImage(sprites[32]);
        sprites[36] = super.flipImage(sprites[33]);
        sprites[37] = super.flipImage(sprites[34]);
        //Duck Right
        sprites[38] = super.extractImage(context.getResources(), R.drawable.player20);
        sprites[38] = Bitmap.createScaledBitmap(sprites[38], width, height*2/3, false);
        //Duck Left
        sprites[39] = super.flipImage(sprites[38]);
        sprites[39] = Bitmap.createScaledBitmap(sprites[39], width, height*2/3, false);

        for (int i = MELEE_RIGHT; i <= END_MELEE_LEFT; i++){
            sprites[i] = Bitmap.createScaledBitmap(sprites[i], (int)(width*1.4), height, false);
        }

        bubble[0] = super.extractImage(context.getResources(), R.drawable.bubble1);
        bubble[1] = super.extractImage(context.getResources(), R.drawable.bubble2);
        bubble[2] = super.extractImage(context.getResources(), R.drawable.bubble3);
        bubble[3] = super.extractImage(context.getResources(), R.drawable.bubble4);
        bubble[4] = super.extractImage(context.getResources(), R.drawable.bubble5);

        for (int i = 0; i < 5; i++){
            bubble[i] = Bitmap.createScaledBitmap(bubble[i], width*5/4, height*5/4, false);
        }
    }
}