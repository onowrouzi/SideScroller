package io.github.onowrouzi.sidescroller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import io.github.onowrouzi.sidescroller.model.HighScore;

public class ScoresActivity extends Activity {

    private List<HighScore> highScores;
    private ListView lstHighScores;
    private ArrayAdapter<HighScore> adapter;
    private TextView lblHighScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scores);

        lblHighScores = (TextView) findViewById(R.id.lbl_high_scores);
        lblHighScores.setTypeface(MainActivity.font);

        highScores = new ArrayList<>();
        lstHighScores = (ListView)findViewById(R.id.lstHighScores);
        getScores(); //temp for filling high score with dummy data
        adapter = new ArrayAdapter<HighScore>(getApplicationContext(), R.layout.score_list_item, R.id.text1, highScores){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                String name = highScores.get(position).getName();
                String score = highScores.get(position).getScore().toString();
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(R.id.text1);
                text1.setText(name);
                text1.setTextColor(Color.BLACK);
                text1.setTextSize(18);
                TextView text2 = (TextView) view.findViewById(R.id.text2);
                text2.setText(score);
                text2.setTextColor(Color.BLACK);
                text2.setTextSize(18);
                return view;
            }};
        lstHighScores.setAdapter(adapter);
    }

    private void getScores()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            highScores.add(new HighScore(entry.getKey(), (Integer)entry.getValue()));
        }
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore o1, HighScore o2) {
                return o2.getScore() - o1.getScore();
            }
        });
    }
}
