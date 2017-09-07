package com.lidong.maxbox.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.views.ProtractorView;

import java.io.IOException;
import java.util.List;

public class ProtractorActivity extends AppCompatActivity {

    private SurfaceView cameraPreview=null;
    private SurfaceHolder surfaceHolder=null;
    private Camera camera=null;
    private Camera.Parameters parameters;
    private int cameraCount;
    //自动聚焦的回调
    private Camera.AutoFocusCallback mAutoFocusCallback;

    ImageButton previewButton;
    boolean previewBnPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFullScreen();
        setContentView(R.layout.activity_protractor);
        previewBnPressed=false;
        initView();
    }

    private void initView() {
        cameraPreview = (SurfaceView) this.findViewById(R.id.protractor_camerapreview);
        previewButton=(ImageButton)findViewById(R.id.preview_button);
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previewBnPressed == false) {
                    previewButton.setImageResource(R.drawable.tool_protractor_viewmode_on);
                    previewBnPressed = true;
                    cameraPreview.setVisibility(View.VISIBLE);
                    initData();
                } else {
                    previewButton.setImageResource(R.drawable.tool_protractor_viewmode_off);
                    previewBnPressed = false;
                    cameraPreview.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData() {
        //获取相机个数
        cameraCount=Camera.getNumberOfCameras();
        surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                // TODO Auto-generated method stub

            }
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                initCamera(surfaceHolder);
                camera.startPreview();
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                releasedCamera();
            }
        });
    }

    private void initCamera(SurfaceHolder viewHolder)
    {
        camera = getCameraInstance(-1);
        parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        mAutoFocusCallback = new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                // TODO Auto-generated method stub
                if(success){
                    camera.setOneShotPreviewCallback(null);
                    //Toast.makeText(MagnifierActivity.this, "自动聚焦成功" , Toast.LENGTH_SHORT).show();
                }
            }
        };
        camera.autoFocus(mAutoFocusCallback);

        startPreviewOfCamera(viewHolder);
    }

    private void startPreviewOfCamera(SurfaceHolder viewHolder)
    {
        try {
            camera.setPreviewDisplay(viewHolder);
            camera.setDisplayOrientation(90);//防止预览图片旋转
            parameters = camera.getParameters();
            parameters.setRotation(90);//防止保存的图片旋转
            camera.setParameters(parameters);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Camera getCameraInstance(int cameraPosition) {
        if (cameraPosition < 0 || cameraPosition > cameraCount) {
            cameraPosition = 0;
        }
        Camera mCamera = null;
        try {
            mCamera = Camera.open(cameraPosition);
        }
        catch (Exception e) {
            //相机别的app在使用，或者是不存在
        }
        return mCamera;
    }

    private void releasedCamera() {
        if (null != camera)
        {
            camera.setPreviewCallback(null) ;
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void initFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
