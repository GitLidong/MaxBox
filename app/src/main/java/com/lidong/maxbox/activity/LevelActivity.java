package com.lidong.maxbox.activity;

import android.app.ActionBar;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.views.LevelView;

public class LevelActivity extends AppCompatActivity implements SensorEventListener{

    // 定义水平仪的仪大圆盘
    private LevelView myview;
    // 定义Sensor管理器
    SensorManager mySM;
    // 定义显示栏　显示X Y　Z轴方向转过角度与当前方位
    private TextView tx, ty;
    private Sensor acc_sensor;
    private Sensor mag_sensor;
    // 加速度传感器数据
    float accValues[] = new float[3];
    // 地磁传感器数据
    float magValues[] = new float[3];
    // 旋转矩阵，用来保存磁场和加速度的数据
    float r[] = new float[9];
    // 模拟方向传感器的数据（原始数据为弧度）
    float values[] = new float[3];

    private boolean level_lock_Press_Flag = false;
    private boolean zero_bn_Press_Flag = false;

    ImageButton level_lock_bn_view;
    ImageButton zero_bn_view;
    Button backBn;

    double xZeroAngle,yZeroAngle;
    double pitchAngle,rollAngle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        //获取水平仪的主组件
        myview=(LevelView)findViewById(R.id.level_show);

        tx = (TextView) findViewById(R.id.tvv_horz);
        ty = (TextView) findViewById(R.id.tvv_vertical);
        // 获取手机传感器管理服务
        mySM = (SensorManager) getSystemService(SENSOR_SERVICE);

        //为level_lock_off的ImageButton设置点击事件
        level_lock_bn_view=(ImageButton)findViewById(R.id.level_lock_off);
        level_lock_bn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level_lock_Press_Flag == false) {
                    level_lock_bn_view.setImageResource(R.drawable.tools_level_lock_on);
                    level_lock_Press_Flag = true;
                    mySM.unregisterListener(LevelActivity.this);
                } else {
                    acc_sensor = mySM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    mag_sensor = mySM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    // 给传感器注册监听：
                    mySM.registerListener(LevelActivity.this, acc_sensor, SensorManager.SENSOR_DELAY_GAME);
                    mySM.registerListener(LevelActivity.this, mag_sensor, SensorManager.SENSOR_DELAY_GAME);
                    level_lock_bn_view.setImageResource(R.drawable.tools_level_lock_off);
                    level_lock_Press_Flag = false;
                }
            }
        });

        //为zero_button的ImageButton设置点击事件
        zero_bn_view=(ImageButton)findViewById(R.id.zero_button);
        zero_bn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zero_bn_Press_Flag == false) {
                    zero_bn_view.setImageResource(R.drawable.tools_level_btn_level_on);
                    zero_bn_Press_Flag = true;
                    xZeroAngle=rollAngle;
                    yZeroAngle=pitchAngle;

                } else {
                    zero_bn_view.setImageResource(R.drawable.tools_level_btn_level_off);
                    zero_bn_Press_Flag = false;
                }
            }
        });

        //返回按钮
        backBn=(Button)findViewById(R.id.btn_back);
        backBn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mySM.unregisterListener(LevelActivity.this);
                finish();
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        //为系统的方向传感器注册监听器
        acc_sensor = mySM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mag_sensor = mySM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // 给传感器注册监听：
        mySM.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_GAME);
        mySM.registerListener(this, mag_sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        // 取消方向传感器的监听
        mySM.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        // 取消方向传感器的监听
        mySM.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 获取手机触发event的传感器的类型

        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accValues = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magValues = event.values.clone();
                break;

        }

        SensorManager.getRotationMatrix(r, null, accValues, magValues);
        SensorManager.getOrientation(r, values);

        // 获取　沿着Z轴转过的角度
        double zAngle = (int) Math.toDegrees(values[0]);
        //tz.setText(zAngle);

        // 获取　沿着X轴倾斜时　与Y轴的夹角
        pitchAngle = ((int) (Math.toDegrees(values[1])*10)/10.0);
        double yAngle=pitchAngle;
        if (true==zero_bn_Press_Flag){
            //捕捉到的倾角在第一象限时
            if (yZeroAngle>=0&&yZeroAngle<=90) {
                //xAngle=rollAngle-xZeroAngle;
                if(pitchAngle>=yZeroAngle&&pitchAngle<=90+yZeroAngle){
                    yAngle=pitchAngle-yZeroAngle;
                }else if (pitchAngle>90+yZeroAngle&&pitchAngle<=180) {
                    yAngle=180-pitchAngle+yZeroAngle;
                } else if (pitchAngle>-180&&pitchAngle<=-180+yZeroAngle){
                    yAngle=yZeroAngle-180-pitchAngle;
                } else if (pitchAngle>=-180+yZeroAngle&&pitchAngle<=-90+yZeroAngle){
                    yAngle=-180-pitchAngle+yZeroAngle;
                } else if (pitchAngle>=-90+yZeroAngle&&pitchAngle<0){
                    yAngle=pitchAngle-yZeroAngle;
                } else if (pitchAngle<=yZeroAngle&&pitchAngle>=0) {
                    yAngle=pitchAngle-yZeroAngle;
                }
            }else if (yZeroAngle>90&&yZeroAngle<=180){
                //否则捕捉到的倾角在第二象限时
                if(pitchAngle>yZeroAngle&&pitchAngle<=180){
                    yAngle=pitchAngle-yZeroAngle;
                }else if (pitchAngle>=-180&&pitchAngle<=-270+yZeroAngle){
                    yAngle=360+pitchAngle-yZeroAngle;
                } else if (pitchAngle>-270+yZeroAngle&&pitchAngle<=-180+yZeroAngle){
                    yAngle=-pitchAngle-180+yZeroAngle;
                } else if (pitchAngle>-180+yZeroAngle&&pitchAngle<=0){
                    yAngle=-180-pitchAngle+yZeroAngle;
                } else if (pitchAngle<=yZeroAngle-90&&pitchAngle>0) {
                    yAngle=-180-pitchAngle+yZeroAngle;
                } else if (pitchAngle>yZeroAngle-90&&pitchAngle<=yZeroAngle){
                    yAngle=pitchAngle-yZeroAngle;
                }
            } else if (yZeroAngle>-90&&yZeroAngle<0) {
                //否则捕捉到的倾角在第四象限时
                if(pitchAngle>=0&&pitchAngle<=90+yZeroAngle){
                    yAngle=pitchAngle-yZeroAngle;
                }else if (pitchAngle>90+yZeroAngle&&pitchAngle<=180+yZeroAngle) {
                    yAngle=180-pitchAngle+yZeroAngle;
                } else if (pitchAngle<=180&&pitchAngle>=180+yZeroAngle){
                    yAngle=180-pitchAngle+yZeroAngle;
                } else if (pitchAngle>=-180&&pitchAngle<=-90+yZeroAngle){
                    yAngle=-180-pitchAngle+yZeroAngle;
                } else if (pitchAngle>-90+yZeroAngle&&pitchAngle<yZeroAngle){
                    yAngle=pitchAngle-yZeroAngle;
                } else if (pitchAngle>=yZeroAngle&&pitchAngle<0) {
                    yAngle=pitchAngle-yZeroAngle;
                }
            } else if (yZeroAngle<=-90&&yZeroAngle>=-180){
                //否则捕捉到的倾角在第三象限时
                if(pitchAngle>=0&&pitchAngle<=180+yZeroAngle){
                    yAngle=180-pitchAngle+yZeroAngle;
                }else if (pitchAngle>180+yZeroAngle&&pitchAngle<=270+yZeroAngle){
                    yAngle=180-rollAngle+yZeroAngle;
                } else if (pitchAngle>270+yZeroAngle&&pitchAngle<=180){
                    yAngle=-360-yZeroAngle+rollAngle;
                } else if (pitchAngle>=-180&&pitchAngle<=yZeroAngle){
                    yAngle=pitchAngle-yZeroAngle;
                } else if (pitchAngle>yZeroAngle&&pitchAngle<yZeroAngle+90) {
                    yAngle=pitchAngle-yZeroAngle;
                } else if (pitchAngle>=yZeroAngle+90&&pitchAngle<0){
                    yAngle=180+yZeroAngle-pitchAngle;
                }
            }
        }
        else{
            //yAngle=-yAngle;
            if(yAngle>=90&&yAngle<=180){
                yAngle=180-yAngle;
            }else if (yAngle<=-90&&yAngle>=-180){
                yAngle=-180-yAngle;
            }
            yAngle=((int)(yAngle*10))/10.0;
        }

        // 获取　沿着Y轴的滚动时　与X轴的角度
        rollAngle=((int) (Math.toDegrees(-values[2])*10))/10.0;
        double xAngle=rollAngle;

        if (true==zero_bn_Press_Flag){
            //捕捉到的倾角在第一象限时
            if (xZeroAngle>=0&&xZeroAngle<=90) {
                //xAngle=rollAngle-xZeroAngle;
                if(rollAngle>=xZeroAngle&&rollAngle<=90+xZeroAngle){
                    xAngle=rollAngle-xZeroAngle;
                }else if (rollAngle>90+xZeroAngle&&rollAngle<=180) {
                    xAngle=180-rollAngle+xZeroAngle;
                } else if (rollAngle>-180&&rollAngle<=-180+xZeroAngle){
                    xAngle=xZeroAngle-180-rollAngle;
                } else if (rollAngle>=-180+xZeroAngle&&rollAngle<=-90+xZeroAngle){
                    xAngle=-180-rollAngle+xZeroAngle;
                } else if (rollAngle>=-90+xZeroAngle&&rollAngle<0){
                    xAngle=rollAngle-xZeroAngle;
                } else if (rollAngle<=xZeroAngle&&rollAngle>=0) {
                    xAngle=rollAngle-xZeroAngle;
                }
            }else if (xZeroAngle>90&&xZeroAngle<=180){
                //否则捕捉到的倾角在第二象限时
                if(rollAngle>xZeroAngle&&rollAngle<=180){
                    xAngle=rollAngle-xZeroAngle;
                }else if (rollAngle>=-180&&rollAngle<=-270+xZeroAngle){
                    xAngle=360+rollAngle-xZeroAngle;
                } else if (rollAngle>-270+xZeroAngle&&rollAngle<=-180+xZeroAngle){
                    xAngle=-rollAngle-180+xZeroAngle;
                } else if (rollAngle>-180+xZeroAngle&&rollAngle<=0){
                    xAngle=-180-rollAngle+xZeroAngle;
                } else if (rollAngle<=xZeroAngle-90&&rollAngle>0) {
                    xAngle=-180-rollAngle+xZeroAngle;
                } else if (rollAngle>xZeroAngle-90&&rollAngle<=xZeroAngle){
                    xAngle=rollAngle-xZeroAngle;
                }
            } else if (xZeroAngle>-90&&xZeroAngle<0) {
                //否则捕捉到的倾角在第四象限时
                if(rollAngle>=0&&rollAngle<=90+xZeroAngle){
                    xAngle=rollAngle-xZeroAngle;
                }else if (rollAngle>90+xZeroAngle&&rollAngle<=180+xZeroAngle) {
                    xAngle=180-rollAngle+xZeroAngle;
                } else if (rollAngle<=180&&rollAngle>=180+xZeroAngle){
                    xAngle=180-rollAngle+xZeroAngle;
                } else if (rollAngle>=-180&&rollAngle<=-90+xZeroAngle){
                    xAngle=-180-rollAngle+xZeroAngle;
                } else if (rollAngle>-90+xZeroAngle&&rollAngle<xZeroAngle){
                    xAngle=rollAngle-xZeroAngle;
                } else if (rollAngle>=xZeroAngle&&rollAngle<0) {
                    xAngle=rollAngle-xZeroAngle;
                }
            } else if (xZeroAngle<=-90&&xZeroAngle>=-180){
                //否则捕捉到的倾角在第三象限时
                if(rollAngle>=0&&rollAngle<=180+xZeroAngle){
                    xAngle=180-rollAngle+xZeroAngle;
                }else if (rollAngle>180+xZeroAngle&&rollAngle<=270+xZeroAngle){
                    xAngle=180-rollAngle+xZeroAngle;
                } else if (rollAngle>270+xZeroAngle&&rollAngle<=180){
                    xAngle=-360-xZeroAngle+rollAngle;
                } else if (rollAngle>=-180&&rollAngle<=xZeroAngle){
                    xAngle=rollAngle-xZeroAngle;
                } else if (rollAngle>xZeroAngle&&rollAngle<xZeroAngle+90) {
                    xAngle=rollAngle-xZeroAngle;
                } else if (rollAngle>=xZeroAngle+90&&rollAngle<0){
                    xAngle=180+xZeroAngle-rollAngle;
                }
            }

        }
        else{
            //xAngle是变换后的要显示的角度
            if(xAngle>=90&&xAngle<=180){
                xAngle=180-xAngle;
            }else if (xAngle<=-90&&xAngle>=-180){
                xAngle=-180-xAngle;
            }
            xAngle=((int)(xAngle*10))/10.0;
        }
        //更新view
        if(true==zero_bn_Press_Flag){
            tx.setText(String.valueOf((int)(xAngle)*10/10.0) + "°");
            ty.setText(String.valueOf((int)(-yAngle)*10/10.0) + "°");
            myview.onChangeXY(zAngle, yAngle, xAngle);
        }else {
            tx.setText(String.valueOf(xAngle) + "°");
            ty.setText(String.valueOf(-yAngle) + "°");
            myview.onChangeXY(zAngle, yAngle, xAngle);
        }

    }
    private double onAngleChanged(double ExAngle, double ZeroAngle){
        double AfAngle=0;
        if (true==zero_bn_Press_Flag){
            //捕捉到的倾角在第一象限时
            if (ZeroAngle>=0&&ZeroAngle<=90) {
                if(ExAngle>=ZeroAngle&&ExAngle<=90+ZeroAngle){
                    AfAngle=ExAngle-ZeroAngle;
                }else if (ExAngle>90+ZeroAngle&&ExAngle<=180) {
                    AfAngle=180-ExAngle+ZeroAngle;
                } else if (ExAngle>-180&&ExAngle<=-180+ZeroAngle){
                    AfAngle=ZeroAngle-180-ExAngle;
                } else if (ExAngle>=-180+ZeroAngle&&ExAngle<=-90+ZeroAngle){
                    AfAngle=-180-ExAngle+ZeroAngle;
                } else if (ExAngle>=-90+ZeroAngle&&ExAngle<0){
                    AfAngle=ExAngle-ZeroAngle;
                } else if (ExAngle<=ZeroAngle&&ExAngle>=0) {
                    AfAngle=ExAngle-ZeroAngle;
                }
            }else if (ZeroAngle>90&&ZeroAngle<=180){
                //否则捕捉到的倾角在第二象限时
                if(ExAngle>ZeroAngle&&ExAngle<=180){
                    AfAngle=ExAngle-ZeroAngle;
                }else if (ExAngle>=-180&&ExAngle<=-270+ZeroAngle){
                    AfAngle=360+ExAngle-ZeroAngle;
                } else if (ExAngle>-270+ZeroAngle&&ExAngle<=-180+ZeroAngle){
                    AfAngle=-ExAngle-180+ZeroAngle;
                } else if (ExAngle>-180+ZeroAngle&&ExAngle<=0){
                    AfAngle=-180-ExAngle+ZeroAngle;
                } else if (ExAngle<=ZeroAngle-90&&ExAngle>0) {
                    AfAngle=-180-ExAngle+ZeroAngle;
                } else if (ExAngle>ZeroAngle-90&&ExAngle<=ZeroAngle){
                    AfAngle=ExAngle-ZeroAngle;
                }
            } else if (ZeroAngle>-90&&ZeroAngle<0) {
                //否则捕捉到的倾角在第四象限时
                if(ExAngle>=0&&ExAngle<=90+ZeroAngle){
                    AfAngle=ExAngle-ZeroAngle;
                }else if (ExAngle>90+ZeroAngle&&ExAngle<=180+ZeroAngle) {
                    AfAngle=180-ExAngle+ZeroAngle;
                } else if (ExAngle<=180&&ExAngle>=180+ZeroAngle){
                    AfAngle=180-ExAngle+ZeroAngle;
                } else if (ExAngle>=-180&&ExAngle<=-90+ZeroAngle){
                    AfAngle=-180-ExAngle+ZeroAngle;
                } else if (ExAngle>-90+ZeroAngle&&ExAngle<ZeroAngle){
                    AfAngle=ExAngle-ZeroAngle;
                } else if (ExAngle>=ZeroAngle&&ExAngle<0) {
                    AfAngle=ExAngle-ZeroAngle;
                }
            } else if (ZeroAngle<=-90&&ZeroAngle>=-180){
                //否则捕捉到的倾角在第三象限时
                if(ExAngle>=0&&ExAngle<=180+ZeroAngle){
                    AfAngle=180-ExAngle+ZeroAngle;
                }else if (ExAngle>180+ZeroAngle&&ExAngle<=270+ZeroAngle){
                    AfAngle=180-ExAngle+ZeroAngle;
                } else if (ExAngle>270+ZeroAngle&&ExAngle<=180){
                    AfAngle=-360-ZeroAngle+ExAngle;
                } else if (ExAngle>=-180&&ExAngle<=ZeroAngle){
                    AfAngle=ExAngle-ZeroAngle;
                } else if (ExAngle>ZeroAngle&&ExAngle<ZeroAngle+90) {
                    AfAngle=ExAngle-ZeroAngle;
                } else if (ExAngle>=ZeroAngle+90&&ExAngle<0){
                    AfAngle=180+ZeroAngle-ExAngle;
                }
            }
        }
        else{
            if(ExAngle>=90&&ExAngle<=180){
                AfAngle=180-ExAngle;
            }else if (ExAngle<=-90&&ExAngle>=-180){
                AfAngle=-180-ExAngle;
            }
        }
        AfAngle=((int)(AfAngle*10))/10.0;
        return AfAngle;
    }
}
