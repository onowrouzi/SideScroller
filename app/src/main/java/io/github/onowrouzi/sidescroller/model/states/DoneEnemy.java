package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.ui.Score;

public class DoneEnemy implements FigureState {

    public Enemy enemy;
    
    public DoneEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        Score.score += 10;
        if (enemy instanceof BossEnemy) {
            GameThread.gameWon = true;
//            Sounds.play("sounds/victory.wav");
//            Sounds.backgroundMusic.stop();
        }
        GameActivity.gameData.enemyFigures.remove(enemy);
    }
    
}
