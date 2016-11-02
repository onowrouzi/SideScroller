package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemy;

public class AliveGroundEnemy implements FigureState {

    public GroundEnemy enemy;
    
    public AliveGroundEnemy(GroundEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (enemy.isFacingLeft()) {
            enemy.travelLeft();
        } else {
            enemy.travelRight();
        }
    }
    
}
