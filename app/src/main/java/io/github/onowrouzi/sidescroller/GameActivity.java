package io.github.onowrouzi.sidescroller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.controller.RepeatListener;
import io.github.onowrouzi.sidescroller.controller.SoundsManager;
import io.github.onowrouzi.sidescroller.model.GameData;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.helpers.SizeManager;
import io.github.onowrouzi.sidescroller.model.ui.Score;
import io.github.onowrouzi.sidescroller.view.GamePanel;

public class GameActivity extends Activity {

    public static GameData gameData;
    public static GamePanel gamePanel;
    public static SoundsManager soundsManager;
    ImageButton buttonA, buttonB, buttonX, buttonY, buttonLeft, buttonRight;
    EditText inputName;
    public static int screenWidth;
    public static int screenHeight;
    public static AlertDialog gameOverDialog;
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

        soundsManager = new SoundsManager(this);

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

        buttonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                player.jump();
            }
        });

        buttonX.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                player.melee();
            }
        });

        inputName = new EditText(this);
        gameOverDialog = new AlertDialog.Builder(GameActivity.this)
                .setTitle("Game Over")
                .setMessage("Input your name:")
                .setView(inputName)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String playerName = inputName.getText().toString();

                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(playerName, Score.score);
                        editor.commit();

                        replayOrExit();
                    }
                })
                .create();
        gameOverDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onBackPressed(){
        GameThread.paused = true;
        String muteOrNot = isMuted ? "Turn Music ON" : "Turn Music OFF";
        String soundsOnOrOff = soundsEnabled ? "Turn Sounds OFF" : "Turn Sounds ON";
        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle("PAUSED")
                .setCancelable(false)
                .setItems(new CharSequence[]{"Resume", muteOrNot, soundsOnOrOff, "Exit Game"}, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int selected){
                        switch (selected){
                            case 0: GameThread.paused = false;
                                    break;
                            case 1: if (isMuted) {
                                        MainActivity.mServ.resumeMusic();
                                        isMuted = false;
                                    } else {
                                        MainActivity.mServ.pauseMusic();
                                        isMuted = true;
                                    }
                                    GameThread.paused = false;
                                    break;
                            case 2: soundsEnabled = !soundsEnabled;
                                    GameThread.paused = false;
                                    break;
                            case 3: confirmExit();
                                    break;
                        }
                    }
                })
                .create();
        ad.setCanceledOnTouchOutside(false);
        ad.show();
    }

    public void confirmExit(){
        AlertDialog ad = new AlertDialog.Builder(this)
            .setMessage("Your score will be discarded if you quit... \nAre you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameThread.paused = false;
                    }
                })
                .create();
        ad.setCanceledOnTouchOutside(false);
        ad.show();
    }

    public void replayOrExit(){
        AlertDialog ad = new AlertDialog.Builder(this)
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
        ad.setCanceledOnTouchOutside(false);
        ad.show();
    }

}