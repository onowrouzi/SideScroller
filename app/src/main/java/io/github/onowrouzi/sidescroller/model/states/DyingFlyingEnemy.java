package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemy;

public class DyingFlyingEnemy implements FigureState {

    public FlyingEnemy enemy;
    
    public DyingFlyingEnemy(FlyingEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (enemy.isFacingLeft()) {
            enemy.spriteState = FlyingEnemy.DEAD_LEFT;
        } else if (enemy.isFacingRight()){
            enemy.spriteState = FlyingEnemy.DEAD_RIGHT;
        }

        if (enemy.isDyingLeft()) {
            if (enemy.spriteState == FlyingEnemy.END_DEAD_LEFT)
                enemy.state = enemy.done;
            else enemy.spriteState++;
        } else if (enemy.isDyingRight()){
            if (enemy.spriteState == FlyingEnemy.END_DEAD_RIGHT)
                enemy.state = enemy.done;
            else enemy.spriteState++;
        }
    }
    
}
