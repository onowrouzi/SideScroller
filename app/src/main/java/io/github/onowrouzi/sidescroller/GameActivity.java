package io.github.onowrouzi.sidescroller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.controller.RepeatListener;
import io.github.onowrouzi.sidescroller.model.GameData;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.helpers.SizeManager;
import io.github.onowrouzi.sidescroller.view.GamePanel;

public class GameActivity extends Activity {

    public static GameData gameData;
    public static GamePanel gamePanel;
    ImageButton buttonA, buttonB, buttonX, buttonY, buttonLeft, buttonRight;
    public static int screenWidth;
    public static int screenHeight;
    public static AlertDialog gameOverDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        SizeManager sizeManager = new SizeManager(getApplicationContext());
        screenWidth = sizeManager.getX();
        screenHeight = sizeManager.getY();

        final Player player = new Player(screenWidth/2, screenHeight*3/4, screenWidth/8, screenHeight/5, getApplicationContext());

        RelativeLayout surface = (RelativeLayout) findViewById(R.id.surface);
        gameData = new GameData(getApplicationContext(), player);
        gamePanel = new GamePanel(this);
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

        gameOverDialog = new AlertDialog.Builder(GameActivity.this)
                .setTitle("Game Over")
                .setMessage("Do you want to play again?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GameActivity.gameData.setStageOne();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                })
                .create();
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
