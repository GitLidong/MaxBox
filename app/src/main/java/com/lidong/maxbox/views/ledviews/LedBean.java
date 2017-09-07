package com.lidong.maxbox.views.ledviews;

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
 * Created by ubuntu on 17-8-30.
 */

public class LedBean extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private int x,y;
    public int POINT_DISTANCE = 59;
    private int POINT_RADIUS;

    private static int STATE = 0;
    private boolean running = false;

    private Paint paintWhite,paintRed;

    private void initXY(){
        x = POINT_DISTANCE *9 ;
        y = POINT_DISTANCE *30 +POINT_DISTANCE/2;
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

    public LedBean(Context context) {
        super(context);
        init();
    }

    public LedBean(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LedBean(Context context, AttributeSet attrs, int defStyleAttr) {
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
            drawBean(STATE,canvas);
            getHolder().unlockCanvasAndPost(canvas);
            try {
                Thread.sleep(1000);
                STATE += 1;
                if(STATE >=29) {
                    STATE = 2;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawBean(int state,Canvas canvas){
        Log.i("LiDong","STATE: "+state);
        clearDraw(canvas);
        switch (state) {
            case 0:
                drawBean0(canvas);
                break;
            case 1:
                drawBean0(canvas);
                drawBean1(canvas);
                break;
            case 2:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean2(canvas);
                break;
            case 3:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean2(canvas);
                drawBean3(canvas);
                break;
            case 4:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean2(canvas);
                drawBean3(canvas);
                drawBean4(canvas);
                break;
            case 5:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean2(canvas);
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                break;
            case 6:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean2(canvas);
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean6_side(canvas);
                break;
            case 7:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean2(canvas);
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean6_side(canvas);
                drawBean7(canvas);
                break;
            case 8:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean2(canvas);
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean8(canvas);
                break;
            case 9:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean2(canvas);
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean9(canvas);
                break;
            case 10:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean2(canvas);
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean10(canvas);
                break;
            case 11:
                drawBean0(canvas);
                drawBean2(canvas);
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean10(canvas);
                drawBean11(canvas);
                break;
            case 12:
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean12(canvas);
                break;
            case 13:
                drawBean2(canvas);
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean13(canvas);
                break;
            case 14:
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean14(canvas);
                break;
            case 15:
                drawBean3(canvas);
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean15(canvas);
                break;
            case 16:
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean16(canvas);
                break;
            case 17:
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean17(canvas);
                break;
            case 18:
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean18(canvas);
                break;
            case 19:
                drawBean4(canvas);
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean19(canvas);
                break;
            case 20:
                drawBean6(canvas);
                drawBean20(canvas);
                break;
            case 21:
                drawBean5(canvas);
                drawBean6(canvas);
                drawBean21(canvas);
                break;
            case 22:
                drawBean6(canvas);
                drawBean22(canvas);
                break;
            case 23:
                drawBean6(canvas);
                drawBean23(canvas);
                break;
            case 24:
                drawBean6(canvas);
                drawBean24(canvas);
                break;
            case 25:
                drawBean25(canvas);
                break;
            case 26:
                drawBean26(canvas);
                break;
            case 27:
                drawBean0(canvas);
                drawBean27(canvas);
                break;
            case 28:
                drawBean0(canvas);
                drawBean1(canvas);
                drawBean28(canvas);
                break;
        }
    }

    private void drawBean0(Canvas canvas) {
        initXY();
        canvas.drawCircle(x , y , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y , POINT_RADIUS, paintRed);
    }

    private void drawBean1(Canvas canvas) {
        initXY();
        canvas.drawCircle(x , y - POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y - POINT_DISTANCE*5, POINT_RADIUS, paintRed);
    }

    private void drawBean2(Canvas canvas) {
        initXY();
        canvas.drawCircle(x , y - POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y - POINT_DISTANCE*10, POINT_RADIUS, paintRed);
    }

    private void drawBean3(Canvas canvas) {
        initXY();
        canvas.drawCircle(x , y - POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y - POINT_DISTANCE*15, POINT_RADIUS, paintRed);
    }

    private void drawBean4(Canvas canvas) {
        initXY();
        canvas.drawCircle(x , y - POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y - POINT_DISTANCE*20, POINT_RADIUS, paintRed);
    }

    private void drawBean5(Canvas canvas) {
        initXY();
        canvas.drawCircle(x , y - POINT_DISTANCE*25, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y - POINT_DISTANCE*25, POINT_RADIUS, paintRed);
    }

    private void drawBean6(Canvas canvas) {
        initXY();
        canvas.drawCircle(x , y - POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x , y - POINT_DISTANCE*30, POINT_RADIUS, paintRed);
    }

    private void drawBean6_side(Canvas canvas) {
        initXY();
        canvas.drawCircle(x -POINT_DISTANCE*5, y , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x -POINT_DISTANCE*5, y , POINT_RADIUS, paintRed);

        canvas.drawCircle(x +POINT_DISTANCE*5, y , POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*5, y , POINT_RADIUS, paintRed);
    }

    private void drawBean7(Canvas canvas) {
        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*4, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y , POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE, POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*2, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y , POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE, POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
        }
    }

    private void drawBean8(Canvas canvas) {
        initXY();
        for(int i=0;i<11;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y , POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE, POINT_RADIUS, paintRed);

            x+=POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<9;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*2, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }

    }

    private void drawBean9(Canvas canvas) {
        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y , POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE, POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE, POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*2, POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*2, POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*4, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*4, POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
        }
    }

    private void drawBean10(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y , POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*4, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*4, POINT_RADIUS, paintRed);

            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<9;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*2, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*2, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*3, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*3, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*6, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*6, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE*6, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE*6, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
        canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
    }

    private void drawBean11(Canvas canvas) {
        initXY();
        for(int i=0;i<13;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*4, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*6, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*6, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintRed);

            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*8, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
    }

    private void drawBean12(Canvas canvas) {
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y , POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*4, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*6, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*6, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*2, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*2, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<5;i++){
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*11, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*11, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
    }

    private void drawBean13(Canvas canvas) {
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*3, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*3, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*4, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*4, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*2, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*2, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*6, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*6, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<5;i++){
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*8, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*8, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*6, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*6, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }

        initXY();
        for(int i=0;i<4;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*11, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*11, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*11, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*11, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
    }

    private void drawBean14(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12 , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*12 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*12 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*6, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*6, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*11, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*11, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*11, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*11, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*3, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*3, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*14, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*14, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE*14, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE*14, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
        canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
    }

    private void drawBean15(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*14 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*14 , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*14 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*14 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*6, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*6, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*11, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*11, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean16(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*5, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*5, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*18 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*18 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*6, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*6, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*7, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*8, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*11, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*11, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*14, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*14, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean17(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*8, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*8, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*17 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*17 , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*17 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*17 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*10, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*10, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*11, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*11, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*9, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*9, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*14, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*14, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*18, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*18, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*18, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*18, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*19, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*19, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*19, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*19, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean18(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*19 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*19 , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*19 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*19 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*14, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*14, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*18, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*18, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*18, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*18, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*19, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*19, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*19, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*19, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*3, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*3, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*21, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*21, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE*21, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE*21, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintRed);
        canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintRed);
    }

    private void drawBean19(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*21 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*21 , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*21 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*21 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*14, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*14, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*18, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*18, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*19, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*19, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*19, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*19, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*22, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*22, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*23, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*23, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean20(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*12, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*13, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*13, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*14, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*14, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*15, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*18, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*18, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*19, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*19, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*21, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*21, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean21(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*16, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*25 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*25 , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*25 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*25 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*18, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*18, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*19, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*19, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*17, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*17, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*21, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*21, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*21, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*21, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*22, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*22, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*23, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*23, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*23, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*23, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*26, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*26, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*26, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*26, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean22(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27 , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*27 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*27 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*21, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*21, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*26, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*26, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*26, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*26, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*3, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*3, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE*29, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*4, y -POINT_DISTANCE*29, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*25, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*25, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
        canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
        canvas.drawCircle(x +POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
    }

    private void drawBean23(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29 , POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*29 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*29 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*21, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*21, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*25, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*25, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*26, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*26, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<6;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<4;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*31, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*31, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*31, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*31, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<3;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*32, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*32, POINT_RADIUS, paintRed);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*32, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x +POINT_DISTANCE*2, y -POINT_DISTANCE*32, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean24(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*20, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*33 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*33 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*21, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*21, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*32, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*32, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*22, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*23, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*31, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*31, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*26, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*26, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*25, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*25, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean25(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*23, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*23, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*36 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*36 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*24, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*24, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*35, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*35, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*25, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*25, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*26, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*26, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*33, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*33, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*34, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*34, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*31, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*31, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*32, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*32, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean26(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*25, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*38 , POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*38 , POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++){
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*26, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*26, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*37, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*4, y -POINT_DISTANCE*37, POINT_RADIUS, paintRed);

            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*29, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*29, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++){
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*35, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*35, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*36, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*36, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++){
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*31, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*31, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*32, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*32, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*33, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*33, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*34, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*34, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<2;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
            x+=POINT_DISTANCE;
        }
    }

    private void drawBean27(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*27, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*27, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<11;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*28, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*28, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<13;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*29, POINT_RADIUS, paintRed);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*6, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
    }

    private void drawBean28(Canvas canvas) {
        initXY();
        for(int i=0;i<5;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*29, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*2, y -POINT_DISTANCE*29, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
        }
        initXY();
        for(int i=0;i<9;i++) {
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintWhite);
            canvas.drawCircle(x -POINT_DISTANCE*5, y -POINT_DISTANCE*30, POINT_RADIUS, paintRed);
            x += POINT_DISTANCE;
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