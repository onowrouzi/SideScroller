package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.GroundEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.Walker;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.Worm;

public class DyingGroundEnemy implements FigureState {

    public GroundEnemy enemy;
    
    public DyingGroundEnemy(GroundEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (enemy.isFacingLeft()) {
            enemy.spriteState = enemy instanceof Walker ? Walker.DEAD_LEFT: Worm.DEAD_LEFT;
            GameActivity.soundsManager.play("explode");
        } else if (enemy.isFacingRight()){
            enemy.spriteState = enemy instanceof Walker ? Walker.DEAD_RIGHT: Worm.DEAD_RIGHT;
            GameActivity.soundsManager.play("explode");
        }

        if (enemy.isDyingLeft()) {
            if (enemy instanceof Walker && enemy.spriteState == Walker.END_DEAD_LEFT
                    || enemy instanceof Worm && enemy.spriteState == Worm.END_DEAD_LEFT)
                enemy.state = enemy.done;
            else enemy.spriteState++;
        } else if (enemy.isDyingRight()){
            if (enemy instanceof Walker && enemy.spriteState == Walker.END_DEAD_RIGHT
                    || enemy instanceof Worm && enemy.spriteState == Worm.END_DEAD_RIGHT)
                enemy.state = enemy.done;
            else enemy.spriteState++;
        }
    }
    
}
