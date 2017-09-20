package com.lidong.maxbox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.lidong.maxbox.MainActivity;
import com.lidong.maxbox.R;
import com.lidong.maxbox.activity.QrcodeSonAcitvity.QrCodeCreateActivity;
import com.lidong.maxbox.activity.QrcodeSonAcitvity.QrCodeResultActivity;
import com.lidong.maxbox.camera.CameraManager;
import com.lidong.maxbox.decode.CaptureActivityHandler;
import com.lidong.maxbox.decode.DecodeManager;
import com.lidong.maxbox.decode.InactivityTimer;
import com.lidong.maxbox.manager.ActivityCollector;
import com.lidong.maxbox.manager.MyActivity;
import com.lidong.maxbox.views.QrCodeFinderView;

import java.io.IOException;

/**
 * Created by ubuntu on 17-9-11.
 * 二维码扫描类。
 */

public class QRcodeActivity extends MyActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private String TAG = "QRcodeActivity";

    //?
    public static final String INTENT_OUT_STRING_SCAN_RESULT = "scan_result";
    private static final String INTENT_IN_INT_SUPPORT_TYPE = "support_type";
    private static final int REQUEST_PERMISSIONS = 1;

    private CaptureActivityHandler mCaptureActivityHandler;
    private InactivityTimer mInactivityTimer;

    //判断当前SurfaceView 是否已经被创建
    private boolean mHasSurface;
    private SurfaceView mSurfaceView;
    //判断闪光灯是否可以打开
    private boolean mNeedFlashLightOpen = true;
    // 闪光灯和添加二维码按钮,返回
    private ImageView mIvFlashLight;
    private ImageView mencode;
    private Button back;

    private QrCodeFinderView mQrCodeFinderView;
    private ViewStub mSurfaceViewStub;
    // 二维码解析管理
    private DecodeManager mDecodeManager;
    //判断摄像头是否在工作
    private boolean isCameraWorking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Log.i(TAG,"onCreate QRcodeActivity");
        initView();
    }

    private void initView() {

        mIvFlashLight = (ImageView) findViewById(R.id.qr_code_ll_flash_light);
        mIvFlashLight.setOnClickListener(this);
        mencode = (ImageView) findViewById(R.id.encode_btn);
        mencode.setOnClickListener(this);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);

        mQrCodeFinderView = (QrCodeFinderView) findViewById(R.id.qr_code_view_finder);
        mSurfaceViewStub = (ViewStub) findViewById(R.id.qr_code_view_stub);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        Log.i(TAG,"onResume QRcodeActivity");
        initCamera();
    }

    private void initData() {
        CameraManager.init();

        mInactivityTimer = new InactivityTimer(this);

        mHasSurface = false;

        isCameraWorking = false;

        mDecodeManager = new DecodeManager();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!mHasSurface) {
            mHasSurface = true;
            initCamera(surfaceHolder);
            Log.i(TAG,"surfaceCreated QRcodeActivity");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mHasSurface = false;
        shutdownCameraAndHandler();
        Log.i(TAG,"surfaceDestroyed");
    }

    public Handler getCaptureActivityHandler() {
        return mCaptureActivityHandler;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qr_code_ll_flash_light:
                if (mNeedFlashLightOpen) {
                    turnFlashlightOn();
                } else {
                    turnFlashLightOff();
                }
                break;
            case R.id.back:
                ActivityCollector.finishAllActivities();
                break;
            case R.id.encode_btn:
                Intent intent1 = new Intent(QRcodeActivity.this,QrCodeCreateActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAllActivities();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shutdownCameraAndHandler();
        Log.i(TAG,"onDestroy QRcodeActivity");
    }

    private void initCamera() {
        if (null == mSurfaceView) {
            mSurfaceViewStub.setLayoutResource(R.layout.layout_surface_view);
            mSurfaceView = (SurfaceView) mSurfaceViewStub.inflate();
        }
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        if (mHasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        //初始化相机
        try {
            if (!CameraManager.get().openDriver(surfaceHolder)) {
                Log.i(TAG,"initCamera failed and return !");
                showPermissionDeniedDialog();
                return;
            }
        } catch (IOException e) {
            // 基本不会出现相机不存在的情况
            Toast.makeText(this, getString(R.string.qr_code_camera_not_found), Toast.LENGTH_SHORT).show();
            finish();
            return;
        } catch (RuntimeException re) {
            Log.i(TAG,"initCamera RuntimeException and return !");
            re.printStackTrace();
            showPermissionDeniedDialog();
            return;
        }
        Log.i(TAG,"initCamera OK and continue !");

        mQrCodeFinderView.setVisibility(View.VISIBLE);
        findViewById(R.id.qr_code_view_background).setVisibility(View.GONE);
        turnFlashLightOff();
        if (mCaptureActivityHandler == null) {
            Log.i(TAG,"mCaptureActivityHandler is null and then new one!");
            mCaptureActivityHandler = new CaptureActivityHandler(this);
        } else {
            mCaptureActivityHandler.startThread(this);
        }
        isCameraWorking = true;
    }

    /**
     * Handler scan result
     *
     * @param result
     */
    public void handleDecode(Result result) {
        mInactivityTimer.onActivity();
        if (null == result) {
            mDecodeManager.showCouldNotReadQrCodeFromScanner(this, new DecodeManager.OnRefreshCameraListener() {
                @Override
                public void refresh() {
                    restartPreview();
                }
            });
        } else {
            String resultString = result.getText();
            handleResult(resultString);
        }
    }

    private void restartPreview() {
        if (null != mCaptureActivityHandler) {
            try {
                mCaptureActivityHandler.restartPreviewAndDecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleResult(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            mDecodeManager.showCouldNotReadQrCodeFromScanner(this, new DecodeManager.OnRefreshCameraListener() {
                @Override
                public void refresh() {
                    restartPreview();
                }
            });
        } else {
            Intent intent =new Intent(QRcodeActivity.this,QrCodeResultActivity.class);
            intent.putExtra("result",resultString);
            startActivity(intent);
        }
    }

    private void showPermissionDeniedDialog() {
        mDecodeManager.showPermissionDeniedDialog(this);
    }

    private void turnFlashLightOff() {
        try {
            CameraManager.get().setFlashLight(false);
            mNeedFlashLightOpen = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnFlashlightOn() {
        try {
            CameraManager.get().setFlashLight(true);
            mNeedFlashLightOpen = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 当前界面 被 stop或者 destroy时候 ，释放camera,
     * 并且停止对应的杀死对应的线程，停止消息处理
     */
    private void shutdownCameraAndHandler() {
        if (isCameraWorking) {
            mCaptureActivityHandler.quitSynchronously();
            CameraManager.get().closeDriver();
            isCameraWorking = false;
        }
    }
}
