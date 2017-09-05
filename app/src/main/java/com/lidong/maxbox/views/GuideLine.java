package com.lidong.maxbox.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lidong.maxbox.R;

/**
 * Created by ubuntu on 17-9-4.
 */

public class GuideLine extends View{
    private String TAG = "GuideLine";

    private Paint paintGuide;

    private float lineWidth;
    private float line1Height,line2Height;
    private float lineMinHeight;
    private float actionDownY;

    private float bmLeft,bmRight;
    private Bitmap bitmap;

    private void initData() {

        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.tool_ruler_ctrl);
        lineMinHeight = bitmap.getHeight()/2;
        line1Height = lineMinHeight;
        line2Height = lineMinHeight*4;

        paintGuide = new Paint();
        paintGuide.setAntiAlias(true);
        paintGuide.setColor(getResources().getColor(R.color.colorGuider));
        paintGuide.setStyle(Paint.Style.FILL);
        paintGuide.setStrokeWidth(5);
    }

    public GuideLine(Context context) {
        super(context);
        initData();
    }

    public GuideLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public GuideLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas,line1Height);
        drawLine(canvas,line2Height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        lineWidth = w;
        bmLeft = lineWidth/2 - bitmap.getWidth()/2;
        bmRight= lineWidth/2 +bitmap.getWidth()/2;
    }

    private void drawLine(Canvas canvas, float lineHeight) {
        canvas.drawLine(0,lineHeight,lineWidth,lineHeight,paintGuide);
        canvas.drawBitmap(bitmap,bmLeft,lineHeight-bitmap.getHeight()/2,new Paint());
    }

    public void setLine1Height(float line1Height) {
        this.line1Height = line1Height > lineMinHeight ? line1Height:lineMinHeight;
    }

    public void setLine2Height(float line2Height) {
        this.line2Height = line2Height > lineMinHeight ? line2Height:lineMinHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        actionDownY = event.getY();
        float distanceToline1 = Math.abs(actionDownY - line1Height);
        float distanceToline2 = Math.abs(actionDownY - line2Height);

        if (distanceToline1 <= distanceToline2 ) {
            setLine1Height(actionDownY);
        } else {
            setLine2Height(actionDownY);
        }
        invalidate();
        return true;
    }
}
