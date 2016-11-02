package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.projectiles.Projectile;

public class ActiveProjectile implements FigureState {

    public Projectile projectile;
    
    public ActiveProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public void update() {
        projectile.x += projectile.dx;
        projectile.y += projectile.dy;
        
        if (projectile.x > GameActivity.screenWidth || projectile.x < -10 ||
                projectile.y > GameActivity.screenHeight || projectile.y < -10) {
            projectile.state = projectile.dying;
        }
    }
    
}
