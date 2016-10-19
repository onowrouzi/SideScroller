package io.github.onowrouzi.sidescroller.model.enemies;

import io.github.onowrouzi.sidescroller.model.MovableFigure;
import io.github.onowrouzi.sidescroller.model.Travel;

public abstract class Enemy extends MovableFigure implements Travel {
    
    public Enemy (float x, float y, int width, int height) {
        super(x,y,width,height);
    }

}
