package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemy;

public class AliveGroundEnemy implements FigureState {

    public Enemy enemy;
    
    public AliveGroundEnemy(GroundEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (enemy.spriteState <= GroundEnemy.WALK_LEFT) {
            enemy.travelLeft();
        } else {
            enemy.travelRight();
        }
    }
    
}
