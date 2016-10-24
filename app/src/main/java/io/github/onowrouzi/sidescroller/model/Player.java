package io.github.onowrouzi.sidescroller.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.ui.BulletCount;
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

    public static final int SHOOT_RIGHT = 3;
    public static final int SHOOT_LEFT = 7;
    private int groundLevel;
    public boolean ascend;
    public boolean descend;
    public boolean jumpLeft;
    public boolean jumpRight;
    public int bulletCount;
    public int bulletRegenCounter;
    private final ArrayList<Observer> observers = new ArrayList<>();
    
    public Player(float x, float y, int width, int height, Context context){
        super(x,y,width,height);
        health = 5;
        immuneTimer = 0;
        bulletCount = 10;
        bulletRegenCounter = 100;
        groundLevel = (int)y + height;
        
        sprites = new Bitmap[32];

        getSprites(context);
    }
    
    @Override
    public void render(Canvas c) {
        if (immuneTimer % 2 == 0)
            c.drawBitmap(sprites[spriteState], (int)x, (int)y, null);
    }

    @Override
    public void update() {
        idle();
        handleImmuneTimer();
        handleBulletCount();
        handleJump();
        handleMelee();
        notifyObservers();
    }
    
    @Override
    public void travelLeft() {

        if (spriteState >= RUN_LEFT && spriteState < END_RUN_LEFT){
            spriteState++;
        } else {
            spriteState = RUN_LEFT;
        }
        
        if (x > 0) x -= 20;
    }
    
    @Override
    public void travelRight() {

        if (spriteState >= RUN_RIGHT && spriteState < END_RUN_RIGHT){
            spriteState++;
        } else {
            spriteState = RUN_RIGHT;
        }
        
        if (x + width < width*6.5){
            x += 20;
        } else if (GameData.stage1) {
            GameData.background.moveBackground();
        }
    }

    public void idle(){
        if (spriteState <= END_STAND_LEFT) {
            if (spriteState != END_STAND_LEFT && spriteState != END_STAND_RIGHT) {
                spriteState++;
            } else if (spriteState == END_STAND_RIGHT) {
                spriteState = STAND_RIGHT;
            } else if (spriteState == END_STAND_LEFT) {
                spriteState = STAND_LEFT;
            }
        }
    }
    
    public void jump(){
        if (!ascend && !descend) {
            ascend = true;

            if (isStandingLeft()) {
                spriteState = JUMP_LEFT;
            }
            if (isRunningLeft()) {
                spriteState = JUMP_LEFT;
                jumpLeft = true;
            }

            if (isStandingRight()) {
                spriteState = JUMP_RIGHT;
            }

            if (isRunningRight()) {
                spriteState = JUMP_RIGHT;
                jumpRight = true;
            }
        }
    }

    public void melee(){
        if (isStandingLeft() || isRunningLeft()){
            spriteState = MELEE_LEFT;
            x -= width*.5;
        } else if (isStandingRight() || isRunningRight()){
            spriteState = MELEE_RIGHT;
        }
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
    
    public void hurt(){
        if (immuneTimer == 0) {
            health--;
            immuneTimer = 20;
        }
        if (health == 0){
            GameThread.gameOver = true;
        }
    }
    
    public float getXofMissileShoot() {
        if (spriteState == SHOOT_LEFT) {
            return super.x;
        }
        else return super.x+super.width;
    }

    public float getYofMissileShoot() {
        return super.y + height/2;
    }
    
    public void resetPlayer(){
        health = 5;
        immuneTimer = 0;
        bulletCount = 10;
        bulletRegenCounter = 100;
        x = y = 350;
        spriteState = STAND_RIGHT;
        ascend = descend = false;
    }
    
    @Override
    public RectF getCollisionBox() {
        if (spriteState == MELEE_LEFT) 
            return new RectF(x,y+height/4,width*.7f,height/4*3);
        else if (spriteState == MELEE_RIGHT) 
            return new RectF(x+width/3,y+height/4,width*.7f,height/4*3);
        return new RectF(x+width*.3f,y+height/4,width*.4f,height/4*3);
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
    
    public void attach(Observer observer){
        observers.add(observer);
    }
    
    public void notifyObservers(){
        for (Observer o : observers){
            if (o instanceof BulletCount){
                o.updateObserver(bulletCount, bulletRegenCounter);
            } else if (o instanceof HealthBars){
                o.updateObserver(health, immuneTimer);
            }
        }
    }

    public void handleJump() {
        if (ascend) {
            if (y > groundLevel - height*2) {
                y -= 20;
            } else {
                ascend = false;
                descend = true;
                spriteState = spriteState == JUMP_LEFT ? FALL_LEFT : FALL_RIGHT;
            }
            if (jumpLeft) x -= 5;
            if (jumpRight) {
                if (x+width*2 < 800) {
                    x += 5;
                } else if (GameData.stage1) {
                    GameData.background.moveBackground();
                }
            }
        } else if (descend) {
            if (y+height >= groundLevel) {
                descend = false;
                jumpLeft = false;
                jumpRight = false;
                spriteState = spriteState == FALL_LEFT ? STAND_LEFT : STAND_RIGHT;
            } else {
                y += 20;
            }
            if (jumpLeft && x>0) x -= 5;
            if (jumpRight){
                if (x+width*2 < 800) {
                    x += 5;
                } else if (GameData.stage1) {
                    GameData.background.moveBackground();
                }
            }
        }
    }

    public void handleBulletCount() {
        if (bulletCount == 0) {
            bulletRegenCounter--;
        }

        if (bulletRegenCounter == 0) {
            bulletCount = 10;
            bulletRegenCounter = 100;
        }
    }

    public void handleMelee() {
         if (isMeleeLeft()){
            if (spriteState == END_MELEE_LEFT) {
                spriteState = STAND_LEFT;
                x += width*.5;
            } else {
                spriteState++;
            }
         } else if (isMeleeRight()){
            if (spriteState == END_MELEE_RIGHT){
                spriteState = STAND_RIGHT;
            } else {
                spriteState++;
            }
         }
    }

    public void handleImmuneTimer() {
        if (immuneTimer > 0) {
            immuneTimer--;
        }
    }

    public void getSprites(Context context){
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

        for (int i = 0; i < MELEE_RIGHT; i++){
            sprites[i] = Bitmap.createScaledBitmap(sprites[i], width, height, false);
        }

        for (int i = MELEE_RIGHT; i <= END_MELEE_LEFT; i++){
            sprites[i] = Bitmap.createScaledBitmap(sprites[i], (int)(width*1.5), height, false);
        }
    }
    
}
