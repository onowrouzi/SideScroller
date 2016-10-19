package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.enemies.BossEnemy;

public class DescendingBoss implements FigureState {
   
    public BossEnemy boss;
    
    public DescendingBoss(BossEnemy boss){
        this.boss = boss;
    }
    
    @Override
    public void update(){
        if (boss.y+boss.height<450){
            boss.y += 10;
        } else {
            boss.state = boss.alive;
        }
    }
}
