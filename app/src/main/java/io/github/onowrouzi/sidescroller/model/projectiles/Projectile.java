package io.github.onowrouzi.sidescroller.model.projectiles;

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.GameActivity;
import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Player;
import io.github.onowrouzi.sidescroller.model.states.ActiveProjectile;
import io.github.onowrouzi.sidescroller.model.states.DoneProjectile;
import io.github.onowrouzi.sidescroller.model.states.ExplodingProjectile;

public abstract class Projectile extends MovableFigure {

    public int size = GameActivity.screenWidth /32;
    public static final int MAX_EXPLOSION_SIZE = 30;
    public float dx; // displacement at each frame
    public float dy; // displacement at each frame
    public boolean isFriendly;
    public MovableFigure owner;
    public int streamId;

    public PointF target;

    private static final int UNIT_TRAVEL_DISTANCE = GameActivity.screenWidth/64; // per frame move
    
    public Projectile(float sx, float sy, float tx, float ty, MovableFigure owner, int streamId) {
        
        super(sx, sy, 10, 10);

        this.owner = owner;
        this.streamId = streamId;
        paint = new Paint();
        
        double angle = Math.atan2(Math.abs(ty - sy), Math.abs(tx - sx));
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));

        if (tx > sx && ty < sy) { // target is upper-right side
            dy = -dy; // dx > 0, dx < 0
        } else if (tx < sx && ty < sy) { // target is upper-left side
            dx = -dx;
            dy = -dy;
        } else if (tx < sx && ty > sy) { // target is lower-left side
            dx = -dx;
        } 
        
        if (this.owner instanceof Player) {
            isFriendly = true;
        } else {
            isFriendly = false;
        }
        
        alive = new ActiveProjectile(this);
        dying = new ExplodingProjectile(this);
        done = new DoneProjectile(this);
        
        state = alive;
    }

    @Override
    public void update() {
        state.update();
    }
    
    @Override
    public RectF getCollisionBox() {
        return new RectF(super.x-size/2,super.y-size/2,super.x+((size)*0.9f),super.y+((size)*0.9f));
    }

}
