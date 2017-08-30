package com.lidong.maxbox.views;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.WindowManager;

import com.lidong.maxbox.R;

/**
 * Created by ubuntu on 17-8-24.
 */

public class LevelView extends View {
    Paint paint = new Paint();
    //定义水平仪表盘图片
    Bitmap LevelBack;
    //定义水平仪气泡图片
    Bitmap LevelBubble;


    //定义气泡的x，y坐标
    // 定义水平仪中气泡 的X、Y座标
    int ballX, ballY;

    // 定义气泡位于中间时（水平仪完全水平），气泡的X、Y座标
    int cx, cy;
    // 定义水平仪大圆盘中心座标X、Y
    int backCx=dip2px(175);
    int backCy=dip2px(175);
    // 定义灵敏度，即水平仪能处理的最大倾斜角，超过该角度，气泡将直接在位于边界。
    int SENSITIVITY = 80;

    //气泡边界取值：
    //1.当气泡位于中心点时，它到上下左右的边界距离相等，为下值。
    int level_bound=(int)(backCx*0.6);
    //2.当气泡不在中心点时，以当前位置为水平位置计算边界
    int xMax,xMin;
    int yMax,yMin;

    public LevelView(Context context,AttributeSet attrs)
    {
        super(context,attrs);
        //init(attrs,0);
        initBitmap();
        initLocation();
    }
    private void initBitmap(){
        //LevelBack=BitmapFactory.decodeResource(getResources(),R.drawable.tools_level_bg_circle_all);
        LevelBubble=BitmapFactory.decodeResource(getResources(),R.drawable.tools_level_icon_circle);
    }

    public void initLocation(){
        // 计算出　水平仪完全水平时　气泡位置　　左上角为原点
        //cx = backCx+(LevelBack.getWidth() - LevelBubble.getWidth()) / 2;
        //cy = backCy+(LevelBack.getHeight() - LevelBubble.getHeight()) / 2;
        cx =backCx-LevelBubble.getWidth() / 2;
        cy =backCy-LevelBubble.getHeight() / 2;
        ballX=cx;
        ballY=cy;
        xMax=cx+level_bound;
        xMin=cx-level_bound;
        yMax=cy+level_bound;
        yMin=cy-level_bound;
    }
    public void ResetZeroLevel(){
        ballX=cx;
        ballY=cy;
    }
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //canvas.drawColor(Color.WHITE);
        //paint.setColor(Color.BLUE);
        //paint.setStyle(Paint.Style.STROKE);

        //canvas.drawRect(0, 0, 650, 650, paint);
        // 绘制水平仪大圆盘图片
        //canvas.drawBitmap(LevelBack, cx,cy, paint);
        // 根据气泡坐标绘制气泡
        canvas.drawBitmap(LevelBubble, ballX, ballY, paint);
        //paint.setColor(Color.GRAY);
        //RectF oval = new RectF(backCx+LevelBack.getWidth()/2-10,backCy+LevelBack.getHeight()/2-10,backCx+LevelBack.getWidth()/2+10,backCy+LevelBack.getHeight()/2+10);
        //canvas.drawOval(oval, paint);

    }

    public void onChangeXY(double zAngle,double yAngle,double xAngle){
        // 定义气泡当前位置X Y坐标值
        int x, y;
        x = cx;
        y = cy;

        // 如果沿x轴的倾斜角　　在最大角度之内则计算出其相应坐标值
        if (Math.abs(xAngle) <= SENSITIVITY) {
            // 根据与x轴的倾斜角度计算X座标的变化值（倾斜角度越大，X座标变化越大）
            //int deltaX = (int) (cx * xAngle / SENSITIVITY);
            int deltaX = (int) (level_bound * xAngle / SENSITIVITY);
            x += deltaX;
        }
        // 如果沿x轴的倾斜角已经大于MAX_ANGLE，气泡应到最左边
        else if (xAngle > SENSITIVITY) {
            x = xMax;
        }
        // 如果与x轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最右边
        else {
            x = xMin;
        }
        // 如果沿Y轴的倾斜角还在最大角度之内
        if (Math.abs(yAngle) <= SENSITIVITY) {
            // 根据沿Y轴的倾斜角度计算Y座标的变化值（倾斜角度越大，Y座标变化越大）
            //int deltaY = (int) (cy * yAngle / SENSITIVITY);
            int deltaY = (int) (level_bound * yAngle / SENSITIVITY);
            y += deltaY;
        }
        // 如果沿Y轴的倾斜角已经大于MAX_ANGLE，气泡应到最下边
        else if (yAngle > SENSITIVITY) {
            y = yMax;
        }
        // 如果沿Y轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最右边
        else {
            y = yMin;
        }

        // 如果计算出来的X、Y座标还位于水平仪的仪表盘内，更新水平仪的气泡座标
        if (isContain(x, y)) {
            ballX = x;
            ballY = y;
        } else {
            // 有待后续继续完成
        }
        //重绘界面
        invalidate();
    }
    // 计算x、y点的气泡是否处于水平仪的大圆盘内
    private boolean isContain(int x, int y) {
        // 计算气泡的圆心座标X、Y
        int ballCx = x + LevelBubble.getWidth() / 2;
        int ballCy = y + LevelBubble.getWidth() / 2;

        // 计算气泡的圆心与水平仪大圆盘中中心之间的距离。
        double distance = Math.sqrt((ballCx - backCx) * (ballCx - backCx)
               + (ballCy - backCy) * (ballCy - backCy));
        // 若两个圆心的距离小于它们的半径差，即可认为处于该点的气泡依然位于仪表盘内
        if (distance < level_bound) {
            return true;
        } else {
            return false;
        }


    }
    /*
    public LevelView(Context context,AttributeSet attrs,int defStyle)
    {
        super(context,attrs,defStyle);
        init(attrs,defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LevelView, defStyle, 0);

        mBubbleRuleColor = a.getColor(R.styleable.LevelView_bubbleRuleColor, mBubbleRuleColor);

        mBubbleColor = a.getColor(R.styleable.LevelView_bubbleColor, mBubbleColor);
        mLimitColor = a.getColor(R.styleable.LevelView_limitColor, mLimitColor);

        mHorizontalColor = a.getColor(R.styleable.LevelView_horizontalColor, mHorizontalColor);


        mLimitRadius = a.getDimension(R.styleable.LevelView_limitRadius, mLimitRadius);
        mBubbleRadius = a.getDimension(R.styleable.LevelView_bubbleRadius, mBubbleRadius);
        mLimitCircleWidth = a.getDimension(R.styleable.LevelView_limitCircleWidth, mLimitCircleWidth);

        mBubbleRuleWidth = a.getDimension(R.styleable.LevelView_bubbleRuleWidth, mBubbleRuleWidth);

        mBubbleRuleRadius = a.getDimension(R.styleable.LevelView_bubbleRuleRadius, mBubbleRuleRadius);


        a.recycle();


        mBubblePaint = new Paint();

        mBubblePaint.setColor(mBubbleColor);
        mBubblePaint.setStyle(Paint.Style.FILL);
        mBubblePaint.setAntiAlias(true);

        mLimitPaint = new Paint();

        mLimitPaint.setStyle(Paint.Style.STROKE);
        mLimitPaint.setColor(mLimitColor);
        mLimitPaint.setStrokeWidth(mLimitCircleWidth);
        //抗锯齿
        mLimitPaint.setAntiAlias(true);

        mBubbleRulePaint = new Paint();
        mBubbleRulePaint.setColor(mBubbleRuleColor);
        mBubbleRulePaint.setStyle(Paint.Style.STROKE);
        mBubbleRulePaint.setStrokeWidth(mBubbleRuleWidth);
        mBubbleRulePaint.setAntiAlias(true);

        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        calculateCenter(widthMeasureSpec, heightMeasureSpec);
    }

    private void calculateCenter(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.UNSPECIFIED);

        int height = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.UNSPECIFIED);

        int center = Math.min(width, height) / 2;

        centerPnt.set(center, center);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean isCenter = isCenter(bubblePoint);
        int limitCircleColor = isCenter ? mHorizontalColor : mLimitColor;
        int bubbleColor = isCenter ? mHorizontalColor : mBubbleColor;

        //水平时振动
        if(isCenter){
            vibrator.vibrate(10);
        }

        mBubblePaint.setColor(bubbleColor);
        mLimitPaint.setColor(limitCircleColor);

        canvas.drawCircle(centerPnt.x, centerPnt.y, mBubbleRuleRadius, mBubbleRulePaint);
        canvas.drawCircle(centerPnt.x, centerPnt.y, mLimitRadius, mLimitPaint);

        drawBubble(canvas);
    }
    private boolean isCenter(PointF bubblePoint){

        if(bubblePoint == null){
            return false;
        }

        return Math.abs(bubblePoint.x - centerPnt.x) < 1 && Math.abs(bubblePoint.y - centerPnt.y) < 1;
    }

    private void drawBubble(Canvas canvas) {
        if(bubblePoint != null){
            canvas.drawCircle(bubblePoint.x, bubblePoint.y, mBubbleRadius, mBubblePaint);
        }
    }
    /**
     * Convert angle to screen coordinate point.
     * @param rollAngle 横滚角(弧度)
     * @param pitchAngle 俯仰角(弧度)
     * @return
     */
    /*
    private PointF convertCoordinate(double rollAngle, double pitchAngle, double radius){
        double scale = radius / Math.toRadians(90);

        //以圆心为原点，使用弧度表示坐标
        double x0 = -(rollAngle * scale);
        double y0 = -(pitchAngle * scale);

        //使用屏幕坐标表示气泡点
        double x = centerPnt.x - x0;
        double y = centerPnt.y - y0;

        return new PointF((float)x, (float)y);
    }

    /**
     *
     * @param pitchAngle （弧度）
     * @param rollAngle (弧度)
     */
    /*
    public void setAngle(double rollAngle, double pitchAngle) {

        this.pitchAngle = pitchAngle;
        this.rollAngle = rollAngle;

        //考虑气泡边界不超出限制圆，此处减去气泡的显示半径，做为最终的限制圆半径
        float limitRadius = mLimitRadius - mBubbleRadius;

        bubblePoint = convertCoordinate(rollAngle, pitchAngle, mLimitRadius);
        outLimit(bubblePoint, limitRadius);

        //坐标超出最大圆，取法向圆上的点
        if(outLimit(bubblePoint, limitRadius)){
            onCirclePoint(bubblePoint, limitRadius);
        }

        invalidate();
    }

    /**
     * 验证气泡点是否超过限制{@link #mLimitRadius}
     * @param bubblePnt
     * @return
     */
    /*
    private boolean outLimit(PointF bubblePnt, float limitRadius){

        float cSqrt = (bubblePnt.x - centerPnt.x)*(bubblePnt.x - centerPnt.x)
                + (centerPnt.y - bubblePnt.y) * + (centerPnt.y - bubblePnt.y);


        if(cSqrt - limitRadius * limitRadius > 0){
            return true;
        }
        return false;
    }

    /**
     * 计算圆心到 bubblePnt点在圆上的交点坐标
     * 即超出圆后的最大圆上坐标
     * @param bubblePnt 气泡点
     * @param limitRadius 限制圆的半径
     * @return
     */
    /*
    private PointF onCirclePoint(PointF bubblePnt, double limitRadius) {
        double azimuth = Math.atan2((bubblePnt.y - centerPnt.y), (bubblePnt.x - centerPnt.x));
        azimuth = azimuth < 0 ? 2 * Math.PI + azimuth : azimuth;

        //圆心+半径+角度 求圆上的坐标
        double x1 = centerPnt.x + limitRadius * Math.cos(azimuth);
        double y1 = centerPnt.y + limitRadius * Math.sin(azimuth);

        bubblePnt.set((float) x1, (float) y1);

        return bubblePnt;
    }

    public double getPitchAngle(){
        return this.pitchAngle;
    }

    public double getRollAngle(){
        return this.rollAngle;
    }
    */
}
