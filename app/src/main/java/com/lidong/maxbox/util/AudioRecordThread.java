package com.lidong.maxbox.util;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.lidong.maxbox.activity.SoundMetelActivity;

/**
 * Created by ubuntu on 17-9-1.
 */

public class AudioRecordThread implements Runnable{

    private String TAG = "AudioRecordThread";

    private static final int SAMPLE_RATE_IN_HZ = 8000;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(
            SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);

    private AudioRecord mAudioRecord;

    private boolean isRunning;

    private Object mLock;

    private SoundMetelActivity.AudioRecordHandler myHandler;

    public AudioRecordThread(SoundMetelActivity.AudioRecordHandler myHandler) {
        mLock = new Object();
        this.myHandler = myHandler;
    }

    @Override
    public void run() {
        Log.i(TAG,"AudioRecordThread run begin !!!!!");
        mAudioRecord.startRecording();
        short[] buffer = new short[BUFFER_SIZE];

        while (isRunning) {
            //r是实际读取的数据长度，一般而言r会小于buffersize
            int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
            long v = 0;
            // 将 buffer 内容取出，进行平方和运算
            for (int i = 0; i < buffer.length; i++) {
                v += buffer[i] * buffer[i];
            }
            // 平方和除以数据总长度，得到音量大小。
            double mean = v / (double) r;
            double volume = 10 * Math.log10(mean);

            int final_volume = (int) volume +10;
            Log.i(TAG,"get noise is :  "+final_volume+"");
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("final_volume",final_volume);
            message.setData(bundle);
            message.what=6666;
            myHandler.sendMessage(message);
            // 大概一秒十次
            synchronized (mLock) {
                try {
                    mLock.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        mAudioRecord.stop();
        mAudioRecord.release();
        mAudioRecord = null;
        Log.i(TAG,"AudioRecordThread run over !!!!!");
    }

    public void startAudioRecordThread() {
        isRunning = true;
        Log.i(TAG,"AudioRecordThread startAudioRecordThread now function !!!!");
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        if (mAudioRecord == null) {
            Log.e(TAG, "AudioRecordThread AudioRecord 初始化失败");
        }
    }

    public void stopAudioRecordThread() {
        isRunning = false;
    }
}
