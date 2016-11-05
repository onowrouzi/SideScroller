package io.github.onowrouzi.sidescroller.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.MainActivity;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.ui.Score;

public class GameDialogManager {
    
    public Activity activity;

    public GameDialogManager(Activity activity){
        this.activity = activity;
    }

    public void showPauseDialog() {
        GameThread.paused = true;
        String muteOrNot = GameActivity.isMuted ? "Turn Music ON" : "Turn Music OFF";
        String soundsOnOrOff = GameActivity.soundsEnabled ? "Turn Sounds OFF" : "Turn Sounds ON";
        AlertDialog ad = new AlertDialog.Builder(activity)
                .setTitle("PAUSED")
                .setCancelable(false)
                .setItems(new CharSequence[]{"Resume", muteOrNot, soundsOnOrOff, "Exit Game"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selected) {
                        switch (selected) {
                            case 0:
                                GameThread.paused = false;
                                break;
                            case 1:
                                if (GameActivity.isMuted) {
                                    MainActivity.mServ.resumeMusic();
                                    GameActivity.isMuted = false;
                                } else {
                                    MainActivity.mServ.pauseMusic();
                                    GameActivity.isMuted = true;
                                }
                                GameThread.paused = false;
                                break;
                            case 2:
                                GameActivity.soundsEnabled = !GameActivity.soundsEnabled;
                                GameThread.paused = false;
                                break;
                            case 3:
                                showExitDialog();
                                break;
                        }
                    }
                })
                .create();
        ad.setCanceledOnTouchOutside(false);
        ad.show();
    }

    public void showExitDialog() {
        AlertDialog ad = new AlertDialog.Builder(activity)
                .setMessage("Your score will be discarded if you quit... \nAre you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                        activity.startActivity(i);
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

    public void showReplayDialog() {
        AlertDialog ad = new AlertDialog.Builder(activity)
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
                        Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                        activity.startActivity(i);
                    }
                })
                .create();
        ad.setCanceledOnTouchOutside(false);
        ad.show();
    }

    public void showGameOverDialog() {
        final EditText inputName = new EditText(activity);
        AlertDialog ad = new AlertDialog.Builder(activity)
                .setTitle("Game Over")
                .setMessage("Input your name:")
                .setView(inputName)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String playerName = inputName.getText().toString();

                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(playerName, Score.score);
                        editor.commit();

                        showReplayDialog();
                    }
                })
                .create();
        ad.setCanceledOnTouchOutside(false);
        ad.show();
    }
}