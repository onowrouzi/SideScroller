package io.github.onowrouzi.sidescroller.view.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.MainActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.view.GameDialogManager;

public class PauseDialog extends DialogFragment {

    public static TextView txtPaused, txtResume, txtMusicToggle, txtSoundsToggle, txtExit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pause_menu, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);

        txtPaused = (TextView) view.findViewById(R.id.txt_paused);
        txtPaused.setTypeface(MainActivity.font);

        txtResume = (TextView) view.findViewById(R.id.txt_resume);
        txtResume.setTypeface(MainActivity.font);
        txtResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameThread.paused = false;
                dismiss();
            }
        });

        txtMusicToggle = (TextView) view.findViewById(R.id.txt_music_toggle);
        txtMusicToggle.setTypeface(MainActivity.font);
        txtMusicToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GameActivity.isMuted) {
                    MainActivity.mServ.resumeMusic();
                    GameActivity.isMuted = false;
                } else {
                    MainActivity.mServ.pauseMusic();
                    GameActivity.isMuted = true;
                }
                String muteOrNot = GameActivity.isMuted ? "Turn Music ON" : "Turn Music OFF";
                txtMusicToggle.setText(muteOrNot);
            }
        });

        txtSoundsToggle = (TextView) view.findViewById(R.id.txt_sounds_toggle);
        txtSoundsToggle.setTypeface(MainActivity.font);
        txtSoundsToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.soundsEnabled = !GameActivity.soundsEnabled;
                String soundsOnOrOff = GameActivity.soundsEnabled ? "Turn Sounds OFF" : "Turn Sounds ON";
                txtSoundsToggle.setText(soundsOnOrOff);
            }
        });

        txtExit = (TextView) view.findViewById(R.id.txt_exit);
        txtExit.setTypeface(MainActivity.font);
        txtExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                GameDialogManager.exitDialog.show();
            }
        });

        return view;
    }

}