package io.github.onowrouzi.sidescroller.model;

import android.graphics.Bitmap;

import io.github.onowrouzi.sidescroller.model.states.FigureState;

public abstract class MovableFigure extends GameFigure implements CollisionBox {
        
    public Bitmap[] sprites;
    public int spriteState;
    public int health;
    public int immuneTimer;
    
    public FigureState state;
    public FigureState alive;
    public FigureState hurt;
    public FigureState dying;
    public FigureState done;
    
    public MovableFigure(float x, float y, int width, int height){
        super(x,y,width,height);
    }
    
    public abstract void update();
    
}
