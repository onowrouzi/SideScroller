package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemies.BossEnemy;

public class RagingBoss implements FigureState {

    public BossEnemy boss;
    public int traversals;

    public RagingBoss(BossEnemy boss){ this.boss = boss; }

    @Override
    public void update() {
        if (boss.isFacingLeft()) {
            if (boss.x > GameActivity.screenWidth*0.1){
                boss.x -= boss.width/3;
                boss.spriteState = boss.spriteState < BossEnemy.END_RAGE_LEFT ? boss.spriteState + 1 : BossEnemy.RAGE_LEFT;
            } else {
                boss.spriteState = BossEnemy.RAGE_RIGHT;
                traversals++;
            }
        } else if (boss.isFacingRight()){
            if (boss.x < GameActivity.screenWidth-boss.width*3) {
                    boss.x += boss.width/3;
                boss.spriteState = boss.spriteState < BossEnemy.END_RAGE_RIGHT ? boss.spriteState + 1 : BossEnemy.RAGE_RIGHT;
            } else {
                boss.spriteState = BossEnemy.RAGE_LEFT;
                traversals++;
            }
        }

        if (traversals == 2) {
            boss.spriteState = boss.isFacingLeft() ? BossEnemy.FLY_LEFT : BossEnemy.FLY_RIGHT;
            if (boss.isFacingLeft()) boss.x += boss.width * 2;
            boss.state = boss.descending ? boss.drop : boss.alive;
            traversals = 0;
        }
    }
}
