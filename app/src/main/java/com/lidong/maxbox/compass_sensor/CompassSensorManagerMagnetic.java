package com.lidong.maxbox.compass_sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class CompassSensorManagerMagnetic extends CompassSensorManager {

	private Sensor accelermeterSensor;
    private Sensor magneticFieldSensor;
	
    private float[] aValues = new float[3]; 
    private float[] mValues = new float[3]; 
    private float[] values = new float[3]; 
    private float[] oriental = new float[9]; 
	
	
	
    private  SensorEventListener mSensorEventListener = new SensorEventListener() {
		 @Override
	        public void onSensorChanged(SensorEvent event) {

			 
	            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
	            	 mValues = event.values; 
	            }
	            
		        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
		            aValues = event.values;
		        }
		        
		        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD ||
		        	event.sensor.getType() == Sensor.TYPE_ACCELEROMETER	)
		        {

		          SensorManager.getRotationMatrix(oriental, null, aValues, mValues);
		          SensorManager.getOrientation(oriental, values);
		          float compassValue = (float)Math.toDegrees(values[0]);
		          float direction =compassValue * -1.0f;
		          mCurrentDirection = processDegree(direction); 
		         
		        }

	        }
		 
		 @Override
	        public void onAccuracyChanged(Sensor sensor, int accuracy) {
	        }
		 
	 };
    
    
    
    
    
    public CompassSensorManagerMagnetic(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
		if(mSensorManager!= null){
			accelermeterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		    magneticFieldSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		}

	}

	
	
	
	
	
	
	@Override
	public void registerSensorListener() {
		// TODO Auto-generated method stub
		if(accelermeterSensor!=null && magneticFieldSensor!=null && mActivity!=null){
			mSensorManager.registerListener(mSensorEventListener, accelermeterSensor, SensorManager.SENSOR_DELAY_NORMAL); 
			mSensorManager.registerListener(mSensorEventListener, magneticFieldSensor,SensorManager.SENSOR_DELAY_NORMAL); 
		}else{
			Toast.makeText(mActivity, "llllll", Toast.LENGTH_SHORT)
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




    @Override
	public float processDegree(float degree) {
		// TODO Auto-generated method stub
		return (degree + 360) % 360;
	}









}
