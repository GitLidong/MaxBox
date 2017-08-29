package com.lidong.maxbox.compass_sensor;

import android.app.Activity;
import android.content.Context;


import android.hardware.SensorManager;


public abstract class CompassSensorManager {

	protected Activity mActivity;
	protected SensorManager mSensorManager;
	protected float mCurrentDirection;

	
	
	public CompassSensorManager(Activity mActivity) {
		super();
		
		if(mActivity != null){
			this.mActivity = mActivity;
			mSensorManager = (SensorManager)mActivity.getSystemService(Context.SENSOR_SERVICE);
		}
		
	}

	
	public float getmCurrentDirection() {
		return mCurrentDirection;
	}


	public void setmCurrentDirection(float mCurrentDirection) {
		this.mCurrentDirection = mCurrentDirection;
	}
	
	

	public abstract void registerSensorListener();
	
	
	public abstract void unregisterSensorListener();
	

	public float processDegree(float degree) {
		return (degree + 720) % 360;
    }
	
	
	
}
