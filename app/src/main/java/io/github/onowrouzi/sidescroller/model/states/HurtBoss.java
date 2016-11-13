package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.BossEnemies.BossEnemy;

public class HurtBoss implements FigureState {

    public BossEnemy boss;
    
    public HurtBoss(BossEnemy boss) { this.boss = boss; }

    @Override
    public void update() {
        if (boss.health == 0){
            boss.spriteState = BossEnemy.EXPLODE;
            boss.state = boss.dying;
        } else if (boss.immuneTimer > 0){
            boss.immuneTimer--;
            if (boss.isFacingLeft()){
                boss.spriteState = boss.spriteState < BossEnemy.END_DIZZY_LEFT ? boss.spriteState+1 : BossEnemy.DIZZY_LEFT;
            } else {
                boss.spriteState = boss.spriteState < BossEnemy.END_DIZZY_RIGHT ? boss.spriteState+1 : BossEnemy.DIZZY_RIGHT;
            }
        } else {
            boss.spriteState = boss.isFacingLeft() ? BossEnemy.RAGE_LEFT : BossEnemy.RAGE_RIGHT;
            boss.state = boss.raging;
        }
    }
    
}
