package io.github.onowrouzi.sidescroller.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.MainActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.ui.Score;
import io.github.onowrouzi.sidescroller.view.Fragments.PauseDialog;

public class GameDialogManager {

    public Activity activity;
    public static PauseDialog pauseDialog;
    public static AlertDialog exitDialog, gameOverDialog, replayDialog;

    public GameDialogManager(Activity activity){
        this.activity = activity;
        pauseDialog = new PauseDialog();
        buildExitDialog();
        buildGameOverDialog();
        buildReplayDialog();
    }

    public void showPauseDialog(){
        GameThread.paused = true;
        pauseDialog.show(activity.getFragmentManager(), "PAUSED");
    }

    public void buildExitDialog() {
        exitDialog = new AlertDialog.Builder(activity)
                .setMessage(R.string.txt_exit_prompt)
                .setCancelable(false)
                .setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                        activity.startActivity(i);
                    }
                })
                .setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameThread.paused = false;
                    }
                })
                .create();
        exitDialog.setCanceledOnTouchOutside(false);
    }

    public void buildReplayDialog() {
        replayDialog = new AlertDialog.Builder(activity)
                .setMessage(R.string.txt_play_again)
                .setCancelable(false)
                .setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GameActivity.gameData.setStage();
                    }
                })
                .setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                        activity.startActivity(i);
                    }
                })
                .create();
        replayDialog.setCanceledOnTouchOutside(false);
    }

    public void buildGameOverDialog() {
        final EditText inputName = new EditText(activity);
        TextView txtGameOver = new TextView(activity);
        txtGameOver.setText(R.string.txt_game_over);
        txtGameOver.setTextSize(30);
        txtGameOver.setGravity(Gravity.CENTER);
        txtGameOver.setTypeface(MainActivity.font);
        gameOverDialog = new AlertDialog.Builder(activity)
                .setCustomTitle(txtGameOver)
                .setMessage(R.string.txt_input_name)
                .setView(inputName)
                .setCancelable(false)
                .setPositiveButton(R.string.txt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String playerName = inputName.getText().toString();

                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(playerName, Score.score);
                        editor.commit();

                        replayDialog.show();
                    }
                })
                .create();
        gameOverDialog.setCanceledOnTouchOutside(false);
    }
}