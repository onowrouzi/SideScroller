package io.github.onowrouzi.sidescroller.model.states;

import android.content.Context;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.droppables.Droppable;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.helpers.DroppableFactory;
import io.github.onowrouzi.sidescroller.model.ui.Score;

public class DoneEnemy implements FigureState {

    public Enemy enemy;
    private DroppableFactory df = new DroppableFactory();
    private Context context;
    
    public DoneEnemy(Enemy enemy, Context context) {
        this.enemy = enemy;
        this.context = context;
    }

    @Override
    public void update() {
        Score.score += 10;
//        if (enemy instanceof BossEnemy) {
//            GameThread.gameWon = true;
//            Sounds.play("sounds/victory.wav");
//            Sounds.backgroundMusic.stop();
//        }
        GameActivity.gameData.enemyFigures.remove(enemy);
        DroppableFactory.generateDroppable(enemy.x, enemy.y, context);
    }
    
}
