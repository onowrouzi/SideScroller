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
import io.github.onowrouzi.sidescroller.model.GameData;
import io.github.onowrouzi.sidescroller.model.GameFigure;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    
    public static int width;
    public static int height;

    private GameThread gameThread;
    private Canvas c;
    private Paint p;
    private Image doubleBufferImage = null;
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
                    p.setTextSize(50);
                    c.drawText("LOADING STAGE " + Integer.toString(GameData.stage), width / 3 - 50, height / 2 - 100, p);
                    c.drawText(" IN " + Integer.toString(GameThread.loading / 10), width / 2 - 50, height / 2 - 25, p);
                    p.setTextSize(25);
                    c.drawText("    Controls: ", width / 3, height / 2 + 30, p);
                    c.drawText("    W = JUMP", width / 3, height / 2 + 80, p);
                    c.drawText("    A = LEFT", width / 3, height / 2 + 110, p);
                    c.drawText("    D = RIGHT", width / 3, height / 2 + 140, p);
                    c.drawText("    SPACE = MELEE", width / 3, height / 2 + 170, p);
                    c.drawText("    LEFT CLICK = SHOOT", width / 3, height / 2 + 200, p);
                } else if (!GameThread.gameOver) {

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
                        p.setColor(Color.BLACK);
                        p.setTextSize(100);
                        c.drawText("PAUSED", width / 2 - 175, height / 2, p);
                    }

                } else {

                    p.setColor(Color.WHITE);
                    p.setTextSize(100);
                    c.drawText("GAME OVER", width / 2 - 250, height / 2, p);
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
    public void onDraw(Canvas canvas) {
        System.out.println("DRAW!!!");
        draw();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("SURFACE CREATED");
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

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

