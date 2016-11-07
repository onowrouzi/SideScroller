package io.github.onowrouzi.sidescroller.model.helpers;

import android.content.Context;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.droppables.Droppable;
import io.github.onowrouzi.sidescroller.model.droppables.FireBallDroppable;
import io.github.onowrouzi.sidescroller.model.droppables.HealthDroppable;
import io.github.onowrouzi.sidescroller.model.droppables.ShurikenDroppable;

public class DroppableFactory {

    public static void generateDroppable(float x, float y, Context context){
        int random = (int) (Math.random() * 100);
        Droppable droppable = null;

        if (random > 60 && random < 70) {
            droppable = new HealthDroppable(x, y, GameActivity.screenWidth/12, GameActivity.screenHeight/10, context);
        } else if (random > 70 && random < 80) {
            droppable = new FireBallDroppable(x, y, GameActivity.screenWidth/12, GameActivity.screenHeight/12, context);
        } else if (random > 10 && random < 30) {
            droppable = new ShurikenDroppable(x, y, GameActivity.screenWidth/18, GameActivity.screenHeight/18, context);
        }

        if (droppable != null) GameActivity.gameData.droppableFigures.add(droppable);
    }

}
