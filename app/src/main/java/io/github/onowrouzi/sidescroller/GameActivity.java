package io.github.onowrouzi.sidescroller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import io.github.onowrouzi.sidescroller.controller.GameThread;
import io.github.onowrouzi.sidescroller.model.GameData;

public class GameActivity extends Activity {

    public static GameThread animator;
    public static GameData gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        animator = new GameThread();
        gameData = new GameData();
        //gamePanel = new GamePanel();

//        JFrame game = new MainWindow();
//        game.setUndecorated(true);
//        game.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
//        game.setLocation(100,50);
//        game.setResizable(false);
//        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        game.setVisible(true);

        new Thread(animator).start();
    }
}
