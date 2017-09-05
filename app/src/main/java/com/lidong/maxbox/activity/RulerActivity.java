package com.lidong.maxbox.activity;

import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lidong.maxbox.R;
import com.lidong.maxbox.views.GuideLine;
import com.lidong.maxbox.views.RulerView;

public class RulerActivity extends AppCompatActivity{

    private String TAG = "RulerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFullScreen();
        setContentView(R.layout.activity_ruler);

        getScreenSizeOfDevice2();

        initView();
    }

    private void initFullScreen() {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(
                WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
    }

    private void getScreenSizeOfDevice2() {
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindowManager().getDefaultDisplay().getRealSize(point);
        }
        DisplayMetrics dm = getResources().getDisplayMetrics();
        double x = Math.pow(point.x/ dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        Log.d(TAG, "Screen inches : " + x + "  "+y);
    }

    private void initView() {

    }


}
