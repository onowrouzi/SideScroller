package io.github.onowrouzi.sidescroller.model.droppables;

import android.content.Context;
import android.graphics.Bitmap;

import io.github.onowrouzi.sidescroller.R;

public class HealthDroppable extends Droppable {

    public HealthDroppable(float x, float y, int width, int height, Context context) {
        super(x, y, width, height);
        sprites = new Bitmap[1];
        sprites[0] = super.extractImage(context.getResources(), R.drawable.heart);
        sprites[0] = Bitmap.createScaledBitmap(sprites[0], width, height, false);
    }

}
