package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies.FlyingEnemy;

public class AliveFlyingEnemy implements FigureState {

    public FlyingEnemy enemy;
    boolean hasFired = false;
    
    public AliveFlyingEnemy(FlyingEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (enemy.isFacingRight()) {
            enemy.travelRight();
            if ((int) enemy.x >= enemy.fireHere && !hasFired) {
                enemy.fireProjectile();
                hasFired = true;
            }
        } else if (enemy.isFacingLeft()){
            enemy.travelLeft();
            if ((int) enemy.x <= enemy.fireHere && !hasFired) {
                enemy.fireProjectile();
                hasFired = true;
            }
        }
    }
    
}
