package com.lidong.maxbox.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.MySoundLevelAdapter;
import com.lidong.maxbox.util.AudioRecordThread;

public class SoundMetelActivity extends AppCompatActivity {

    private String TAG = "SoundMetelActivity";

    private RecyclerView sound_level_recycle;
    private MySoundLevelAdapter mySoundLevelAdapter;
    private LinearLayoutManager linearLayoutManager;

    private Button sound_title_back;
    private TextView sound_level_show;
    private AudioRecordHandler myHandler;
    private AudioRecordThread myRunnable;
    private Thread myThread;

    private ImageView sound_level_pointer;

    private RotateAnimation rotateAnimation;
    private float lastAngle;
    private float nowAngle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_metel);
        Log.i(TAG,"SoundMetelActivity onCreate() and init some data");
        init();
    }

    private void init() {
        sound_level_pointer = (ImageView) findViewById(R.id.sound_level_pointer);

        sound_level_recycle = (RecyclerView) findViewById(R.id.sound_level_recycle);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        sound_level_recycle.setLayoutManager(linearLayoutManager);

        mySoundLevelAdapter = new MySoundLevelAdapter(getApplicationContext());
        sound_level_recycle.setAdapter(mySoundLevelAdapter);

        sound_title_back = (Button) findViewById(R.id.sound_title_back);
        sound_title_back.setOnClickListener(onClick);

        sound_level_show = (TextView) findViewById(R.id.sound_level_show);

        initThread();

        lastAngle = 0f;
    }

    private void initThread() {
        myHandler = new AudioRecordHandler();
        myRunnable = new AudioRecordThread(myHandler);
        myRunnable.startAudioRecordThread();

        this.myThread = new Thread(myRunnable);
        myThread.start();

    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sound_title_back:
                    finish();
                    myRunnable.stopAudioRecordThread();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"SoundMetelActivity onDestroy() !!!");
        if(myThread != null) {
            myThread.interrupt();
            Log.i(TAG," myThread interrupt by my function");
        }else {
            Log.i(TAG," myThread interrupt because its work all been done!!!");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG,"SoundMetelActivity onBackPressed() !!!");
        myRunnable.stopAudioRecordThread();
    }

    public class AudioRecordHandler extends Handler{

        private int volume;

        public AudioRecordHandler() {
            super();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 6666:
                    Bundle bundle = msg.getData();
                    volume = (int) bundle.get("final_volume");
                    sound_level_show.setText(volume+"");
                    nowAngle = volume * 2;
                    rotateAnimation = new RotateAnimation(
                            lastAngle,nowAngle,
                            Animation.RELATIVE_TO_SELF, 1.0f,
                            Animation.RELATIVE_TO_SELF,0f);
                    //动画执行完后是否停留在执行完的状态
                    rotateAnimation.setFillAfter(true);
                    sound_level_pointer.setAnimation(rotateAnimation);
                    rotateAnimation.start();
                    lastAngle = nowAngle;
                    break;
            }
        }
    }
}
