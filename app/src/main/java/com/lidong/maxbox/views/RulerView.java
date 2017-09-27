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
    private Paint paintCm;

    private float fromY;
    private float cmLongFromX,cmShortFromX;
    private float cmFromY;
    private float toX;

    private float inchLongToX,inchShortToX;
    private float inchFromX;

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

        paintCm = new Paint();
        paintCm.setAntiAlias(true);
        paintCm.setColor(getResources().getColor(R.color.colorSteelBlue));
        paintCm.setStyle(Paint.Style.FILL);
        paintCm.setStrokeWidth(2);
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

        for(int i=0;i<10;i++){
            canvas.drawLine(cmLongFromX,cmFromY+i*200,toX,cmFromY+i*200,paintCm);
            canvas.drawText(String.valueOf(i+1),cmLongFromX-pointDiameter,cmFromY+i*200,paintCm);

            canvas.drawLine(inchFromX,cmFromY+i*508,inchLongToX,cmFromY+i*508,paintCm);
            canvas.drawText(String.valueOf(i+1),inchLongToX+pointDiameter,cmFromY+i*508,paintCm);

            for(int j=1;j<=4;j++){
                canvas.drawLine(cmShortFromX,cmFromY+i*200+j*40,toX,cmFromY+i*200+j*40,paintCm);
                canvas.drawLine(inchFromX,(float) (cmFromY+i*508+j*101.6),
                        inchShortToX,(float) (cmFromY+i*508+j*101.6),paintCm);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        pointDiameter = centerX/15;

        lineHeight = h;

        fromY = 2*pointDiameter;
        cmFromY = 3*pointDiameter;

        cmLongFromX = w - 6*pointDiameter;
        cmShortFromX = w - 4*pointDiameter;
        toX = w - 2*pointDiameter;

        inchFromX = 2*pointDiameter;
        inchLongToX = 6*pointDiameter;
        inchShortToX = 4*pointDiameter;
    }
}
