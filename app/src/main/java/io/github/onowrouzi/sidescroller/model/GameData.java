package io.github.onowrouzi.sidescroller.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies.Bird;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.Walker;
import io.github.onowrouzi.sidescroller.model.helpers.EnemyFactory;
import io.github.onowrouzi.sidescroller.model.ui.Background;
import io.github.onowrouzi.sidescroller.model.ui.BulletCount;
import io.github.onowrouzi.sidescroller.model.ui.FireBallCount;
import io.github.onowrouzi.sidescroller.model.ui.HealthBars;
import io.github.onowrouzi.sidescroller.model.ui.Score;

public class GameData {

    private Context context;
    public final List<MovableFigure> enemyFigures;
    public final List<MovableFigure> friendFigures;
    public final List<MovableFigure> droppableFigures;
    public final List<GameFigure> uiFigures;
    
    public Player player;
    public static Background background;
    public static Score gameScore;
    public static HealthBars healthBars;
    public static BulletCount bulletCount;
    public static FireBallCount fireBallCount;
    public static boolean stage1;
    public static boolean stage2;
    public static int stage;
    
    public GameData(Context context, Player player) {

        this.context = context;
        this.player = player;

        stage1 = true;
        stage = 1;
        
        enemyFigures = Collections.synchronizedList(new ArrayList<MovableFigure>());
        friendFigures = Collections.synchronizedList(new ArrayList<MovableFigure>());
        droppableFigures = Collections.synchronizedList(new ArrayList<MovableFigure>());
        uiFigures = Collections.synchronizedList(new ArrayList<GameFigure>());

        friendFigures.add(player);
        
        background = new Background(context);
        uiFigures.add(background);
        gameScore = new Score(10,GameActivity.screenHeight/16,80,20);
        uiFigures.add(gameScore);
        healthBars = new HealthBars(GameActivity.screenWidth*4/7, 0, GameActivity.screenWidth/10, GameActivity.screenHeight/10, player, context);
        uiFigures.add(healthBars);
        bulletCount = new BulletCount(10, GameActivity.screenHeight/10, GameActivity.screenWidth/16, GameActivity.screenHeight/20, player, context);
        uiFigures.add(bulletCount);
        fireBallCount = new FireBallCount(GameActivity.screenWidth * 9/10, GameActivity.screenHeight/10, GameActivity.screenWidth/10, GameActivity.screenHeight/12, player, context);
        uiFigures.add(fireBallCount);
        enemyFigures.add(new Walker(GameActivity.screenWidth + GameActivity.screenWidth/8, GameActivity.screenHeight - GameActivity.screenHeight/4, GameActivity.screenWidth/8, GameActivity.screenHeight/5, context));
        enemyFigures.add(new Bird(-GameActivity.screenWidth/8, GameActivity.screenHeight/20, GameActivity.screenWidth/8, GameActivity.screenHeight/8, context));
    }
    
    public void update() {
       
        if (stage1) {
            Enemy enemy = EnemyFactory.generateEnemy(context);
            if (enemy != null) {
                enemyFigures.add(enemy);
            }
        }
        
        synchronized (friendFigures) {
            for (int i = 0; i < friendFigures.size(); i++) {
                friendFigures.get(i).update();
            }
        }

        synchronized (droppableFigures) {
            for (int i = 0; i < droppableFigures.size(); i++){
                droppableFigures.get(i).update();
            }
        }
        
        synchronized (enemyFigures) {
            for (int i = 0; i < enemyFigures.size(); i++) {
                enemyFigures.get(i).update();
            }
        }
        
//        if (Score.score >= 200 && !stage2){
//            setBossStage();
//        }
    }
    
    public void setBossStage() {
        stage1 = false;
        stage2 = true;
        stage = 2;
        enemyFigures.clear();
        enemyFigures.add(new BossEnemy(600, -200, 200, 200, context));
        GameThread.loading = 60;
        player.resetPlayer();
//        background.changeBackground("images/background2.png");
//        backgroundMusic.stop();
//        Sounds.play("sounds/bossLoop.wav");
    }

    public void setStageOne() {
        Score.score = 0;
        stage1 = true;
        stage2 = false;
        stage = 1;
//        background.changeBackground("images/background1.png");
        enemyFigures.clear();
        player.resetPlayer();
        GameThread.gameWon = GameThread.gameOver = false;
//        GameThread.loading = 60;
//        Sounds.backgroundMusic.stop();
//        Sounds.play("sounds/songLoop.wav");
    }
    
}

