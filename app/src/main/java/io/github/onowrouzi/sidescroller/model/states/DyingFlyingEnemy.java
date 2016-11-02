package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies.Bat;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies.Bird;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies.FlyingEnemy;

public class DyingFlyingEnemy implements FigureState {

    public FlyingEnemy enemy;
    
    public DyingFlyingEnemy(FlyingEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (enemy.isFacingLeft()) {
            enemy.spriteState = enemy instanceof Bird ? Bird.DEAD_LEFT : Bat.DEAD_LEFT;
        } else if (enemy.isFacingRight()){
            enemy.spriteState = enemy instanceof Bird ? Bird.DEAD_RIGHT : Bat.DEAD_RIGHT;
        }

        if (enemy.isDyingLeft()) {
            if ((enemy.spriteState == Bird.END_DEAD_LEFT && enemy instanceof Bird)
                || (enemy.spriteState == Bat.END_DEAD_LEFT && enemy instanceof Bat))
                enemy.state = enemy.done;
            else enemy.spriteState++;
        } else if (enemy.isDyingRight()){
            if ((enemy.spriteState == Bird.END_DEAD_RIGHT && enemy instanceof Bird)
                    || (enemy.spriteState == Bat.END_DEAD_RIGHT && enemy instanceof Bat))
                enemy.state = enemy.done;
            else enemy.spriteState++;
        }
    }
    
}
