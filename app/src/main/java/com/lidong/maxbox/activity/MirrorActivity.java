package com.lidong.maxbox.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.lidong.maxbox.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * Created by ubuntu on 17-8-30.
 */

public class MirrorActivity extends Activity implements View.OnClickListener
        ,SurfaceHolder.Callback,SeekBar.OnSeekBarChangeListener{
    private SurfaceView mView = null;
    private SurfaceHolder mHolder=null;
    private Camera mCamera=null;
    private Button take_photo;
    public static final String FILE_PATH = "filePath";
    private ImageView photo_result;
    private SharedPreferences preferences;
    private Camera.AutoFocusCallback mAutoFocusCallback;
    private SeekBar seekBar;
    private SharedPreferences shared = null;
    private int num=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        take_photo = (Button) findViewById(R.id.take_photo);
        take_photo.setOnClickListener(this);
        photo_result = (ImageView) findViewById(R.id.image_result);
        seekBar = (SeekBar) findViewById(R.id.seekbar_brintness);
        seekBar.setOnSeekBarChangeListener(this);
        shared=getSharedPreferences("base64",MODE_PRIVATE);
        num=shared.getInt("seekBarNum", 50);
        changeAppBrightness(num);
        Log.i("fffffffffff",""+num);
        seekBar.setProgress(num);
        mView=(SurfaceView)this.findViewById(R.id.surfaceView1);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels >= displayMetrics.heightPixels ?
                displayMetrics.heightPixels : displayMetrics.widthPixels;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, width * 16 / 9);
        mView.setLayoutParams(params);
        mHolder=mView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
        preferences = getSharedPreferences("count",MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                startTakephoto();
                break;
        }
    }

    private void startTakephoto() {
        //获取到相机参数
        Camera.Parameters parameters = mCamera.getParameters();
        //设置图片保存格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        //设置图片大小
        parameters.setPreviewSize(1920,1080);
        parameters.setPictureSize(1920,1080);
        //设置对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mAutoFocusCallback = new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                // TODO Auto-generated method stub
                if(success){
                    camera.setOneShotPreviewCallback(null);
                    Toast.makeText(MirrorActivity.this,
                            "自动聚焦成功" , Toast.LENGTH_SHORT).show();
                }
            }
        };

        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                dealWithCameraData(data);
                mCamera=Camera.open(1);
                try {
                    mCamera.setPreviewDisplay(mHolder);
                    setCameraDisplayOrientation(MirrorActivity.this,1,mCamera);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mCamera.startPreview();
            }
        });
    }

    //保存拍照数据
    private void dealWithCameraData(byte[] data) {
        Log.i("gengxuejing","kkkkk");
        FileOutputStream fos = null;
        String tempStr = null;
        tempStr = Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/";
        //图片临时保存位置
        String fileName = tempStr + System.currentTimeMillis() + ".jpg";
        Log.i("MirrorActivity",fileName);
        File tempFile = new File(fileName);

        try {
            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                    tempFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String path = tempFile.getAbsolutePath();
        // 最后通知图库更新
        getApplicationContext().sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        try {
            fos = new FileOutputStream(fileName);
            fos.write(data);
            fos.close();
            Bitmap bitmap = BitmapFactory.decodeFile(fileName);
            photo_result.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // TODO Auto-generated method stub
        mCamera=Camera.open(1);
        try {
            mCamera.setPreviewDisplay(mHolder);
            setCameraDisplayOrientation(MirrorActivity.this,1,mCamera);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // TODO Auto-generated method stub
        mCamera.startPreview();
        mCamera.release();
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        changeAppBrightness(seekBar.getProgress());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    // 获取系统屏幕亮度
    public int getScreenBrightness() {
        int value = 0;
        ContentResolver cr = getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {

        }
        return value;
    }

    // 获取app亮度
    public void changeAppBrightness(int brightness) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        window.setAttributes(lp);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        SharedPreferences.Editor editor=  shared.edit();
        editor.clear();
        editor.putInt("seekBarNum", seekBar.getProgress());
        editor.commit();
    }

}

