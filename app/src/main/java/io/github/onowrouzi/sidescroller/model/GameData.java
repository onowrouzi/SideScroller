package io.github.onowrouzi.sidescroller.model;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.List;

import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemy;
import io.github.onowrouzi.sidescroller.model.ui.Background;
import io.github.onowrouzi.sidescroller.model.ui.BulletCount;
import io.github.onowrouzi.sidescroller.model.ui.Ground;
import io.github.onowrouzi.sidescroller.model.ui.HealthBars;
import io.github.onowrouzi.sidescroller.model.ui.Score;

public class GameData {
    
    public final List<MovableFigure> enemyFigures;
    public final List<MovableFigure> friendFigures;
    public final List<GameFigure> uiFigures;
    
    public Player player;
    public static Ground ground;
    public static Background background;
    public static Score gameScore;
    public static HealthBars healthBars;
    public static BulletCount bulletCount;
    public static boolean stage1;
    public static boolean stage2;
    public static int stage;
    
    public GameData() {
        
        stage1 = true;
        stage = 1;
        
        enemyFigures = Collections.synchronizedList(new ArrayList<MovableFigure>() );
        friendFigures = Collections.synchronizedList(new ArrayList<MovableFigure>() );
        uiFigures = Collections.synchronizedList(new ArrayList<GameFigure>());
            
        player = new Player(350,350,120,100);
        friendFigures.add(player);
        
        background = new Background();
        uiFigures.add(background);
        ground = new Ground(0,450,800,200);
        uiFigures.add(ground);
        gameScore = new Score(10,30,80,20);
        uiFigures.add(gameScore);
        healthBars = new HealthBars(500, 30, 200, 20, player);
        uiFigures.add(healthBars);
        bulletCount = new BulletCount(10, 60, 200, 20, player);
        uiFigures.add(bulletCount);
        
        enemyFigures.add(new GroundEnemy(860,378,50,75,'A'));
        enemyFigures.add(new FlyingEnemy(0,30,75,75));
    }
    
    public void update() {
       
        if (stage1) {
            Enemy enemy = EnemyFactory.generateEnemy();
            if (enemy != null) {
                enemyFigures.add(enemy);
            }
        } 
        
        synchronized (friendFigures) {
            for (int i = 0; i < friendFigures.size(); i++) {
                friendFigures.get(i).update();
            }
        }
        
        synchronized (enemyFigures) {
            for (int i = 0; i < enemyFigures.size(); i++) {
                enemyFigures.get(i).update();
            }
        } 
        
        if (Score.score >= 200 && !stage2){
            setBossStage();
        }
    }
    
    public void setBossStage() {
        stage1 = false;
        stage2 = true;
        stage = 2;
        enemyFigures.clear();
        enemyFigures.add(new BossEnemy(600, -200, 200, 200));
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
        background.changeBackground("images/background1.png");
        enemyFigures.clear();
        player.resetPlayer();
        GameThread.gameWon = GameThread.gameOver = false;
        GameThread.loading = 60;
//        Sounds.backgroundMusic.stop();
//        Sounds.play("sounds/songLoop.wav");
    }
    
}

