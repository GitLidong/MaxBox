package com.lidong.maxbox.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidong.maxbox.MainActivity;
import com.lidong.maxbox.R;
import com.lidong.maxbox.compass_sensor.CompassSensorManager;
import com.lidong.maxbox.compass_sensor.CompassSensorManagerOriential;
import com.lidong.maxbox.views.CompassView;

/**
 * Created by ubuntu on 17-8-23.
 */

public class CompassMainActivity extends Activity implements View.OnClickListener {

    private static final int EXIT_TIME = 2000;
    private final float MAX_ROATE_DEGREE = 1.0f;


    private CompassSensorManager mCompassSensorManager;

    private TextView compass_textview;
    private Button back;
    private CompassView compassView;
    private RelativeLayout compass_alert;
    private ImageView compass_first;
    private TextView short_direction;
    private TextView angle;

    private LocationManager mLocationManager;
    private String mLocationProvider;
    private float mAngleDirection;
    private float mCurrentAngleDirection;
    private AccelerateInterpolator mInterpolator;
    protected final Handler mHandler = new Handler();
    private boolean mStopDrawing;

    private long firstExitTime = 0L;
    private boolean isNull = false;
    private TextView locationTextView;

    protected Runnable mCompassViewUpdater = new Runnable() {
        @Override
        public void run() {
            if (compassView != null && !mStopDrawing && mCompassSensorManager != null) {
                mCurrentAngleDirection = mCompassSensorManager.getmCurrentDirection();
                if (mAngleDirection != mCurrentAngleDirection) {


                    float tmpAngle = mCurrentAngleDirection;
                    if (tmpAngle - mAngleDirection > 180) {
                        tmpAngle -= 360;
                    } else if (tmpAngle - mAngleDirection < -180) {
                        tmpAngle += 360;
                    }

                    float distance = tmpAngle - mAngleDirection;
                    if (Math.abs(distance) > MAX_ROATE_DEGREE) {
                        distance = distance > 0 ? MAX_ROATE_DEGREE : (-1.0f * MAX_ROATE_DEGREE);
                    }

                    mAngleDirection = mCompassSensorManager.processDegree(mAngleDirection
                            + ((tmpAngle - mAngleDirection) * mInterpolator
                            .getInterpolation(Math.abs(distance) > MAX_ROATE_DEGREE ? 0.4f
                                    : 0.3f)));
                    compassView.updateAngle(mAngleDirection);
                }

                updateDirection();

                mHandler.postDelayed(mCompassViewUpdater, 20);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        init();
        initServices();
    }

    private void init() {

        mAngleDirection = 0.0f;
        mCurrentAngleDirection = 0.0f;
        mInterpolator = new AccelerateInterpolator();
        mStopDrawing = true;

        short_direction = (TextView) findViewById(R.id.direction);
        angle = (TextView) findViewById(R.id.angle);
        compass_textview = (TextView) findViewById(R.id.compass_direction);
        back = (Button) findViewById(R.id.back);
        compassView = (CompassView) findViewById(R.id.compassPointer);
        compass_first = (ImageView) findViewById(R.id.compass_first);
        locationTextView = (TextView) findViewById(R.id.locationView);
        compass_alert = (RelativeLayout) getLayoutInflater().inflate(R.layout.compass_alert, null);
        back.setOnClickListener(this);
        compassView.setOnClickListener(this);
        compass_first.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationProvider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            updateLocation(mLocationManager
                    .getLastKnownLocation(mLocationProvider));
            //原地不动，则不会及时更新，故将距离10改为0
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, mLocationListener);

        } else {
            locationTextView.setText(R.string.compass_cannot_get_location);
        }

        if (mCompassSensorManager != null) {
            mCompassSensorManager.registerSensorListener();
        }


        mStopDrawing = false;
        mHandler.postDelayed(mCompassViewUpdater, 20);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStopDrawing = true;

        if (mCompassSensorManager != null) {
            mCompassSensorManager.unregisterSensorListener();
        }

        if (mLocationProvider != null) {
            mLocationManager.removeUpdates(mLocationListener);

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.compass_first:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.compass_text_calibration_title);
                builder.setView(compass_alert);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        compass_first.setImageDrawable(null);
                        isNull = true;

                    }
                }).create().show();


                break;
        }
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        if (curTime - firstExitTime < EXIT_TIME) {
            finish();
        } else {
            Toast.makeText(this, R.string.compass_exit_toast, Toast.LENGTH_SHORT)
                    .show();
            firstExitTime = curTime;
        }
    }

    private void initServices() {
        mCompassSensorManager = new CompassSensorManagerOriential(this);
        initLocationProvider();
    }

    private void initLocationProvider() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);  //定位过程中消耗资费
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        mLocationProvider = mLocationManager.getBestProvider(criteria, true);
    }

    private void updateDirection() {
        if (isNull == true) {
            float direction = mCompassSensorManager.processDegree(mCurrentAngleDirection * -1.0f);
            if (direction > 22.5f && direction < 157.5f) {
                compass_textview.setText(R.string.compass_east);
                short_direction.setText(R.string.compass_east_short);
            } else if (direction > 202.5f && direction < 337.5f) {
                compass_textview.setText(R.string.compass_west);
                short_direction.setText(R.string.compass_west_short);
            }
            if (direction > 112.5f && direction < 247.5f) {
                compass_textview.setText(R.string.compass_south);
                short_direction.setText(R.string.compass_south_short);
            } else if (direction < 67.5f || direction > 292.5f) {
                compass_textview.setText(R.string.compass_north);
                short_direction.setText(R.string.compass_north_short);
            }

            if (direction > 112.5f && direction < 157.5f) {
                compass_textview.setText(R.string.compass_southeast);
                short_direction.setText(R.string.compass_southeast_short);
            } else if (direction > 202.5f && direction < 247.5f) {
                compass_textview.setText(R.string.compass_southwest);
                short_direction.setText(R.string.compass_southwest_short);
            }
            if (direction > 22.5f && direction < 67.5f) {
                compass_textview.setText(R.string.compass_northeast);
                short_direction.setText(R.string.compass_northeast_short);
            } else if (direction > 292.5f && direction < 337.5f) {
                compass_textview.setText(R.string.compass_northwest);
                short_direction.setText(R.string.compass_northwest_short);
            }

            int direction2 = (int) direction;
            angle.setText(String.valueOf(direction2) + getResources()
                    .getString(R.string.compass_degree));

        }
    }

    private void updateLocation(Location location) {
        if (location == null) {
            locationTextView.setText(R.string.compass_cannot_get_location);
        } else {
            StringBuilder sb = new StringBuilder();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            if (latitude >= 0.0f) {
                sb.append(getString(R.string.location_north,
                        getLocationString(latitude)));
            } else {
                sb.append(getString(R.string.location_south,
                        getLocationString(-1.0 * latitude)));
            }

            sb.append("    ");

            if (longitude >= 0.0f) {
                sb.append(getString(R.string.location_east,
                        getLocationString(longitude)));
            } else {
                sb.append(getString(R.string.location_west,
                        getLocationString(-1.0 * longitude)));
            }
            locationTextView.setText(sb.toString());
        }
    }


    private String getLocationString(double input) {
        int du = (int) input;
        int fen = (((int) ((input - du) * 3600))) / 60;
        int miao = (((int) ((input - du) * 3600))) % 60;
        return String.valueOf(du) + "°" + String.valueOf(fen) + "'"
                + String.valueOf(miao);
    }


    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (status != LocationProvider.OUT_OF_SERVICE) {
                if (ActivityCompat.checkSelfPermission(getBaseContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                        .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                updateLocation(mLocationManager
                        .getLastKnownLocation(mLocationProvider));
            } else {
                locationTextView.setText(R.string.compass_cannot_get_location);
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }
    };


}
