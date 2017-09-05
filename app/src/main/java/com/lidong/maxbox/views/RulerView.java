package com.lidong.maxbox.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lidong.maxbox.R;

/**
 * Created by ubuntu on 17-9-4.
 */

public class RulerView extends View{

    private Paint paintCenter;
    private Paint paintScale;

    private  float fromY;
    private int centerX;
    private int pointDiameter;
    private int lineHeight;

    private void initData() {

        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);

        paintCenter = new Paint();
        paintCenter.setAntiAlias(true);
        paintCenter.setColor(getResources().getColor(R.color.colorSteelBlue));
        paintCenter.setStyle(Paint.Style.FILL);
        paintCenter.setStrokeWidth(20);

        paintScale = new Paint();
        paintScale.setAntiAlias(true);
        paintScale.setColor(getResources().getColor(R.color.colorSteelBlue));
        paintScale.setStyle(Paint.Style.FILL);
        paintScale.setStrokeWidth(5);
    }

    public RulerView(Context context) {
        super(context);
        initData();
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(centerX,fromY,pointDiameter,paintCenter);

        canvas.drawLine(centerX,fromY,centerX,lineHeight,paintCenter);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        pointDiameter = centerX/15;

        lineHeight = h;

        fromY = 2*pointDiameter;
    }
}
