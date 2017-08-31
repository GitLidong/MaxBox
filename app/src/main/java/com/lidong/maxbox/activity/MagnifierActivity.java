package com.lidong.maxbox.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.lidong.maxbox.R;

import java.io.IOException;
import java.util.List;

public class MagnifierActivity extends Activity {

    private SurfaceView cameraPreview=null;
    private SurfaceHolder surfaceHolder=null;
    private ImageButton flashBn;
    private Camera camera=null;
    //自动聚焦的回调
    private Camera.AutoFocusCallback mAutoFocusCallback;
    //闪光灯是否开启
    private boolean isLighting=false;
    //是否支持闪光灯
    private boolean isSupportedLight=false;
    //调整Zoom的seekbar
    private SeekBar mSeekBar;
    private int cameraCount,maxZoom;
    private Camera.Parameters parameters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnifier);
        initView();
        initData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    private void initView() {
        cameraPreview = (SurfaceView) this.findViewById(R.id.magnifier_camerapreview);
        flashBn = (ImageButton) findViewById(R.id.magnifier_iview_operation);
        flashBn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isSupportedLight){
                    if (isLighting == false) {
                        String flashMode=Camera.Parameters.FLASH_MODE_TORCH;
                        flashBn.setImageResource(R.drawable.btn_magn_light_pressed);
                        parameters.setFlashMode(flashMode);
                        camera.setParameters(parameters);
                        isLighting = true;
                    } else {
                        String flashMode=Camera.Parameters.FLASH_MODE_OFF;
                        flashBn.setImageResource(R.drawable.btn_magn_light_normal);
                        parameters.setFlashMode(flashMode);
                        camera.setParameters(parameters);
                        isLighting = false;
                    }
                }
            }
        });
        mSeekBar=(SeekBar)findViewById(R.id.magnifier_seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                parameters = camera.getParameters();
                parameters.setZoom((int) (progress * 1.0f / (maxZoom * 100) * maxZoom));
                camera.setParameters(parameters);
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
        //设置放大倍数  parameters.setZoom(12);
        //开启闪光灯    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        //参数设置赋给Camera对象
        maxZoom = parameters.getMaxZoom();
        mSeekBar.setMax(maxZoom*100);
        mSeekBar.setProgress((int) (0.5 * maxZoom * 100));
        parameters.setZoom((int) (0.5 * maxZoom));
        camera.setParameters(parameters);
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

        List<String> features = parameters.getSupportedFlashModes();//判断是否支持闪光灯
        if (features.contains(Camera.Parameters.FLASH_MODE_ON)) {
            isLighting = false;
            isSupportedLight = true;
        }
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
}
