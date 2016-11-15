package io.github.onowrouzi.sidescroller.model.helpers;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.GameData;
import io.github.onowrouzi.sidescroller.model.Player;

public class PlayerActionHandler {

    private Player p;

    public PlayerActionHandler(Player p){ this.p = p; }

    public void handle(){
        idle();
        handleTimers();
        handleJump();
        handleMelee();
        handleThrow();
    }

    public void handleJump() {
        if (p.ascend) {
            if (p.y > GameActivity.groundLevel - p.height*2.5) {
                p.y -= p.height/4;
            } else {
                p.ascend = false;
                p.descend = true;
                if (p.spriteState == Player.JUMP_LEFT)
                    p.spriteState = Player.FALL_LEFT;
                if (p.spriteState == Player.JUMP_RIGHT)
                    p.spriteState = Player.FALL_RIGHT;
            }
            if (p.jumpLeft) p.x -= p.width/8;
            if (p.jumpRight) {
                if (p.x + p.width < p.width * 5){
                    p.x += p.width/8;
                    GameData.background.moveBackground();
                }
            }
        } else if (p.descend) {
            if (p.y+p.height >= GameActivity.groundLevel) {
                p.descend = p.jumpLeft = p.jumpRight = false;
                if (p.spriteState == Player.FALL_LEFT)
                    p.spriteState = Player.STAND_LEFT;
                if (p.spriteState == Player.FALL_RIGHT)
                    p.spriteState = Player.STAND_RIGHT;
            } else {
                p.y += p.height/4;
            }
            if (p.jumpLeft && p.x>0) p.x -= p.width/8;
            if (p.jumpRight){
                if (p.x+p.width*2 < 800)
                    p.x += p.width/8;
                else
                    GameData.background.moveBackground();
            }
        }
    }

    public void handleMelee() {
        if (p.isMeleeLeft()){
            if (p.spriteState == Player.END_MELEE_LEFT) {
                p.spriteState = Player.STAND_LEFT;
                p.x += p.width*.5;
            } else {
                p.spriteState++;
            }
        } else if (p.isMeleeRight()){
            if (p.spriteState == Player.END_MELEE_RIGHT){
                p.spriteState = Player.STAND_RIGHT;
            } else {
                p.spriteState++;
            }
        }
    }

    public void handleThrow(){
        if (p.isThrowLeft()){
            p.spriteState = p.spriteState == Player.END_THROW_LEFT ? p.spriteState = Player.STAND_LEFT : p.spriteState + 1;
        } else if (p.isThrowRight()){
            p.spriteState = p.spriteState == Player.END_THROW_RIGHT ? p.spriteState = Player.STAND_RIGHT : p.spriteState + 1;
        }
    }

    public void handleTimers() {
        if (p.immuneTimer > 0) p.immuneTimer--;
        if (p.invincibilityTimer > 0) p.invincibilityTimer--;
        if (p.shieldTimer > 0) p.shieldTimer--;
    }

    public void idle(){
        if (p.spriteState <= Player.END_STAND_LEFT) {
            if (p.spriteState != Player.END_STAND_LEFT && p.spriteState != Player.END_STAND_RIGHT) {
                p.spriteState++;
            } else if (p.spriteState == Player.END_STAND_RIGHT) {
                p.spriteState = Player.STAND_RIGHT;
            } else {
                p.spriteState = Player.STAND_LEFT;
            }
        }
    }
}