package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemy;

public class DyingGroundEnemy implements FigureState {

    public Enemy enemy;
    
    public DyingGroundEnemy(GroundEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (enemy.spriteState < GroundEnemy.DEAD_LEFT) {
            enemy.spriteState = GroundEnemy.DEAD_LEFT;
        } else {
            enemy.spriteState = GroundEnemy.DEAD_RIGHT;
        }
        //Sounds.play("sounds/dyingGroundEnemySound.wav");
        enemy.state = enemy.done;
    }
    
}
