package io.github.onowrouzi.sidescroller.model.states;

import android.content.Context;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.GameData;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemies.BossEnemy;
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
        GameActivity.gameData.enemyFigures.remove(enemy);
        if (enemy instanceof BossEnemy) GameData.bossPresent = false;
        DroppableFactory.generateDroppable(enemy.x, enemy.y, context);
    }
    
}
