package com.lidong.maxbox.activity;

import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.lidong.maxbox.R;
import com.lidong.maxbox.views.Flashlight;

public class FlashlightActivity extends AppCompatActivity {

    private Flashlight mFlashlight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFullScreen();
        setContentView(R.layout.activity_flashlight);
        mFlashlight = (Flashlight)findViewById(R.id.flashlight);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDestroy() {
        //TODO Auto-generated method stub
        super.onDestroy();
        mFlashlight.unregisterTorchCallback();
    }

    private void initFullScreen() {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(
                WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
    }
}
