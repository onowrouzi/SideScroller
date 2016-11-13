package io.github.onowrouzi.sidescroller.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.droppables.Droppable;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemies.BossEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.FlyingEnemies.Bird;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.Walker;
import io.github.onowrouzi.sidescroller.model.helpers.EnemyFactory;
import io.github.onowrouzi.sidescroller.model.ui.Background;
import io.github.onowrouzi.sidescroller.model.ui.ShurikenCount;
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
    public static ShurikenCount shurikenCount;
    public static FireBallCount fireBallCount;
    public static boolean bossPresent;
    public static int spawnBossScore = 200;

    public GameData(Context context, Player player) {

        this.context = context;
        this.player = player;
        
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
        shurikenCount = new ShurikenCount(10, GameActivity.screenHeight/10, GameActivity.screenWidth/16, GameActivity.screenHeight/20, player, context);
        uiFigures.add(shurikenCount);
        fireBallCount = new FireBallCount(GameActivity.screenWidth * 9/10, GameActivity.screenHeight/10, GameActivity.screenWidth/10, GameActivity.screenHeight/12, player, context);
        uiFigures.add(fireBallCount);
        enemyFigures.add(new Walker(GameActivity.screenWidth + GameActivity.screenWidth/8, GameActivity.screenHeight - GameActivity.screenHeight/4, GameActivity.screenWidth/8, GameActivity.screenHeight/5, context));
        enemyFigures.add(new Bird(-GameActivity.screenWidth/8, GameActivity.screenHeight/20, GameActivity.screenWidth/8, GameActivity.screenHeight/8, context));
    }
    
    public void update() { 

        processCollisions();

        if (!bossPresent) {
            Enemy enemy = EnemyFactory.generateEnemy(context);
            if (enemy != null) enemyFigures.add(enemy);
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
        
        if (Score.score >= spawnBossScore && !bossPresent){
            enemyFigures.add(new BossEnemy(GameActivity.screenWidth*7/8, -GameActivity.screenHeight/6,
                    GameActivity.screenWidth/6, GameActivity.screenHeight/6, context));
            bossPresent = true;
            spawnBossScore += 200;
        }
    }

    private synchronized void processCollisions() {
        for (int i = 0; i < friendFigures.size(); i++) {
            MovableFigure f = friendFigures.get(i);
            for (int j = 0; j < enemyFigures.size(); j++) {
                MovableFigure e = enemyFigures.get(j);
                if (e.getCollisionBox().intersect(f.getCollisionBox())
                        && ((e.state == e.alive) || (e.state == e.hurt))){
                    e.handleCollision(f);
                } else if (e instanceof BossEnemy && e.getCollisionBox().intersect(f.getCollisionBox())){
                    BossEnemy be = (BossEnemy) e;
                    if (be.state == be.drop || be.state == be.raging) be.handleCollision(f);
                }
            }
        }

        for (int i = 0; i < droppableFigures.size(); i++){
            Droppable d = (Droppable) droppableFigures.get(i);
            for (int j = 0; j < friendFigures.size(); j++){
                MovableFigure f = friendFigures.get(j);
                if (d.getCollisionBox().intersect(f.getCollisionBox())
                        && f instanceof Player)
                    d.handleCollision(f);
            }
        }
    }

    public void setStage() {
        Score.score = 0;
        enemyFigures.clear();
        droppableFigures.clear();
        friendFigures.clear();
        friendFigures.add(player);
        player.resetPlayer();
        GameThread.gameWon = GameThread.gameOver = false;
    }
    
}