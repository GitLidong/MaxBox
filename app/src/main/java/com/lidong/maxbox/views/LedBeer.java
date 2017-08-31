package com.lidong.maxbox.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ubuntu on 17-8-28.
 */

public class LedBeer extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private int x,y;
    public int POINT_DISTANCE = 59;
    private int POINT_RADIUS;

    private static int STATE = 0;
    private boolean running = false;

    private Paint paintWhite,paintRed;

    private void initXY(){
        x = POINT_DISTANCE *4 ;
        y = POINT_DISTANCE *12;
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

    public LedBeer(Context context) {
        super(context);
        init();
    }

    public LedBeer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LedBeer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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
        while (running) {
            SurfaceHolder holder = getHolder();
            Canvas canvas = holder.lockCanvas();
            drawBeer(STATE,canvas);
            getHolder().unlockCanvasAndPost(canvas);
            try {
                Thread.sleep(1000);
                STATE += 1;
                if(STATE >=3) {
                    STATE = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawBeer(int state,Canvas canvas){
        Log.i("LiDong","STATE: "+state);
        clearDraw(canvas);
        drawBeerBack(canvas);
        switch (state) {
            case 0:
                drawBeer1(canvas);
                break;
            case 1:
                drawBeer2(canvas);
                break;
            case 2:
                drawBeer3(canvas);
                break;
        }
    }

    private void drawBeerBack(Canvas canvas) {
        initXY();
        canvas.drawCircle(x , y , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*9, y , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*9, y , POINT_RADIUS, paintRed);


        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE, POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*8, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*8, y -POINT_DISTANCE, POINT_RADIUS, paintRed);
            y += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<6;i++){
            canvas.drawCircle(x +POINT_DISTANCE*2, y +POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y +POINT_DISTANCE, POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*2, y +POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y +POINT_DISTANCE*10, POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x +POINT_DISTANCE*9, y +POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*9, y +POINT_DISTANCE*3, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x +POINT_DISTANCE*11, y +POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*11, y +POINT_DISTANCE*4, POINT_RADIUS, paintRed);
            y += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x +POINT_DISTANCE*9, y +POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*9, y +POINT_DISTANCE*7, POINT_RADIUS, paintRed);
            y -= POINT_DISTANCE;
            x += POINT_DISTANCE;
        }
    }

    private void drawBeer1(Canvas canvas){
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x , y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x , y , POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*9, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*9, y , POINT_RADIUS, paintRed);

            y += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++){
            canvas.drawCircle(x +POINT_DISTANCE*2, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<5;i++){
            canvas.drawCircle(x +POINT_DISTANCE*3, y +POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*3, y +POINT_DISTANCE*2, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x +POINT_DISTANCE*7, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*7, y -POINT_DISTANCE, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<3;i++){
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE, POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*6, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*6, y -POINT_DISTANCE, POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
        }

        initXY();
        canvas.drawCircle(x +POINT_DISTANCE*4, y+POINT_DISTANCE*3 , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*4, y+POINT_DISTANCE*3 , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*2, y-POINT_DISTANCE*3 , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*2, y-POINT_DISTANCE*3 , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*3, y-POINT_DISTANCE*2 , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*3, y-POINT_DISTANCE*2 , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*6, y-POINT_DISTANCE*4 , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*6, y-POINT_DISTANCE*4 , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*7, y-POINT_DISTANCE*2 , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*7, y-POINT_DISTANCE*2 , POINT_RADIUS, paintRed);

    }

    private void drawBeer2(Canvas canvas){
        initXY();
        canvas.drawCircle(x , y , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y , POINT_RADIUS, paintRed);
        canvas.drawCircle(x +POINT_DISTANCE*9, y , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*9, y , POINT_RADIUS, paintRed);

        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x +POINT_DISTANCE*2, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*4, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*4, y , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<5;i++){
            canvas.drawCircle(x +POINT_DISTANCE*3, y +POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*3, y +POINT_DISTANCE*2, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE, POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*7, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*7, y -POINT_DISTANCE, POINT_RADIUS, paintRed);
        canvas.drawCircle(x +POINT_DISTANCE*7, y , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*7, y , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*4, POINT_RADIUS, paintRed);
        canvas.drawCircle(x +POINT_DISTANCE*6, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*6, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*4, y +POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*4, y +POINT_DISTANCE*3, POINT_RADIUS, paintRed);
        canvas.drawCircle(x +POINT_DISTANCE*6, y +POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*6, y +POINT_DISTANCE*3, POINT_RADIUS, paintRed);


    }

    private void drawBeer3(Canvas canvas){
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x +POINT_DISTANCE*2, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*4, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*4, y , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*6, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*6, y , POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*2, y-POINT_DISTANCE , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y-POINT_DISTANCE , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*4, y-POINT_DISTANCE , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*4, y-POINT_DISTANCE , POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*3, y-POINT_DISTANCE*2 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*3, y-POINT_DISTANCE*2 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        canvas.drawCircle(x , y +POINT_DISTANCE, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y +POINT_DISTANCE , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*3 , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*7, y -POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*7, y -POINT_DISTANCE*4 , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*9, y +POINT_DISTANCE , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*9, y +POINT_DISTANCE , POINT_RADIUS, paintRed);
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
