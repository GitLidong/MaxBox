package com.lidong.maxbox.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.lidong.maxbox.camera.CameraManager;
import com.lidong.maxbox.decode.CaptureActivityHandler;
import com.lidong.maxbox.decode.DecodeManager;
import com.lidong.maxbox.decode.InactivityTimer;
import com.lidong.maxbox.views.QrCodeFinderView;

import java.io.IOException;

/**
 * Created by ubuntu on 17-9-11.
 * 二维码扫描类。
 */

public class QRcodeActivity extends Activity implements SurfaceHolder.Callback,
        View.OnClickListener {
    public static final String INTENT_OUT_STRING_SCAN_RESULT = "scan_result";
    private static final String INTENT_IN_INT_SUPPORT_TYPE = "support_type";
    private static final int REQUEST_PERMISSIONS = 1;
    private CaptureActivityHandler mCaptureActivityHandler;
    private boolean mHasSurface;
    private InactivityTimer mInactivityTimer;
    private QrCodeFinderView mQrCodeFinderView;
    private SurfaceView mSurfaceView;
    private View mLlFlashLight;
    private boolean mNeedFlashLightOpen = true;
    private ImageView mIvFlashLight;
    private ImageView mencode;
    private ViewStub mSurfaceViewStub;
    private DecodeManager mDecodeManager = new DecodeManager();
    private Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initView();
        initData();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!mHasSurface) {
            mHasSurface = true;
            initCamera(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mHasSurface = false;
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
                Intent intent = new Intent(QRcodeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.encode_btn:
                Intent intent1 = new Intent(QRcodeActivity.this,QrCodeCreateActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void initView() {
        mIvFlashLight = (ImageView) findViewById(R.id.qr_code_ll_flash_light);
        mencode = (ImageView) findViewById(R.id.encode_btn);
        mQrCodeFinderView = (QrCodeFinderView) findViewById(R.id.qr_code_view_finder);
        mSurfaceViewStub = (ViewStub) findViewById(R.id.qr_code_view_stub);
        mHasSurface = false;
        mIvFlashLight.setOnClickListener(this);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        mencode.setOnClickListener(this);
    }

    private void initData() {
        CameraManager.init();
        mInactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            initCamera();
        //} else {
            //ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, REQUEST_PERMISSIONS);
        //}
    }

    @Override
    protected void onDestroy() {
        //if (null != mInactivityTimer) {
            //mInactivityTimer.shutdown();
        //}
        super.onDestroy();
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

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            if (!CameraManager.get().openDriver(surfaceHolder)) {
                showPermissionDeniedDialog();
                return;
            }
        } catch (IOException e) {
            // 基本不会出现相机不存在的情况
            Toast.makeText(this, getString(R.string.qr_code_camera_not_found), Toast.LENGTH_SHORT).show();
            finish();
            return;
        } catch (RuntimeException re) {
            re.printStackTrace();
            showPermissionDeniedDialog();
            return;
        }
        mQrCodeFinderView.setVisibility(View.VISIBLE);
        //mLlFlashLight.setVisibility(View.VISIBLE);
        findViewById(R.id.qr_code_view_background).setVisibility(View.GONE);
        turnFlashLightOff();
        if (mCaptureActivityHandler == null) {
            mCaptureActivityHandler = new CaptureActivityHandler(this);
        }
    }

    private void showPermissionDeniedDialog() {
        //findViewById(R.id.qr_code_view_background).setVisibility(View.VISIBLE);
        //mQrCodeFinderView.setVisibility(View.GONE);
        mDecodeManager.showPermissionDeniedDialog(this);
    }

    private void turnFlashLightOff() {
        try {
            CameraManager.get().setFlashLight(false);
            mNeedFlashLightOpen = true;
            //mTvFlashLightText.setText(getString(R.string.qr_code_open_flash_light));
            //mIvFlashLight.setBackgroundResource(R.drawable.flashlight_turn_on);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnFlashlightOn() {
        try {
            CameraManager.get().setFlashLight(true);
            mNeedFlashLightOpen = false;
            //mTvFlashLightText.setText(getString(R.string.qr_code_close_flash_light));
            //mIvFlashLight.setBackgroundResource(R.drawable.flashlight_turn_off);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
