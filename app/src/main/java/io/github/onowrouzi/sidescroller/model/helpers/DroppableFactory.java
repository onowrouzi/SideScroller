package io.github.onowrouzi.sidescroller.model.helpers;

import android.content.Context;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.droppables.Droppable;
import io.github.onowrouzi.sidescroller.model.droppables.HealthDroppable;
import io.github.onowrouzi.sidescroller.model.droppables.ShurikenDroppable;

public class DroppableFactory {

    public static void generateDroppable(float x, float y, Context context){

        int random = (int) (Math.random() * 100);

        Droppable droppable = null;
        if ((random > 30 && random < 50) || GameActivity.gameData.enemyFigures.size() < 4) {
//            int randomType = (int) (Math.random()*100);
            droppable = new HealthDroppable(x, y, GameActivity.screenWidth/12, GameActivity.screenHeight/12, context);
        } else if (random > 60 && random < 100) {
//            int randomType = (int) (Math.random() * 100);
            droppable = new ShurikenDroppable(x, y, GameActivity.screenWidth/15, GameActivity.screenHeight/15, context);
        }

        if (droppable != null) GameActivity.gameData.droppableFigures.add(droppable);
    }

}
