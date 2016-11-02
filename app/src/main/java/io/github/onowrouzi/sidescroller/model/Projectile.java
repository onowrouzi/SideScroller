package io.github.onowrouzi.sidescroller.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import io.github.onowrouzi.sidescroller.model.states.ActiveProjectile;
import io.github.onowrouzi.sidescroller.model.states.DoneProjectile;
import io.github.onowrouzi.sidescroller.model.states.ExplodingProjectile;

public class Projectile extends MovableFigure {

    public int size = 10;
    public static final int MAX_EXPLOSION_SIZE = 30;
    public float dx; // displacement at each frame
    public float dy; // displacement at each frame
    public boolean isFriendly;
    public MovableFigure owner;
    
    public int primaryColor, secondaryColor;
    public PointF target;

    private static final int UNIT_TRAVEL_DISTANCE = 15; // per frame move
    
    public Projectile(float sx, float sy, float tx, float ty,
                      int primaryColor, int secondaryColor, MovableFigure owner) {
        
        super(sx, sy, 10, 10);
        
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.owner = owner;
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
    public void render(Canvas c) {
        paint.setColor(primaryColor);
        c.drawCircle(super.x-size, super.y-size, size*2, paint);
        paint.setColor(secondaryColor);
        c.drawCircle(super.x-size/2, super.y-size/2, size, paint);
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
