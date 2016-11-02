package io.github.onowrouzi.sidescroller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.controller.RepeatListener;
import io.github.onowrouzi.sidescroller.model.GameData;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.SizeManager;
import io.github.onowrouzi.sidescroller.view.GamePanel;

public class GameActivity extends Activity {

    public static GameData gameData;
    public static GamePanel gamePanel;
    ImageButton buttonA, buttonB, buttonX, buttonY, buttonLeft, buttonRight;
    public static int screenWidth;
    public static int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        SizeManager sizeManager = new SizeManager(getApplicationContext());
        screenWidth = sizeManager.getX();
        screenHeight = sizeManager.getY();

        final Player player = new Player(screenWidth/2, screenHeight-screenHeight/4, screenWidth/8, screenHeight/5, getApplicationContext());

        RelativeLayout surface = (RelativeLayout) findViewById(R.id.surface);
        gameData = new GameData(getApplicationContext(), player);
        gamePanel = new GamePanel(getApplicationContext());
        surface.addView(gamePanel);

        gamePanel.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && event.getY() < screenHeight - screenHeight/4
                        && player.bulletCount > 0){
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
                player.travelLeft();
            }
        }));

        buttonRight.setOnTouchListener(new RepeatListener(50, 50, player, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.travelRight();
            }
        }));

        buttonA.setOnTouchListener(new RepeatListener(50, 50, player, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.jump();
            }
        }));

        buttonX.setOnTouchListener(new RepeatListener(50, 50, player, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.melee();
            }
        }));
    }

}
