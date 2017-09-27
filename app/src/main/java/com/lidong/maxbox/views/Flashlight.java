package com.lidong.maxbox.views;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.lidong.maxbox.R;

/**
 * Created by ubuntu on 17-9-5.
 */

public class Flashlight extends ViewGroup {
    private static String TAG = "Flashlight";
    public boolean mState = false;

    private Context mContext;
    private CameraManager mCameraManager;
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;
    private CameraManager.TorchCallback mTorchCallback;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Flashlight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(mState);

        mContext = context;

        mCameraManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);

        mTorchCallback = new CameraManager.TorchCallback() {
            @Override
            public void onTorchModeChanged(String cameraId, boolean enabled) {
                if ("0".equals(cameraId)) {
                    mState = enabled;
                    setBackground(enabled);
                }
            }
        };
        mCameraManager.registerTorchCallback(mTorchCallback, null);
    }

    public Flashlight(Context context) {
        super(context, null);
    }

    public void setBackground(boolean state) {
        if (state) {
            setBackgroundResource(R.drawable.flashlight_level3);
        } else {
            setBackgroundResource(R.drawable.flashlight_level0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        mLeft = r * 5 / 12;
        mRight = r * 7 / 12;
        mTop = b * 103 / 171;
        mBottom = b * 133 / 171;
        Log.d(TAG, "onLayout l=" + l + " t=" + t + " r=" + r + " b=" + b);
    }

    private boolean withInView(int x, int y) {
        Log.d(TAG, "withInView l=" + mLeft + " right=" + mRight + " top=" + mTop + " bottom=" + mBottom);
        return (x > mLeft) && (x < mRight) && (y > mTop) && (y < mBottom);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        final int action = event.getAction();
        final int x = (int)event.getX();
        final int y = (int)event.getY();
        Log.d(TAG, "onTouchEvent X=" + x + " y=" + y);
        if (action == MotionEvent.ACTION_DOWN) {
            if (withInView(x, y)) {
                if (mState) {
                    mState = false;
                    setFlashLight(false);
                } else {
                    mState = true;
                    setFlashLight(true);
                }
            } else {
                return false;
            }
        }
        return super.onTouchEvent(event);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setFlashLight(boolean enabled) {
        try {
            Log.d(TAG, "setFlashLight " + enabled);
            mCameraManager.setTorchMode("0", enabled);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void unregisterTorchCallback() {
        mCameraManager.unregisterTorchCallback(mTorchCallback);
    }
}
