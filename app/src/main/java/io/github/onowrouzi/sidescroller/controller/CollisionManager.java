package io.github.onowrouzi.sidescroller.controller;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.GameFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.droppables.Droppable;
import io.github.onowrouzi.sidescroller.model.droppables.HealthDroppable;
import io.github.onowrouzi.sidescroller.model.droppables.ShurikenDroppable;
import io.github.onowrouzi.sidescroller.model.enemies.Enemy;
import io.github.onowrouzi.sidescroller.model.enemies.GroundEnemies.SpikyRoll;
import io.github.onowrouzi.sidescroller.model.projectiles.PlayerFireBall;
import io.github.onowrouzi.sidescroller.model.projectiles.Projectile;

public class CollisionManager {

    public void handleCollisions(GameFigure gf1, GameFigure gf2){
        if (gf1 instanceof Projectile)
            checkProjectileCollision((Projectile) gf1, gf2);
        else if (gf2 instanceof Projectile)
            checkProjectileCollision((Projectile) gf2, gf1);
        else if (gf1 instanceof Droppable && gf2 instanceof Player)
            checkPickups((Droppable) gf1, (Player) gf2);
        else if (gf2 instanceof Droppable && gf1 instanceof Player)
            checkPickups((Droppable) gf2, (Player) gf1);
        else if (gf1 instanceof Player && gf2 instanceof Enemy)
            checkPlayerDamage((Player) gf1, (Enemy) gf2);
        else if (gf2 instanceof Player && gf1 instanceof Enemy)
            checkPlayerDamage((Player) gf2, (Enemy) gf1);
    }

    public void checkProjectileCollision(Projectile p, GameFigure gf){
        if (gf instanceof Projectile) return;
        if (p instanceof PlayerFireBall && gf instanceof Enemy) {
            Enemy e = (Enemy) gf;
            e.state = e.dying;
        } else if (p.owner instanceof Player && gf instanceof Enemy && (!(gf instanceof SpikyRoll))) {
            Enemy e = (Enemy) gf;
            e.state = e.dying;
            p.state = p.dying;
        } else if (p.owner instanceof Enemy && gf instanceof Player){
            Player player = (Player) gf;
            player.hurt();
            p.state = p.dying;
        }
    }

    public void checkPickups(Droppable d, Player p){
        if (d instanceof HealthDroppable && p.health < 6) {
            p.health++;
            GameActivity.gameData.droppableFigures.remove(d);
        } else if (d instanceof ShurikenDroppable && p.bulletCount < 10) {
            p.bulletCount = 10;
            GameActivity.gameData.droppableFigures.remove(d);
        }
    }

    public void checkPlayerDamage(Player p, Enemy e){
        if (e instanceof SpikyRoll) {
            if (p.isJumpRight() || p.isJumpLeft()) p.bounceBack();
            p.hurt();
        } else if ((p.isMeleeLeft() && e.x < p.x) || (p.isMeleeRight() && e.x > p.x + p.width/2)) {
            e.state = e.dying;
        } else if (p.isJumpLeft() || p.isJumpRight()) {
            e.state = e.dying;
            p.bounceOff();
        } else p.hurt();
    }
}