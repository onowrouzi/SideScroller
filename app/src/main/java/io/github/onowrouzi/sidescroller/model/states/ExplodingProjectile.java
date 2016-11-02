package io.github.onowrouzi.sidescroller.model.states;

import io.github.onowrouzi.sidescroller.model.projectiles.Projectile;

public class ExplodingProjectile implements FigureState {

    public Projectile projectile;
    
    public ExplodingProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public void update() {
        if (projectile.size < Projectile.MAX_EXPLOSION_SIZE){
            projectile.size += 10;
        } else {
            projectile.state = projectile.done;
        }
    }
    
}
