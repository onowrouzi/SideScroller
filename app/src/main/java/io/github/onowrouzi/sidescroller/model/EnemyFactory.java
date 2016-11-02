package io.github.onowrouzi.sidescroller.model;

import android.content.Context;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemy;

public class EnemyFactory{
    
    public static Enemy generateEnemy(Context context){
        
        int random = (int) (Math.random() * 1000);
        
        Enemy enemy = null;
        if (random > 90 && random < 95) {
            int randomType = (int) (Math.random()*100);
            char type;
            if (randomType < 70) {
                type = 'A';
            } else {
                type = 'B';
            }
            if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                enemy = new GroundEnemy(GameActivity.screenWidth + GameActivity.screenWidth/8, GameActivity.screenHeight - GameActivity.screenHeight/4, GameActivity.screenWidth/8, GameActivity.screenHeight/5, type, context);
            } else {
                enemy = new GroundEnemy(-GameActivity.screenWidth/8, GameActivity.screenHeight - GameActivity.screenHeight/4, GameActivity.screenWidth/8, GameActivity.screenHeight/5, type, context);
                enemy.spriteState = GroundEnemy.WALK_RIGHT;
            }    
        }
        else if (random > 95 && random < 100) {
            if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                enemy = new FlyingEnemy(-GameActivity.screenWidth/8, GameActivity.screenHeight/20, GameActivity.screenWidth/8, GameActivity.screenHeight/8, context);
            } else {
                enemy = new FlyingEnemy(GameActivity.screenWidth + GameActivity.screenWidth/8, GameActivity.screenHeight/20, GameActivity.screenWidth/8, GameActivity.screenHeight/8, context);
                enemy.spriteState = FlyingEnemy.START_FLAP_LEFT;
            }
        }
        return enemy;
    }
    
}
