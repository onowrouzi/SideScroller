package io.github.onowrouzi.sidescroller.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.GameFigure;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    
    public static int width;
    public static int height;

    private GameThread gameThread;
    private Canvas c;
    private Paint p;
    private SurfaceHolder holder;

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);

        gameThread = new GameThread();
        setFocusable(true);

        c = new Canvas();
        p = new Paint();
    }

    public void draw(){
        p.setColor(Color.WHITE);
        p.setTextSize(100);
        c.drawText("HEY!!!", 50, 50, p);
        if (holder.getSurface().isValid()) {
            c = holder.lockCanvas(null);
            if (gameThread.running) {

                if (GameThread.gameWon) {
                    p.setColor(Color.WHITE);
                    c.drawText("YOU WON!!!", width / 2 - 250, height / 2, p);
                } else if (GameThread.loading > 0) {
                    p.setColor(Color.WHITE);
                    p.setTextSize(GameActivity.screenWidth/20);
                    c.drawText("LOADING ... ", GameActivity.screenWidth/20, GameActivity.screenHeight/12, p);
                    p.setTextSize(GameActivity.screenWidth/24);
                    c.drawText("    Controls: ", GameActivity.screenWidth / 4, GameActivity.screenHeight / 3, p);
                    c.drawText("    Arrows = Movement", GameActivity.screenWidth / 4, GameActivity.screenHeight / 3 + p.getTextSize(), p);
                    c.drawText("    A = JUMP", GameActivity.screenWidth / 4, GameActivity.screenHeight / 3  + p.getTextSize()*2+10, p);
                    c.drawText("    X = MELEE", GameActivity.screenWidth / 4, GameActivity.screenHeight / 3  + p.getTextSize()*3+10, p);
                    c.drawText("    Tap screen above player to shoot.", GameActivity.screenWidth / 4,
                                                            GameActivity.screenHeight / 3  + p.getTextSize()*4+10, p);
                } else if (!GameThread.gameOver) {

                    p.setColor(Color.WHITE);

                    synchronized (GameActivity.gameData.uiFigures) {
                        for (GameFigure u : GameActivity.gameData.uiFigures) {
                            u.render(c);
                        }
                    }

                    synchronized (GameActivity.gameData.friendFigures) {
                        for (GameFigure f : GameActivity.gameData.friendFigures) {
                            f.render(c);
                        }
                    }

                    synchronized (GameActivity.gameData.enemyFigures) {
                        for (GameFigure e : GameActivity.gameData.enemyFigures) {
                            e.render(c);
                        }
                    }

                    if (GameThread.paused) {
                        p.setTextSize(100);
                        c.drawText("PAUSED", width / 2 - 175, height / 2, p);
                    }

                } else {

                    p.setColor(Color.WHITE);
                    p.setTextSize(GameActivity.screenWidth/8);
                    c.drawText("GAME OVER", GameActivity.screenWidth / 2 - GameActivity.screenWidth / 3, GameActivity.screenHeight / 3, p);
                }

                holder.unlockCanvasAndPost(c);

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas) { draw(); }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { gameThread.start(); }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.running = false;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try it again and again...
            }
        }
    }
}