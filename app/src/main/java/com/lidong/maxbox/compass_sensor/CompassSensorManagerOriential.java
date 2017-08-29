package com.lidong.maxbox.compass_sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class CompassSensorManagerOriential extends CompassSensorManager {

	
	private Sensor mOrientationSensor;
	
	private SensorEventListener mSensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			float direction = event.values[0] * -1.0f;
			mCurrentDirection = processDegree(direction);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
		
	public CompassSensorManagerOriential(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
		
		if(mSensorManager!= null){
			mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		}

	}

	@Override
	public void registerSensorListener() {
		// TODO Auto-generated method stub
		if (mOrientationSensor != null) {
			mSensorManager.registerListener(mSensorEventListener,
					mOrientationSensor, SensorManager.SENSOR_DELAY_GAME);
		} else {
			Toast.makeText(mActivity, "KKKKKKKKKK", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void unregisterSensorListener() {
		// TODO Auto-generated method stub
		if(mSensorManager !=null){
			mSensorManager.unregisterListener(mSensorEventListener);
		}
		
	}



}
