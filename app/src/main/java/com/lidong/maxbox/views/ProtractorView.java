package com.lidong.maxbox.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidong.maxbox.R;

import java.text.AttributedCharacterIterator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by ubuntu on 17-9-1.
 */

public class ProtractorView extends View {
    //定义画笔
    private Paint mpaint1=null;
    private Paint mpaint2=null;

    //定义中心点位置及图片
    private int centerX;
    private int centerY;
    private Bitmap centerPointImg=null;
    private float centerWidth;
    private float radiusMin;
    private float radiusMax;

    //定义量角器的指示点1,2位置及图片
    private float mPoint1X;
    private float mPoint1Y;
    private Bitmap pointImg=null;
    private float pointWidth;

    private float mPoint2X;
    private float mPoint2Y;
    RectF oval=null;

    private float point1Rotation;
    private float point2Rotation;
    private float touchRotation;
    private TextView textView;


    private Rect bitmapRect,positionRect;

    public ProtractorView(Context context, AttributeSet attrs) {
        super(context,attrs);
        initData();
    }

    private void initData(){

        //textView=(TextView) findViewById(R.id.degreeView);

        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);

        //初始化画笔,绘制红色直线
        mpaint1=new Paint();
        mpaint1.setAntiAlias(true);
        mpaint1.setColor(0xffff0000);
        mpaint1.setStyle(Paint.Style.STROKE);
        mpaint1.setStrokeWidth(8);

        centerPointImg= BitmapFactory.decodeResource(getResources(), R.drawable.tool_protractor_bg0);
        centerWidth=centerPointImg.getWidth();

        centerX=getRight()-(int) centerWidth/2;
        centerY=getBottom()/2-(int) centerWidth/2;

        pointImg=BitmapFactory.decodeResource(getResources(), R.drawable.tool_protractro_paint);
        pointWidth=pointImg.getWidth();

        //绘制绿色圆弧
        mpaint2=new Paint();
        mpaint2.setAntiAlias(true);
        mpaint2.setColor(0xff00ff00);
        //mpaint2.setStyle(Paint.Style.FILL);
        mpaint2.setStrokeWidth(10);
        mpaint2.setStyle(Paint.Style.STROKE);

        radiusMax=dip2px(230);
        //RadiusMin=29*radiusMax/48;
        radiusMin=(float)0.59*radiusMax;

        point1Rotation=DegreeToRadian(120);
        point2Rotation=DegreeToRadian(240);

    }

    private float RadianToDegree(float radian){
        return  (float)(radian*180/Math.PI);
    }

    private  float DegreeToRadian(float degree){
        return (float)(degree*Math.PI/180);
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private float RotToXLoc(float pointRotation,int scale){
        float x=0;
        x=(float) (getRight()+scale*radiusMin*cos(pointRotation));
        return x;
    }

    private float RotToYLoc(float pointRotation,int scale){
        float y=0;
        y=(float)(getBottom()/2+scale*radiusMin*sin(pointRotation));
        return y;
    }

    private float LocToRot(PointF pointF){
        float deltaX=Math.abs(pointF.x-getRight());
        float deltaY=pointF.y-getBottom()/2;
        float delta=(float)Math.sqrt(deltaX*deltaX+deltaY*deltaY);
        float rot=(float)Math.acos(deltaY/delta);

        return rot;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        //绘制直线代码
//        canvas.drawLine(getRight(),getBottom()/2,RotToXLoc(point1Rotation,10),
//                RotToYLoc(point1Rotation,10),mpaint1);
//        canvas.drawLine(getRight(),getBottom()/2,RotToXLoc(point2Rotation,10),
//                RotToYLoc(point2Rotation,10),mpaint1);

        //绘制虚线代码
        PathEffect effects=new DashPathEffect(new float[]{4,4,4,4},0);
        Path path=new Path();
        path.moveTo(getRight(),getBottom()/2);
        path.lineTo(RotToXLoc(point1Rotation,10), RotToYLoc(point1Rotation,10));
        path.moveTo(getRight(),getBottom()/2);
        path.lineTo(RotToXLoc(point2Rotation,10), RotToYLoc(point2Rotation,10));
        mpaint1.setPathEffect(effects);
        canvas.drawPath(path,mpaint1);

        //绘制位图代码
        canvas.drawBitmap(centerPointImg, getRight()-centerWidth/2, getBottom()/2-centerWidth/2, mpaint1);
        canvas.drawBitmap(centerPointImg,RotToXLoc(point1Rotation,1)-centerWidth/2,
                RotToYLoc(point1Rotation,1)-centerWidth/2,mpaint1);
        canvas.drawBitmap(centerPointImg,RotToXLoc(point2Rotation,1)-centerWidth/2,
                RotToYLoc(point2Rotation,1)-centerWidth/2,mpaint1);

        //动态绘制圆弧
        RectF oval=new RectF();
        oval.left=getRight()-radiusMax;
        oval.top=getBottom()/2-radiusMax;
        oval.right=getRight()+radiusMax;
        oval.bottom=getBottom()/2+radiusMax;

        //mpaint3负责显示度数的绘制
        Paint mpaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint3.setStrokeWidth(80);
        mpaint3.setTextSize(80);
        mpaint3.setColor(Color.BLACK);
        mpaint3.setTypeface(Typeface.DEFAULT_BOLD);
        mpaint3.setTextAlign(Paint.Align.LEFT);


        float baseX=120;
        float baseY=400;


        if (point1Rotation<=point2Rotation){
            canvas.drawArc(oval,RadianToDegree(point1Rotation),
                    RadianToDegree(point2Rotation-point1Rotation),false,mpaint2);
            String textString=String.valueOf(((int)(10*RadianToDegree(point2Rotation-point1Rotation)))/10.0)+"°";

            //实现数据的旋转
            canvas.rotate(-90, baseX,baseY);
            canvas.drawText(textString,baseX,baseY,mpaint3);
            canvas.rotate(90, baseX,baseY);

        }else{
            canvas.drawArc(oval,RadianToDegree(point2Rotation),
                    RadianToDegree(point1Rotation-point2Rotation),false,mpaint2);
        }
        canvas.restore();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        PointF pointF=new PointF(event.getX(),event.getY());
        touchRotation=LocToRot(pointF);
        //判断touchRotation距离哪个ratation近，则将该rotation赋值为touchRotation;
        float delta1=(float) Math.abs(touchRotation+Math.PI/2-point1Rotation);
        float delta2=(float)Math.abs(touchRotation+Math.PI/2-point2Rotation);
        if (delta1<=delta2){
            point1Rotation=(float) (touchRotation+Math.PI/2);
        }else{
            point2Rotation=(float) (touchRotation+Math.PI/2);
        }
        invalidate();
        return true;
    }

}
