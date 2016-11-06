package io.github.onowrouzi.sidescroller.controller;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.droppables.Droppable;
import io.github.onowrouzi.sidescroller.model.droppables.HealthDroppable;
import io.github.onowrouzi.sidescroller.model.droppables.ShurikenDroppable;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.SpikyRoll;
import io.github.onowrouzi.sidescroller.model.projectiles.Projectile;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.GroundEnemy;

public class GameThread extends Thread {

    public boolean running = true;
    public static boolean paused;
    public static boolean gameOver;
    public static boolean gameWon;
    private final int FRAMES_PER_SECOND = 20;
    public static int loading;

    @Override
    public void run() {

        //Sounds.play("sounds/songLoop.wav");
        loading = 60;

        while (running){
            long startTime = System.currentTimeMillis();

            if (!paused && !gameOver) {
                if (loading == 0) {
                    processCollisions();
                    checkForPickups();
                    GameActivity.gameData.update();
                } else {
                    loading--;
                }
                GameActivity.gamePanel.draw();
            }

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0/FRAMES_PER_SECOND*1500)-(int)(endTime-startTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e){}
            }
        }
        System.exit(0);
    }

    private synchronized void processCollisions() {
        for (MovableFigure f : GameActivity.gameData.friendFigures) {
            for (MovableFigure e : GameActivity.gameData.enemyFigures) {
                if (e.getCollisionBox().intersect(f.getCollisionBox())
                        && ((e.state == e.alive) || (e.state == e.hurt))){
                    handleCollisions(e,f);
                }
            }
        }
    }

    private synchronized void checkForPickups(){
        for (int i = 0; i < GameActivity.gameData.droppableFigures.size(); i++){
            MovableFigure d = GameActivity.gameData.droppableFigures.get(i);
            if (GameActivity.gameData.player.getCollisionBox().intersect(d.getCollisionBox())){
                if (d instanceof HealthDroppable && GameActivity.gameData.player.health < 6){
                    GameActivity.gameData.player.health++;
                    GameActivity.gameData.droppableFigures.remove(d);
                }
                if (d instanceof ShurikenDroppable && GameActivity.gameData.player.bulletCount < 10){
                    GameActivity.gameData.player.bulletCount = 10;
                    GameActivity.gameData.droppableFigures.remove(d);
                }
            }
        }
    }

    private synchronized void handleCollisions(MovableFigure e, MovableFigure f) {
        if (f instanceof Player) {
            if (GameActivity.gameData.player.isMeleeLeft() ||
                    GameActivity.gameData.player.isMeleeRight()) {
                if (e instanceof BossEnemy) {
                    BossEnemy boss = (BossEnemy) e;
                    boss.hurt();
                } else if (e instanceof SpikyRoll){
                    //do nothing if player melee's spikyroll...
                } else if (GameActivity.gameData.player.isMeleeLeft()
                        && e.state == e.alive
                        && e.x > GameActivity.gameData.player.x) {
                    GameActivity.gameData.player.hurt();
                } else if (GameActivity.gameData.player.isMeleeRight()
                        && !(e instanceof SpikyRoll)
                        && e.state == e.alive
                        && e.x < GameActivity.gameData.player.x) {
                    GameActivity.gameData.player.hurt();
                } else {
                    e.state = e.dying;
                }
            } else if (GameActivity.gameData.player.descend || GameActivity.gameData.player.ascend) {
                if (e instanceof GroundEnemy){
                    GroundEnemy enemy = (GroundEnemy) e;
                    if (enemy instanceof SpikyRoll){
                        GameActivity.gameData.player.hurt();
                        GameActivity.gameData.player.bounceBack();
                    } else{
                        e.state = e.dying;
                        GameActivity.gameData.player.bounceOff();
                    }
                } else {
                    GameActivity.gameData.player.hurt();
                }
            } else {
                GameActivity.gameData.player.hurt();
                if (e instanceof Projectile){
                    e.state = e.dying;
                }
            }
        } else {
            if (e instanceof BossEnemy){
                BossEnemy boss = (BossEnemy) e;
                boss.hurt();
            } else {
                e.state = e.dying;
                f.state = f.dying;
            }
        }
    }

}