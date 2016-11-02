package io.github.onowrouzi.sidescroller.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.GameFigure;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;

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
                    p.setTextSize(100);
                    c.drawText("LOADING ... ", 200, 200, p);
//                    c.drawText(Integer.toString(GameThread.loading / 10), 300, 200, p);
//                    p.setTextSize(25);
//                    c.drawText("    Controls: ", width / 3, height / 2 + 30, p);
//                    c.drawText("    W = JUMP", width / 3, height / 2 + 80, p);
//                    c.drawText("    A = LEFT", width / 3, height / 2 + 110, p);
//                    c.drawText("    D = RIGHT", width / 3, height / 2 + 140, p);
//                    c.drawText("    SPACE = MELEE", width / 3, height / 2 + 170, p);
//                    c.drawText("    LEFT CLICK = SHOOT", width / 3, height / 2 + 200, p);
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
                            //c.drawRect(((Enemy)e).getCollisionBox(), p);
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

