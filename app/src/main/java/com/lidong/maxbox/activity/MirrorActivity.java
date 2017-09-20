package com.lidong.maxbox.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.lidong.maxbox.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by ubuntu on 17-8-30.
 *
 * 为了让用户有效地拍照或录像，他们必须能够看到设备相机看到什么。
 * 相机预览类是可以显示来自相机的实时图像数据的SurfaceView，
 * 因此用户可以对图片或视频进行构图和捕获。
 */

public class MirrorActivity extends Activity implements View.OnClickListener,
        SurfaceHolder.Callback,SeekBar.OnSeekBarChangeListener{

    private String TAG = "MirrorActivity";

    //SurfaceView 用来加载相机预览，可以显示来自相机的实时图像数据的
    private SurfaceView mView;
    private SurfaceHolder mHolder=null;
    private Camera mCamera=null;

    private Button take_photo;
    private ImageView photo_result;

    private SeekBar seekBar;
    private int num=0;

    private SharedPreferences shared = null;
    private boolean isCameraWorking;
    int screenWidth, screenHeight;

    //存储照片名字，及文件
    private File pictureFile;
    private String pictureName;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFullScreen();
        setContentView(R.layout.activity_mirror);
        initView();
    }

    private void initView() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        take_photo = (Button) findViewById(R.id.take_photo);
        take_photo.setOnClickListener(this);
        photo_result = (ImageView) findViewById(R.id.image_result);
        photo_result.setOnClickListener(this);
        seekBar = (SeekBar) findViewById(R.id.seekbar_brintness);
        seekBar.setOnSeekBarChangeListener(this);
        shared=getSharedPreferences("base64",MODE_PRIVATE);
        num=shared.getInt("seekBarNum", 50);
        Log.i(TAG,"seekBar num： "+num);
        changeAppBrightness(num);
        seekBar.setProgress(num);

        mView=(SurfaceView)this.findViewById(R.id.surfaceView1);
        mHolder=mView.getHolder();
        mHolder.addCallback(this);
        // 已弃用的设置，但在3.0之前的Android版本上需要
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
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

        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                dealWithCameraData(data);
            }
        });
    }

    //保存拍照数据
    private void dealWithCameraData(byte[] data) {
        Log.i(TAG,"dealWithCameraData function");
        //根据系统时间命名照片，同时更具名字创建相应的目录和文件
        pictureName =System.currentTimeMillis() + ".jpg";
        if (isExternalStorageWritable()) {
            try {
                pictureFile = getAlbumStorageDir(pictureName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //先将图片写入bitmap中
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        try {
            //将 bitmap 旋转270度，不知道为什么拍完照存储照片会自动旋转90度。
            Matrix matrix = new Matrix();
            matrix.setRotate(270, bitmap.getWidth(),bitmap.getHeight());
            Bitmap resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            //打开文件的输入流，将旋转后的bitmap写入文件中
            FileOutputStream fos = new FileOutputStream(pictureFile);
            resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String path = pictureFile.getAbsolutePath();
        // 最后通知图库更新
        getApplicationContext().sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        //设置预览的小照片
        photo_result.setImageBitmap(bitmap);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // TODO Auto-generated method stub
        initCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }
        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // TODO Auto-generated method stub
        releaseCamera();
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

    //初始化相机
    private void initCamera() {
        if (checkCameraHardware(this)) {
            mCamera = getCameraInstance();
            try {
                mCamera.setPreviewDisplay(mHolder);
                setCameraDisplayOrientation(MirrorActivity.this,0,mCamera);
                mCamera.startPreview();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            setCameraParameters(mCamera);
            isCameraWorking = true;
        }
        else {
            Toast.makeText(this,"该设备可能没有摄像头",Toast.LENGTH_SHORT).show();
        }
    }

    /*
     *  检查设备是否有摄像头
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /*
     * 安全获取摄像头,默认启用的摄像头是后置摄像头id=0，  id =1前置摄像头
     * 使用Camera.open（）时，请始终检查异常情况。
     * 如果相机使用或不存在，则无法检查异常将导致您的应用程序被系统关闭。
     */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(1); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        releaseCamera();

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

    /*
     * 设置全屏
     */
    private void initFullScreen() {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(
                WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
    }

    /*
     * 释放相机
     */
    private void releaseCamera(){
        if(mCamera != null) {
            /* 若摄像头正在工作，先停止它 */
            if(isCameraWorking) {
                mCamera.stopPreview();
                isCameraWorking = false;
            }
            mCamera.release();
        }
    }

    /*
     * 设置相机的部分参数
     */
    private void setCameraParameters(Camera camera) {
        //获取其他常用设置
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setJpegQuality(100);
        List<Camera.Size> sizeList = parameters.getSupportedPictureSizes();
        Camera.Size resultSize = getSuitableSize(sizeList);
        Log.i("lidong",resultSize.width+" suitbale "+resultSize.height);
        parameters.setPictureSize(resultSize.width,resultSize.height);

        mCamera.setParameters(parameters);
    }

    //获得合适的尺寸
    private Camera.Size getSuitableSize(List<Camera.Size> sizeList) {
        int lastDiff = Math.abs(sizeList.get(0).height - screenWidth );
        int nowDiff;
        int index = 0;
        if (sizeList.size() > 1) {
            for (int i=0 ;i<sizeList.size();i++) {
                nowDiff = Math.abs(sizeList.get(i).height - screenWidth);
                if (lastDiff >= nowDiff) {
                    lastDiff = nowDiff;
                    index = i;
                }
            }
        }
        return sizeList.get(index);
    }

}

