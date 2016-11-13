package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemies.BossEnemy;

public class DescendingBoss implements FigureState {
   
    public BossEnemy boss;
    
    public DescendingBoss(BossEnemy boss){ this.boss = boss; }
    
    @Override
    public void update(){
        if (boss.immuneTimer > 0){
            boss.state = boss.hurt;
            boss.spriteState = boss.isFacingLeft() ? BossEnemy.DIZZY_LEFT : BossEnemy.DIZZY_RIGHT;
        } else if (boss.y < GameActivity.screenHeight*.7) {
            boss.y += boss.height/8;
            boss.x = boss.isFacingLeft() ? boss.x+boss.width/6 : boss.x-boss.width/6;
            if (boss.isFacingLeft()) boss.travelLeft();
            else boss.travelRight();
        } else if (boss.attackTimer < 0) {
            boss.descending = false;
            boss.fireProjectile();
            boss.attackTimer = 10;
        } else if ((boss.isShootLeft() && boss.spriteState < BossEnemy.END_SHOOT_LEFT)
                || (boss.isShootRight() && boss.spriteState < BossEnemy.END_SHOOT_RIGHT)) {
            boss.spriteState++;
        } else {
            boss.spriteState = boss.isFacingLeft() ? BossEnemy.FLY_LEFT : BossEnemy.FLY_RIGHT;
            boss.state = boss.alive;
        }
    }
}
