package io.github.onowrouzi.sidescroller.model.droppables;

import android.content.Context;
import android.graphics.Bitmap;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.R;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;

public class FireBallDroppable extends Droppable {
    public FireBallDroppable(float x, float y, int width, int height, Context context) {
        super(x, y, width, height);

        sprites = new Bitmap[1];
        sprites[0] = super.extractImage(context.getResources(), R.drawable.fire);
    }

    @Override
    public void handleCollision(MovableFigure mf){
         Player p = (Player) mf;
         if (p.fireBallCount < 5) {
             p.fireBallCount++;
             GameActivity.gameData.droppableFigures.remove(this);
         }
    }
}
