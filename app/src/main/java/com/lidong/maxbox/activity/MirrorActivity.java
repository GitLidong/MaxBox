package com.lidong.maxbox.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by ubuntu on 17-8-30.
 */

public class MirrorActivity extends Activity implements View.OnClickListener
        ,SurfaceHolder.Callback,SeekBar.OnSeekBarChangeListener{

    private String TAG = "MirrorActivity";

    private SurfaceView mView = null;
    private SurfaceHolder mHolder=null;

    private Camera mCamera=null;

    private Button take_photo;
    private ImageView photo_result;
    public static final String FILE_PATH = "filePath";

    private SharedPreferences preferences;
    private SharedPreferences shared = null;

    private Camera.AutoFocusCallback mAutoFocusCallback;

    private SeekBar seekBar;
    private int num=0;

    private boolean isCameraWorking;
    private int width,height;

    private File pictureFile;
    private String pictureName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);

        initView();
    }

    private void initView() {
        preferences = getSharedPreferences("count",MODE_PRIVATE);

        take_photo = (Button) findViewById(R.id.take_photo);
        take_photo.setOnClickListener(this);

        photo_result = (ImageView) findViewById(R.id.image_result);
        photo_result.setOnClickListener(this);

        seekBar = (SeekBar) findViewById(R.id.seekbar_brintness);
        seekBar.setOnSeekBarChangeListener(this);
        shared=getSharedPreferences("base64",MODE_PRIVATE);
        num=shared.getInt("seekBarNum", 50);
        Log.i(TAG,""+num);
        changeAppBrightness(num);
        seekBar.setProgress(num);

        mView=(SurfaceView)this.findViewById(R.id.surfaceView1);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels >= displayMetrics.heightPixels ?
                displayMetrics.heightPixels : displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, width * 16 / 9);
        mView.setLayoutParams(params);
        mHolder=mView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
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
                startTakePhoto();
                break;
            case R.id.image_result:
                gotoGallery();
                break;
            default:
                break;
        }
    }

    private void startTakePhoto() {
        //自动聚焦
        mCamera.autoFocus(mAutoFocusCallback);
        mAutoFocusCallback = new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                // TODO Auto-generated method stub
                if(success){
                    camera.setOneShotPreviewCallback(null);
                    Toast.makeText(MirrorActivity.this, "自动聚焦成功" , Toast.LENGTH_SHORT).show();
                }
            }
        };

        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                dealWithCameraData(data);
                initCamera();
                mCamera.startPreview();
            }
        });
    }

    //保存拍照数据
    private void dealWithCameraData(byte[] data) {
        Log.i(TAG,"dealWithCameraData function");

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        pictureName =System.currentTimeMillis() + ".jpg";

        if (isExternalStorageWritable()) {
            try {
                pictureFile = getAlbumStorageDir(pictureName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //将照片旋转270度，不知道为什么拍完找存储照片回自动旋转90度
            Matrix matrix = new Matrix();
            matrix.setRotate(270, bitmap.getWidth(),bitmap.getHeight());
            Bitmap resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            FileOutputStream fos = new FileOutputStream(pictureFile);
            resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String path = pictureFile.getAbsolutePath();
        // 最后通知图库更新
        getApplicationContext().sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));

        photo_result.setImageBitmap(bitmap);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // TODO Auto-generated method stub
        initCamera();
        mCamera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // TODO Auto-generated method stub
        if(mCamera != null) {
                /* 若摄像头正在工作，先停止它 */
            if(isCameraWorking) {
                mCamera.stopPreview();
                isCameraWorking = false;
            }
            mCamera.release();
        }
        Log.i(TAG,"surfaceDestroyed was function and relase camera!");
    }

    public static void setCameraDisplayOrientation(
            Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
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

    // 获取app亮度
    public void changeAppBrightness(int brightness) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        window.setAttributes(lp);
    }

    //判断 SDcard是否可用
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.i(TAG,"isExternalStorageWritable can use");
            return true;
        }
        return false;
    }

    //创建父文件夹及子文件
    public File getAlbumStorageDir(String albumName) throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory("OurMaxTools"),"");
        if (!file.mkdirs()) {
            if (file.exists()){
                Log.i(TAG,"files already exits");
            } else {
                Log.e(TAG, "Directory not created");
            }
        }
        Log.i(TAG,file.getCanonicalPath());
        File result = new File(file.getCanonicalPath(),albumName);
        if (!result.exists()) {
            result.createNewFile();
        }
        return result;
    }

    //初始化相机参数
    private void initCamera() {
        //默认启用的摄像头是后置摄像头id=0，  id =1前置摄像头
        mCamera = Camera.open(1);
        //获取其他常用设置
        Camera.Parameters parameters = mCamera.getParameters();
        try {
            mCamera.setPreviewDisplay(mHolder);
            setCameraDisplayOrientation(MirrorActivity.this,0,mCamera);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         /* 每秒从摄像头捕获5帧画面， */
        parameters.setPreviewFrameRate(5);
            /* 设置照片的输出格式:jpg */
        parameters.setPictureFormat(PixelFormat.JPEG);
        /* 照片质量 */
        parameters.set("jpeg-quality", 85);
        //设置图片大小
        parameters.setPictureSize(width,height);
        //设置对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        isCameraWorking = true;
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

    /*
     *Android 7.0的新特性规定，对于android 7.0应用(仅仅对于android 7.0版本的sdk而言，若是编译版本低于25仍然不会受到影响)，
     * android框架使用StrictMode Api禁止我们的应用对外部(跨越应用分享)公开file://
     * 若使用file://格式共享文件则会报FileUriExposedException异常，
     * android 7.0应用间的文件共享需要使用content://类型的URI分享，并且需要为其提供临时的文件访问权限
     * (Intent.FLAG_GRANT_READ_URI_PERMISSION和Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
     * 对此，官方给我们的建议是使用FileProvider类进行分享.
     */
    private void gotoGallery() {
        Uri photoURI = FileProvider.getUriForFile(this, "com.lidong.maxbox.fileprovider", pictureFile);
        Log.i(TAG,"filePath:    "+pictureFile);
        Log.i(TAG,"photoURI:    "+photoURI);
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(photoURI, "image/*");
        startActivity(intent);

    }

}

