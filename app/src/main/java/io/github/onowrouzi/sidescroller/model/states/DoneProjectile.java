package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.Projectile;

public class DoneProjectile implements FigureState {

    public Projectile projectile;
    
    public DoneProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public void update() {
        if (projectile.isFriendly){
            GameActivity.gameData.friendFigures.remove(projectile);
        } else {
            GameActivity.gameData.enemyFigures.remove(projectile);
        }
    }
    
}
