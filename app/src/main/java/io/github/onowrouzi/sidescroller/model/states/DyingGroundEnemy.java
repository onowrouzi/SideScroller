package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.GroundEnemy;

public class DyingGroundEnemy implements FigureState {

    public GroundEnemy enemy;
    
    public DyingGroundEnemy(GroundEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (!enemy.isDyingLeft() && !enemy.isDyingRight())
            enemy.setDead();

        if (enemy.isDyingLeft() || enemy.isDyingRight())
            enemy.checkDone();
    }
    
}
