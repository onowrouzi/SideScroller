package io.github.onowrouzi.sidescroller.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemy;

public class EnemyFactory{
    
    public static Enemy generateEnemy(Context context){
        
        int random = (int) (Math.random() * 100);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        
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
                enemy = new GroundEnemy(size.x + size.x/8, size.y - 150, size.x/8, size.y/5, type, context);
            } else {
                enemy = new GroundEnemy(-size.x/8, size.y - 150, size.x/8, size.y/5, type, context);
                enemy.spriteState = GroundEnemy.STAND_RIGHT;
            }    
        }
        else if (random % 79 == 0) {
            if (GameActivity.gameData.enemyFigures.size() % 2 == 0) {
                enemy = new FlyingEnemy(-size.x/8, size.y/30, size.x/8, size.y/8, context);
            } else {
                enemy = new FlyingEnemy(size.x + size.x/8, size.y/30, size.x/8, size.y/8, context);
                enemy.spriteState = FlyingEnemy.START_FLAP_LEFT;
            }
        }
        return enemy;
    }
    
}
