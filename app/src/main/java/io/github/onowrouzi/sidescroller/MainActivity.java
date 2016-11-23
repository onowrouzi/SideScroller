package io.github.onowrouzi.sidescroller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Size;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.onowrouzi.sidescroller.model.helpers.SizeManager;

public class MainActivity extends Activity {

    Button btnStart, btnExit, btnScores;
    TextView txtLogo;
    ImageView imgLogo;
    public static Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        SizeManager sm = new SizeManager(this);

        txtLogo = (TextView) findViewById(R.id.txt_logo);
        imgLogo = (ImageView) findViewById(R.id.image_logo);
        ViewGroup.LayoutParams imgLP = imgLogo.getLayoutParams();
        imgLP.width = sm.getX();
        imgLP.height = sm.getY()/2;
        imgLogo.setLayoutParams(imgLP);

        btnStart = (Button) findViewById(R.id.btn_start);
        ViewGroup.LayoutParams startLP = btnStart.getLayoutParams();
        startLP.width = sm.getX();
        startLP.height = sm.getY()/10;
        btnStart.setLayoutParams(startLP);
        btnExit = (Button) findViewById(R.id.btn_exit);
        ViewGroup.LayoutParams exitLP = btnExit.getLayoutParams();
        exitLP.width = sm.getX();
        exitLP.height = sm.getY()/10;
        btnExit.setLayoutParams(exitLP);
        btnScores = (Button) findViewById(R.id.btn_scores);
        ViewGroup.LayoutParams scoreLP = btnScores.getLayoutParams();
        scoreLP.width = sm.getX();
        scoreLP.height = sm.getY()/10;
        btnScores.setLayoutParams(scoreLP);

        font = Typeface.createFromAsset(getAssets(), "shanghai.ttf");
        font = Typeface.create(font, Typeface.BOLD);
        txtLogo.setTypeface(font);
        btnStart.setTypeface(font);
        btnExit.setTypeface(font);
        btnScores.setTypeface(font);

        txtLogo.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        btnStart.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        btnScores.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        btnExit.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

        btnScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ScoresActivity.class);
                startActivity(i);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                finishAffinity();
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });

        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);
        doBindService();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this, MusicService.class));
    }

    //MUSIC SERVICE LOGIC
    private boolean mIsBound = false;
    public static MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
}
