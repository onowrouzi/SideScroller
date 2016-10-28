package io.github.onowrouzi.sidescroller;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.onowrouzi.sidescroller.model.HighScore;

public class ScoresActivity extends Activity {

    private List<HighScore> highScores;
    private ListView lstHighScores;
    private ArrayAdapter<HighScore> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        highScores = new ArrayList<>();
        lstHighScores = (ListView)findViewById(R.id.lstHighScores);
        addDummyData(); //temp for filling high score with dummy data
        adapter = new ArrayAdapter<HighScore>(getApplicationContext(), android.R.layout.simple_list_item_1, highScores){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                text.setTextSize(18);
                return view;
            }};
        lstHighScores.setAdapter(adapter);
    }

    private void addDummyData()
    {
        highScores.add(new HighScore("user123456", 2200));
        highScores.add(new HighScore("user245", 1800));
        highScores.add(new HighScore("user111", 1799));
        highScores.add(new HighScore("user544", 199));
        highScores.add(new HighScore("user666", 77));
        highScores.add(new HighScore("user121", 0));
        highScores.add(new HighScore("user1112543534543454455545454455445", 199));
    }
}
