package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemy;

public class AliveFlyingEnemy implements FigureState {

    public FlyingEnemy enemy;
    
    public AliveFlyingEnemy(FlyingEnemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        if (enemy.isFacingRight()) {
            enemy.travelRight();
        } else if (enemy.isFacingLeft()){
            enemy.travelLeft();
        }
        if ((int)enemy.x == enemy.fireHere){ 
            enemy.fireProjectile();
        }
    }
    
}
