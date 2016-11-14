package io.github.onowrouzi.sidescroller.model.PowerUps;

import android.content.Context;
import android.graphics.Bitmap;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.ui.Score;

public class DoubleScore extends PowerUp {

    public DoubleScore(float x, float y, int width, int height, Context context) {
        super(x, y, width, height);
        sprites = new Bitmap[1];
        sprites[0] = super.extractImage(context.getResources(), R.drawable.times_two);
    }

    @Override
    public void handleCollision(MovableFigure mf) {
        Score.dblScoreTimer = 500;
        Score.increment = 20;
        GameActivity.gameData.powerUpFigures.remove(this);
    }
}
