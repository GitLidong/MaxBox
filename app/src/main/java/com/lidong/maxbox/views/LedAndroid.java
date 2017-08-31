package com.lidong.maxbox.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ubuntu on 17-8-24.
 */

public class LedAndroid extends View{

    private int POINT_DISTANCE = 59;
    private int POINT_RADIUS;

    private Paint paintWhite,paintRed;
    private int x,y;

    private void initXY(){
        x = POINT_DISTANCE *7 ;
        y = POINT_DISTANCE *9;
    }

    private void init(){
        paintWhite = new Paint();
        paintWhite.setAntiAlias(true);
        paintWhite.setStyle(Paint.Style.FILL);
        paintWhite.setColor(Color.WHITE);
        paintWhite.setStrokeWidth(25);

        paintRed= new Paint();
        paintRed.setAntiAlias(true);
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(5);

        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
        initXY();
    }

    public LedAndroid(Context context) {
        super(context);
        init();
    }

    public LedAndroid(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSixLine(canvas);
        initXY();
        for(int i=0;i<4;i++ ){
            canvas.drawCircle(x - POINT_DISTANCE*3 ,y - POINT_DISTANCE,POINT_RADIUS,paintWhite);
            canvas.drawCircle(x - POINT_DISTANCE*3 ,y - POINT_DISTANCE,POINT_RADIUS,paintRed);

            canvas.drawCircle(x+POINT_DISTANCE *8 ,y - POINT_DISTANCE,POINT_RADIUS,paintWhite);
            canvas.drawCircle(x+POINT_DISTANCE *8 ,y - POINT_DISTANCE,POINT_RADIUS,paintRed);

            y += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x,y+POINT_DISTANCE*4,POINT_RADIUS,paintWhite);
            canvas.drawCircle(x,y+POINT_DISTANCE*4,POINT_RADIUS,paintRed);

            canvas.drawCircle(x+POINT_DISTANCE*4,y+POINT_DISTANCE*4,POINT_RADIUS,paintWhite);
            canvas.drawCircle(x+POINT_DISTANCE*4,y+POINT_DISTANCE*4,POINT_RADIUS,paintRed);

            canvas.drawCircle(x,y+POINT_DISTANCE*5,POINT_RADIUS,paintWhite);
            canvas.drawCircle(x,y+POINT_DISTANCE*5,POINT_RADIUS,paintRed);

            canvas.drawCircle(x+POINT_DISTANCE*4,y+POINT_DISTANCE*5,POINT_RADIUS,paintWhite);
            canvas.drawCircle(x+POINT_DISTANCE*4,y+POINT_DISTANCE*5,POINT_RADIUS,paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<6;i++ ){
            canvas.drawCircle(x,y+POINT_DISTANCE*8,POINT_RADIUS,paintWhite);
            canvas.drawCircle(x,y+POINT_DISTANCE*8,POINT_RADIUS,paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<4;i++){
            canvas.drawCircle(x+POINT_DISTANCE*2 ,y+POINT_DISTANCE*9,POINT_RADIUS,paintWhite);
            canvas.drawCircle(x+POINT_DISTANCE*2 ,y+POINT_DISTANCE*9,POINT_RADIUS,paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        canvas.drawCircle(x-POINT_DISTANCE*2,y+POINT_DISTANCE,POINT_RADIUS,paintWhite);
        canvas.drawCircle(x-POINT_DISTANCE*2,y+POINT_DISTANCE,POINT_RADIUS,paintRed);

        canvas.drawCircle(x+POINT_DISTANCE*7,y+POINT_DISTANCE,POINT_RADIUS,paintWhite);
        canvas.drawCircle(x+POINT_DISTANCE*7,y+POINT_DISTANCE,POINT_RADIUS,paintRed);

        canvas.drawCircle(x+POINT_DISTANCE*3 ,y+POINT_DISTANCE*10,POINT_RADIUS,paintWhite);
        canvas.drawCircle(x+POINT_DISTANCE*3 ,y+POINT_DISTANCE*10,POINT_RADIUS,paintRed);
    }

    private void drawSixLine(Canvas canvas){
        initXY();
        for (int i = 0; i < 6; i++) {
            canvas.drawCircle(x , y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x , y , POINT_RADIUS, paintRed);

            canvas.drawCircle(x, y + POINT_DISTANCE * 13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x, y + POINT_DISTANCE * 13, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++){
            canvas.drawCircle(x - POINT_DISTANCE*4 , y + POINT_DISTANCE*4 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x - POINT_DISTANCE*4 , y + POINT_DISTANCE*4 , POINT_RADIUS, paintRed);

            canvas.drawCircle(x + POINT_DISTANCE*9 , y + POINT_DISTANCE*4 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x + POINT_DISTANCE*9 , y + POINT_DISTANCE*4 , POINT_RADIUS, paintRed);
            y += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++){
            canvas.drawCircle(x - POINT_DISTANCE*3 , y + POINT_DISTANCE*3 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x - POINT_DISTANCE*3 , y + POINT_DISTANCE*3 , POINT_RADIUS, paintRed);

            canvas.drawCircle(x + POINT_DISTANCE*6 , y + POINT_DISTANCE*12 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x + POINT_DISTANCE*6 , y + POINT_DISTANCE*12 , POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
            y -= POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++){
            canvas.drawCircle(x - POINT_DISTANCE*3 , y + POINT_DISTANCE*10 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x - POINT_DISTANCE*3 , y + POINT_DISTANCE*10 , POINT_RADIUS, paintRed);

            canvas.drawCircle(x + POINT_DISTANCE*6 , y + POINT_DISTANCE , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x + POINT_DISTANCE*6 , y + POINT_DISTANCE , POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
            y += POINT_DISTANCE;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        POINT_DISTANCE = w/18;
        POINT_RADIUS = POINT_DISTANCE/2 - 5;
        Log.i("lidong","point_distance: "+POINT_DISTANCE);
    }
}
