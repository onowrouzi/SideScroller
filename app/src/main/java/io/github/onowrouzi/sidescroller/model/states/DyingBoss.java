package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.BossEnemies.BossEnemy;

public class DyingBoss implements FigureState {
    
    public BossEnemy boss;
    
    public DyingBoss(BossEnemy boss) {
        this.boss = boss;
    }

    @Override
    public void update() {
        if (boss.spriteState < BossEnemy.END_EXPLODE)
            boss.spriteState++;
        else
            boss.state = boss.done;
    }
    
}
