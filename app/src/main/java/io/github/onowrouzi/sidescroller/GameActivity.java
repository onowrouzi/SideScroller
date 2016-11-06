package io.github.onowrouzi.sidescroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.controller.RepeatListener;
import io.github.onowrouzi.sidescroller.controller.SoundsManager;
import io.github.onowrouzi.sidescroller.model.GameData;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.helpers.SizeManager;
import io.github.onowrouzi.sidescroller.view.GameDialogManager;
import io.github.onowrouzi.sidescroller.view.GamePanel;

public class GameActivity extends Activity {

    public static GameData gameData;
    public static GamePanel gamePanel;
    public static GameDialogManager gameDialogManager;
    public static SoundsManager soundsManager;
    ImageButton buttonA, buttonB, buttonX, buttonY, buttonLeft, buttonRight;
    public static int screenWidth;
    public static int screenHeight;
    public static int groundLevel;
    public static boolean isMuted = false;
    public static boolean soundsEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        SizeManager sizeManager = new SizeManager(getApplicationContext());
        screenWidth = sizeManager.getX();
        screenHeight = sizeManager.getY();

        gameDialogManager = new GameDialogManager(this);
        soundsManager = new SoundsManager(this);

        final Player player = new Player(screenWidth/2, screenHeight*3/4, screenWidth/8, screenHeight/5, getApplicationContext());

        groundLevel = screenHeight*3/4 + screenHeight/5;

        RelativeLayout surface = (RelativeLayout) findViewById(R.id.surface);
        gameData = new GameData(getApplicationContext(), player);
        gamePanel = new GamePanel(this);
        surface.addView(gamePanel);

        gamePanel.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && player.bulletCount > 0
                        && GameThread.loading == 0){
                    player.fireProjectile(event.getX(), event.getY());
                    return true;
                }
                return false;
            }
        });

        buttonA = (ImageButton) findViewById(R.id.button_a);
        buttonB = (ImageButton) findViewById(R.id.button_b);
        buttonX = (ImageButton) findViewById(R.id.button_x);
        buttonY = (ImageButton) findViewById(R.id.button_y);
        buttonLeft = (ImageButton) findViewById(R.id.button_left);
        buttonRight = (ImageButton) findViewById(R.id.button_right);
        buttonA.bringToFront();
        buttonB.bringToFront();
        buttonX.bringToFront();
        buttonY.bringToFront();
        buttonLeft.bringToFront();
        buttonRight.bringToFront();

        buttonLeft.setOnTouchListener(new RepeatListener(50, 50, player, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameThread.loading == 0) player.travelLeft();
            }
        }));

        buttonRight.setOnTouchListener(new RepeatListener(50, 50, player, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameThread.loading == 0) player.travelRight();
            }
        }));

        buttonA.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN && GameThread.loading == 0)
                    player.jump();
                return true;
            }
        });

        buttonX.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN && GameThread.loading == 0)
                    player.melee();
                return true;
            }
        });

        buttonB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && player.fireBallCount > 0
                        && GameThread.loading == 0)
                    player.throwFireBall();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed(){
        gameDialogManager.showPauseDialog();
    }
}