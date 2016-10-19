package io.github.onowrouzi.sidescroller.model;

import android.content.res.Resources;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemy;

public class EnemyFactory{
    
    public static Enemy generateEnemy(Resources resources){
        
        int random = (int) (Math.random() * 100);
        
        Enemy enemy = null;
        if (random % 97 == 0) {
            int randomType = (int) (Math.random()*100);
            char type;
            if (randomType < 70) {
                type = 'A';
            } else {
                type = 'B';
            }
            if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                enemy = new GroundEnemy(860, 378, 50, 75, type, resources);
            } else {
                enemy = new GroundEnemy(-60, 378, 50, 75, type, resources);
                enemy.spriteState = GroundEnemy.STAND_RIGHT;
            }    
        }
        else if (random % 79 == 0) {
            if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                enemy = new FlyingEnemy(-60, 30, 75, 75, resources);
            } else {
                enemy = new FlyingEnemy(860, 125, 75, 75, resources);
                enemy.spriteState = FlyingEnemy.START_FLAP_LEFT;
            }
        }
        return enemy;
    }
    
}
