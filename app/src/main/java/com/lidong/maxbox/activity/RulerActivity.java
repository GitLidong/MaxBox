package com.lidong.maxbox.activity;

import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.myinterface.RulerSizeCallback;
import com.lidong.maxbox.views.GuideLine;

public class RulerActivity extends AppCompatActivity{

    private String TAG = "RulerActivity";

    private GuideLine guideLine;

    private TextView rule_size_cm;
    private TextView rule_size_inch;

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
        double screenInches = Math.sqrt(x + y);//英寸
        Log.d(TAG, "Screen inches : " +screenInches);
    }

    private void initView() {
        rule_size_cm = (TextView) findViewById(R.id.rule_size_cm);
        rule_size_cm.setRotation(90);
        rule_size_inch = (TextView) findViewById(R.id.rule_size_inch);
        rule_size_inch.setRotation(90);

        guideLine = (GuideLine) findViewById(R.id.rule_guide_line);
        guideLine.setOnSizeCallback(sizeCallback);

    }

    private RulerSizeCallback sizeCallback = new RulerSizeCallback() {
        @Override
        public void setRulerSizeCm(String cm) {
            rule_size_cm.setText(cm+" cm");
        }

        @Override
        public void setRuleSizeInch(String inch) {
            rule_size_inch.setText(inch+" inch");
        }
    };

}
