package com.lidong.maxbox.activity;

import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.lidong.maxbox.R;
import com.lidong.maxbox.util.Flashlight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FlashlightActivity extends AppCompatActivity {

    private Flashlight mFlashlight;
    private static String TAG = "FlashlightActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
        PowerManager mPowerMgr = (PowerManager) getSystemService(POWER_SERVICE);
        mFlashlight = (Flashlight)findViewById(R.id.flashlight);
        mFlashlight.setPowerManager(mPowerMgr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //modify by zhangpengfei for ICE2-120 sync status with status bar start
        //changFlashLightBackGround();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDestroy() {
        //TODO Auto-generated method stub
        super.onDestroy();
        //add by wangshenxing [ICE2-1304] 2017/06/02 start
        //if (!com.huaqin.common.featureoption.FeatureOption.HQ_1520_AMAZON_FLASHON_ONDESTROY) {
        //mFlashlight.setFlashLight(false);
        //}
        //add by wangshenxing [ICE2-1304] 2017/06/02 end
        //add by zhangpengfei for ICE2-120 sync status with status bar start
        mFlashlight.unregisterTorchCallback();
    }

    private void changFlashLightBackGround() {
        String flashLightOn = "flash_mode is :0x1";
        String currentFlashLightLevel = readFlashLightLevel();
        if (currentFlashLightLevel != null) {
            mFlashlight.mState = currentFlashLightLevel.equals(flashLightOn);
            mFlashlight.setBackground(mFlashlight.mState);
        }
    }

    private String readFlashLightLevel() {
        String torchDevice = "/sys/class/flash_test/flash_test/flash_value";
        String flashLightLevel = null;
        FileReader flashLightFileReader = null;
        BufferedReader br = null;
        try {
            flashLightFileReader = new FileReader(torchDevice);
            br = new BufferedReader (flashLightFileReader);
            flashLightLevel = br.readLine();
            Log.i(TAG,"flashLightLevel--->" + flashLightLevel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (flashLightFileReader != null) {
                try {
                    flashLightFileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flashLightLevel;
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
