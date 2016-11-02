package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemy;

public class DyingGroundEnemy implements FigureState {

    public GroundEnemy enemy;
    
    public DyingGroundEnemy(GroundEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (enemy.isFacingLeft()) {
            enemy.spriteState = GroundEnemy.DEAD_LEFT;
        } else if (enemy.isFacingRight()){
            enemy.spriteState = GroundEnemy.DEAD_RIGHT;
        }

        if (enemy.isDyingLeft()) {
            if (enemy.spriteState == GroundEnemy.END_DEAD_LEFT)
                enemy.state = enemy.done;
            else enemy.spriteState++;
        } else if (enemy.isDyingRight()){
            if (enemy.spriteState == GroundEnemy.END_DEAD_RIGHT)
                enemy.state = enemy.done;
            else enemy.spriteState++;
        }
        //Sounds.play("sounds/dyingGroundEnemySound.wav");

    }
    
}
