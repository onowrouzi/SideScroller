package io.github.onowrouzi.sidescroller.model.helpers;

import io.github.onowrouzi.sidescroller.model.GameData;
import io.github.onowrouzi.sidescroller.model.Player;

public class PlayerActionHandler {

    private Player p;

    public PlayerActionHandler(Player p){
        this.p = p;
    }

    public void handle(){
        idle();
        handleImmuneTimer();
        handleBulletCount();
        handleJump();
        handleMelee();
        handleThrow();
    }

    public void handleJump() {
        if (p.ascend) {
            if (p.y > p.groundLevel - p.height*2) {
                p.y -= 20;
            } else {
                p.ascend = false;
                p.descend = true;
                if (p.spriteState == p.JUMP_LEFT)
                    p.spriteState = p.FALL_LEFT;
                if (p.spriteState == p.JUMP_RIGHT)
                    p.spriteState = p.FALL_RIGHT;
            }
            if (p.jumpLeft) p.x -= 5;
            if (p.jumpRight) {
                if (p.x+p.width*2 < 800) {
                    p.x += 5;
                } else if (GameData.stage1) {
                    GameData.background.moveBackground();
                }
            }
        } else if (p.descend) {
            if (p.y+p.height >= p.groundLevel) {
                p.descend = p.jumpLeft = p.jumpRight = false;
                if (p.spriteState == p.FALL_LEFT)
                    p.spriteState = p.STAND_LEFT;
                if (p.spriteState == p.FALL_RIGHT)
                    p.spriteState = p.STAND_RIGHT;
            } else {
                p.y += 20;
            }
            if (p.jumpLeft && p.x>0) p.x -= 5;
            if (p.jumpRight){
                if (p.x+p.width*2 < 800) {
                    p.x += 5;
                } else if (GameData.stage1) {
                    GameData.background.moveBackground();
                }
            }
        }
    }

    public void handleBulletCount() {
        if (p.bulletCount == 0) {
            p.bulletRegenCounter--;
        }

        if (p.bulletRegenCounter == 0) {
            p.bulletCount = 10;
            p.bulletRegenCounter = 100;
        }
    }

    public void handleMelee() {
        if (p.isMeleeLeft()){
            if (p.spriteState == p.END_MELEE_LEFT) {
                p.spriteState = p.STAND_LEFT;
                p.x += p.width*.5;
            } else {
                p.spriteState++;
            }
        } else if (p.isMeleeRight()){
            if (p.spriteState == p.END_MELEE_RIGHT){
                p.spriteState = p.STAND_RIGHT;
            } else {
                p.spriteState++;
            }
        }
    }

    public void handleThrow(){
        if (p.isThrowLeft()){
            p.spriteState = p.spriteState == p.END_THROW_LEFT ? p.spriteState = p.STAND_LEFT : p.spriteState + 1;
        } else if (p.isThrowRight()){
            p.spriteState = p.spriteState == p.END_THROW_RIGHT ? p.spriteState = p.STAND_RIGHT : p.spriteState + 1;
        }
    }

    public void handleImmuneTimer() {
        if (p.immuneTimer > 0) p.immuneTimer--;
    }

    public void idle(){
        if (p.spriteState <= p.END_STAND_LEFT) {
            if (p.spriteState != p.END_STAND_LEFT && p.spriteState != p.END_STAND_RIGHT) {
                p.spriteState++;
            } else if (p.spriteState == p.END_STAND_RIGHT) {
                p.spriteState = p.STAND_RIGHT;
            } else if (p.spriteState == p.END_STAND_LEFT) {
                p.spriteState = p.STAND_LEFT;
            }
        }
    }
}
