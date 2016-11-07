package io.github.onowrouzi.sidescroller.controller;

import android.util.Log;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.droppables.Droppable;

public class GameThread extends Thread {

    public boolean running = true;
    public static boolean paused;
    public static boolean gameOver;
    public static boolean gameWon;
    private final int FRAMES_PER_SECOND = 20;
    public static int loading;

    @Override
    public void run() {

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
                GameActivity.gamePanel.draw();
            }

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0/FRAMES_PER_SECOND*1500)-(int)(endTime-startTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e){
                    Log.e("THREAD ERROR: ", e.toString());
                }
            }
        }
        System.exit(0);
    }

    private synchronized void processCollisions() {
        for (int i = 0; i <  GameActivity.gameData.friendFigures.size(); i++) {
            MovableFigure f = GameActivity.gameData.friendFigures.get(i);
            for (int j = 0; j < GameActivity.gameData.enemyFigures.size(); j++) {
                MovableFigure e = GameActivity.gameData.enemyFigures.get(j);
                if (e.getCollisionBox().intersect(f.getCollisionBox())
                        && ((e.state == e.alive) || (e.state == e.hurt))){
                    e.handleCollision(f);
                }
            }
        }

        for (int i = 0; i < GameActivity.gameData.droppableFigures.size(); i++){
            Droppable d = (Droppable) GameActivity.gameData.droppableFigures.get(i);
            for (int j = 0; j < GameActivity.gameData.friendFigures.size(); j++){
                MovableFigure f = GameActivity.gameData.friendFigures.get(j);
                if (d.getCollisionBox().intersect(f.getCollisionBox())
                        && f instanceof Player)
                    d.handleCollision(f);
            }
        }
    }

}