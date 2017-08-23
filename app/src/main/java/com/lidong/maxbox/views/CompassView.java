package com.lidong.maxbox.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ubuntu on 17-8-23.
 */

public class CompassView extends android.support.v7.widget.AppCompatImageView {

    private float mAngle;
    private Drawable compass;


    public CompassView(Context context) {
        super(context);
        mAngle = 0.0f;
        compass = null;

    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAngle = 0.0f;
        compass = null;

    }

    public CompassView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAngle = 0.0f;
        compass = null;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (compass == null) {
            compass = getDrawable();
            int width = getWidth();
            int height = getHeight();
            compass.setBounds(0, 0, width, height);
        }
        canvas.save();
        canvas.rotate(mAngle, getWidth() / 2, getHeight() / 2);
        compass.draw(canvas);
        canvas.restore();
    }



    public void updateAngle(float angle) {
        mAngle = angle;
        invalidate();
    }
}
