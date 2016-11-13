package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemies.BossEnemy;

public class AliveBoss implements FigureState {
    
    public BossEnemy boss;

    public AliveBoss(BossEnemy boss) { this.boss = boss; }

    @Override
    public void update() {
        if (boss.immuneTimer > 0){
            boss.state = boss.hurt;
            boss.spriteState = boss.isFacingLeft() ? BossEnemy.DIZZY_LEFT : BossEnemy.DIZZY_RIGHT;
        } else if (boss.y > GameActivity.screenHeight*.1){
            boss.y -= boss.height/8;
            boss.x = boss.isFacingLeft() ? boss.x+boss.width/6 : boss.x-boss.width/6;
            if (boss.isFacingLeft()) boss.travelLeft();
            else boss.travelRight();
        } else if ((boss.x + boss.width*1.1 > GameActivity.screenWidth && boss.isFacingRight())
                || (boss.x - boss.width*0.1 < 0 && boss.isFacingLeft())){
            boss.spriteState = boss.isFacingLeft() ? BossEnemy.FLY_RIGHT : BossEnemy.FLY_LEFT;
            boss.descending = true;
            boss.state = boss.drop;
        } else {
            if (boss.isFacingLeft()) boss.travelLeft();
            else boss.travelRight();
        }
    }
    
}
