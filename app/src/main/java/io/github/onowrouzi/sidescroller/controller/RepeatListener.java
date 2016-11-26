package io.github.onowrouzi.sidescroller.controller;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import io.github.onowrouzi.sidescroller.model.Player;

public class RepeatListener implements OnTouchListener {

    private Handler handler = new Handler();

    private int firstInterval;
    private final int interval;
    private final OnClickListener clickListener;
    public Player player;
    private View view;

    private Runnable handlerRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, interval);
            clickListener.onClick(view);
        }
    };
    
    public RepeatListener(int firstInterval, int interval, Player player, OnClickListener clickListener) {
        this.player = player;

        if (clickListener == null)
            throw new IllegalArgumentException("Must include click listener");
        if (firstInterval <= 0 || interval <= 0)
            throw new IllegalArgumentException("Intervals must be greater than zero.");

        this.firstInterval = firstInterval;
        this.interval = interval;
        this.clickListener = clickListener;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(handlerRunnable);
                handler.postDelayed(handlerRunnable, firstInterval);
                this.view = view;
                this.view.setPressed(true);
                clickListener.onClick(view);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handler.removeCallbacks(handlerRunnable);
                this.view.setPressed(false);
                this.view = null;
                if (player.isRunningRight()) {
                    player.spriteState = Player.STAND_RIGHT;
                } else if (player.isRunningLeft()){
                    player.spriteState = Player.STAND_LEFT;
                }
                return true;
        }
        return false;
    }

}