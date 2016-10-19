package io.github.onowrouzi.sidescroller.controller;

import android.animation.Animator;
import android.animation.TimeInterpolator;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.Projectile;
import io.github.onowrouzi.sidescroller.model.enemies.BossEnemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemy;

public class GameThread extends Animator implements Runnable {

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
                    GameActivity.gameData.update();
                } else {
                    loading--;
                }
//                GameActivity.screen.gameRender();
//                GameActivity.screen.printScreen();
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
//                if (e.getCollisionBox().intersects(f.getCollisionBox())
//                        && (e.state == e.alive || e.state == e.hurt)){
//                    handleCollisions(e,f);
//                }
            }
        }
    }

    private synchronized void handleCollisions(MovableFigure e, MovableFigure f) {
        if (f instanceof Player) {
            if (GameActivity.gameData.player.spriteState == Player.MELEE_LEFT ||
                    GameActivity.gameData.player.spriteState == Player.MELEE_RIGHT) {
                if (e instanceof BossEnemy) {
                    BossEnemy boss = (BossEnemy) e;
                    boss.hurt();
                } else if (GameActivity.gameData.player.spriteState == Player.MELEE_LEFT
                        && e.spriteState < GroundEnemy.DEAD_LEFT
                        && e.x > GameActivity.gameData.player.x) {
                    GameActivity.gameData.player.hurt();
                } else if (GameActivity.gameData.player.spriteState == Player.MELEE_RIGHT
                        && e.spriteState > GroundEnemy.DEAD_LEFT
                        && e.x < GameActivity.gameData.player.x) {
                    GameActivity.gameData.player.hurt();
                } else {
                    e.state = e.dying;
                }
            } else if (GameActivity.gameData.player.descend || GameActivity.gameData.player.ascend) {
                if (e instanceof GroundEnemy){
                    GroundEnemy enemy = (GroundEnemy) e;
                    if (enemy.type == 'A') {
                        e.state = e.dying;
                        GameActivity.gameData.player.bounceOff();
                    } else {
                        GameActivity.gameData.player.hurt();
                        GameActivity.gameData.player.bounceBack();
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

    @Override
    public long getStartDelay() {
        return 0;
    }

    @Override
    public void setStartDelay(long startDelay) {

    }

    @Override
    public Animator setDuration(long duration) {
        return null;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public void setInterpolator(TimeInterpolator value) {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}