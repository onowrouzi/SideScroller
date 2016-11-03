package io.github.onowrouzi.sidescroller.model.helpers;

import android.content.Context;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies.Bat;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies.Bird;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.SpikyRoll;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.Walker;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.Worm;

public class EnemyFactory{
    
    public static Enemy generateEnemy(Context context){
        
        int random = (int) (Math.random() * 1000);
        
        Enemy enemy = null;
        if ((random > 85 && random < 95) || GameActivity.gameData.enemyFigures.size() < 2) {
            int randomType = (int) (Math.random()*100);
            if (randomType < 40) {
                if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                    enemy = new Walker(GameActivity.screenWidth + GameActivity.screenWidth / 8,
                            GameActivity.screenHeight - GameActivity.screenHeight / 4,
                            GameActivity.screenWidth / 8,
                            GameActivity.screenHeight / 5,
                            context);
                } else {
                    enemy = new Walker(-GameActivity.screenWidth / 8,
                            GameActivity.screenHeight - GameActivity.screenHeight / 4,
                            GameActivity.screenWidth / 8,
                            GameActivity.screenHeight / 5,
                            context);
                    enemy.spriteState = Walker.WALK_RIGHT;
                }
            } else if (randomType < 60) {
                if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                    enemy = new SpikyRoll(GameActivity.screenWidth + GameActivity.screenWidth / 8,
                            GameActivity.screenHeight - GameActivity.screenHeight / 8,
                            GameActivity.screenWidth / 12,
                            GameActivity.screenHeight / 12,
                            context);
                } else {
                    enemy = new SpikyRoll(-GameActivity.screenWidth / 8,
                            GameActivity.screenHeight - GameActivity.screenHeight / 8,
                            GameActivity.screenWidth / 12,
                            GameActivity.screenHeight / 12,
                            context);
                    enemy.spriteState = SpikyRoll.WALK_RIGHT;
                }
            } else {
                if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                    enemy = new Worm(GameActivity.screenWidth + GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight - GameActivity.screenHeight / 5,
                                    GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 7,
                                    context);
                } else {
                    enemy = new Worm(-GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight - GameActivity.screenHeight / 5,
                                    GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 7,
                                    context);
                    enemy.spriteState = Worm.WALK_RIGHT;
                }
            }
        } else if ((random > 95 && random < 100)  || GameActivity.gameData.enemyFigures.size() < 2) {
            int randomType = (int) (Math.random()*100);
            if (randomType < 50) {
                if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                    enemy = new Bird(-GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 20,
                                    GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 8,
                                    context);
                } else {
                    enemy = new Bird(GameActivity.screenWidth + GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 20,
                                    GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 8,
                                    context);
                    enemy.spriteState = Bird.START_FLAP_LEFT;
                }
            } else {
                if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                    enemy = new Bat(-GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 16,
                                    GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 8,
                                    context);
                } else {
                    enemy = new Bat(GameActivity.screenWidth + GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 16,
                                    GameActivity.screenWidth / 8,
                                    GameActivity.screenHeight / 8,
                                    context);
                    enemy.spriteState = Bat.START_FLAP_LEFT;
                }
            }
        }
        return enemy;
    }
    
}
