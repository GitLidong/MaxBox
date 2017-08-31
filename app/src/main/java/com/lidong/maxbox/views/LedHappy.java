package com.lidong.maxbox.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ubuntu on 17-8-24.
 */

public class LedHappy extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    public static int POINT_DISTANCE = 59;
    private int POINT_RADIUS;
    private int STATE = 0;

    private Paint paintWhite,paintRed;
    private int x,y;

    private boolean running = false;

    private void initXY(){
        x = POINT_DISTANCE *7 ;
        y = POINT_DISTANCE *9;
    }

    public LedHappy(Context context) {
        super(context);
       init();
    }

    public LedHappy(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LedHappy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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

        getHolder().addCallback(this);
        //设置背景透明
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        //----
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
        initXY();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("lidong","surfaceCreated");
        running = true;
        new Thread(this).start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while (running ) {
            try{
                canvas = getHolder().lockCanvas();
                drawHappy(STATE,canvas);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                getHolder().unlockCanvasAndPost(canvas);
            }
            try {
                STATE ++;
                if(STATE >= 3){
                    STATE =0;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawHappy(int state,Canvas canvas){
        Log.i("LiDong","STATE: "+state);
        clearDraw(canvas);
        drawSixLine(canvas);
        if(state == 0){
            drawSmile1(canvas);
        }
        else if(state == 1){
            drawSmile2(canvas);
        }
        else if(state == 2){
            drawSmile3(canvas);
        }
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
        initXY();
        for(int i=0;i<4;i++){
            canvas.drawCircle(x+POINT_DISTANCE, y + POINT_DISTANCE * 10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x+POINT_DISTANCE, y + POINT_DISTANCE * 10, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
    }

    private void drawSmile1(Canvas canvas){
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x, y + POINT_DISTANCE * 8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x, y + POINT_DISTANCE * 8, POINT_RADIUS, paintRed);

            canvas.drawCircle(x + POINT_DISTANCE*5 , y + POINT_DISTANCE * 8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x + POINT_DISTANCE*5 , y + POINT_DISTANCE * 8, POINT_RADIUS, paintRed);
            y += POINT_DISTANCE;
        }
        initXY();
        canvas.drawCircle(x , y + POINT_DISTANCE * 4, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y + POINT_DISTANCE * 4, POINT_RADIUS, paintRed);
        canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintRed);
        canvas.drawCircle(x + POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x + POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintRed);

        canvas.drawCircle(x + POINT_DISTANCE*5, y + POINT_DISTANCE * 4, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x + POINT_DISTANCE*5, y + POINT_DISTANCE * 4, POINT_RADIUS, paintRed);
        canvas.drawCircle(x + POINT_DISTANCE*4, y + POINT_DISTANCE * 5, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x + POINT_DISTANCE*4, y + POINT_DISTANCE * 5, POINT_RADIUS, paintRed);
        canvas.drawCircle(x + POINT_DISTANCE*6, y + POINT_DISTANCE * 5, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x + POINT_DISTANCE*6, y + POINT_DISTANCE * 5, POINT_RADIUS, paintRed);
    }

    private void drawSmile2(Canvas canvas){
        initXY();
        for(int i=0;i<3;i++){
            canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintRed);

            canvas.drawCircle(x + POINT_DISTANCE*4, y + POINT_DISTANCE * 5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x + POINT_DISTANCE*4, y + POINT_DISTANCE * 5, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 8, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
            y += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x + POINT_DISTANCE*6, y + POINT_DISTANCE * 8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x + POINT_DISTANCE*6, y + POINT_DISTANCE * 8, POINT_RADIUS, paintRed);
            x -=POINT_DISTANCE;
            y += POINT_DISTANCE;
        }
    }

    private void drawSmile3(Canvas canvas){
        initXY();
        canvas.drawCircle(x , y + POINT_DISTANCE * 4, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y + POINT_DISTANCE * 4, POINT_RADIUS, paintRed);
        canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintRed);
        canvas.drawCircle(x + POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x + POINT_DISTANCE, y + POINT_DISTANCE * 5, POINT_RADIUS, paintRed);
        initXY();
        for(int i=0;i<3;i++){
            canvas.drawCircle(x + POINT_DISTANCE*4, y + POINT_DISTANCE * 5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x + POINT_DISTANCE*4, y + POINT_DISTANCE * 5, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x - POINT_DISTANCE, y + POINT_DISTANCE * 8, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
            y += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x + POINT_DISTANCE*5 , y + POINT_DISTANCE * 8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x + POINT_DISTANCE*5 , y + POINT_DISTANCE * 8, POINT_RADIUS, paintRed);
            y += POINT_DISTANCE;
        }

    }

    private void clearDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.SRC);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        POINT_DISTANCE = w/18;
        POINT_RADIUS = POINT_DISTANCE/2 - 5;
        Log.i("lidong","point_distance: "+POINT_DISTANCE);
    }
}
