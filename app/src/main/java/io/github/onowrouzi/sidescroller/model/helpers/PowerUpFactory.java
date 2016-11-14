package io.github.onowrouzi.sidescroller.model.helpers;

import android.content.Context;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.PowerUps.Invinciblity;
import io.github.onowrouzi.sidescroller.model.PowerUps.PowerUp;
import io.github.onowrouzi.sidescroller.model.PowerUps.Shield;

public class PowerUpFactory {

    public static void generatePowerUp(Context context){
        int random = (int) (Math.random() * 1000);
        int origin = (int) (Math.random() * 100);
        int x = origin < 50 ? GameActivity.screenWidth*9/8 : -GameActivity.screenWidth/8;
        int y = GameActivity.groundLevel-GameActivity.screenHeight/10;
        PowerUp powerUp = null;

        if (random > 65 && random < 70) {
//            powerUp = new HealthDroppable(x, y, GameActivity.screenWidth/12, GameActivity.screenHeight/10, context);
        } else if (random == 75) {
            powerUp = new Shield(x, y, GameActivity.screenWidth/12, GameActivity.screenHeight/12, context);
        } else if (random == 10) {
            powerUp = new Invinciblity(x, y, GameActivity.screenWidth/18, GameActivity.screenHeight/18, context);
        }

        if (powerUp != null) GameActivity.gameData.powerUpFigures.add(powerUp);
    }
}