package io.github.onowrouzi.sidescroller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.GameData;
import io.github.onowrouzi.sidescroller.view.GamePanel;

public class GameActivity extends Activity {

    public static GameData gameData;
    public static GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        RelativeLayout surface = (RelativeLayout) findViewById(R.id.surface);
        gameData = new GameData(getApplicationContext());
        gamePanel = new GamePanel(getApplicationContext());
        surface.addView(gamePanel);
    }

}
