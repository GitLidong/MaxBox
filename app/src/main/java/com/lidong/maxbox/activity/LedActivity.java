package com.lidong.maxbox.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.MyLedAdapter;

import java.util.ArrayList;
import java.util.List;

public class LedActivity extends AppCompatActivity {

    private List<Integer> listBitmapIDs;
    private RecyclerView led_recycleview;
    private MyLedAdapter myLedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);
        init();
    }

    private void init(){
        listBitmapIDs = new ArrayList<>();
        listBitmapIDs.add(0);
        listBitmapIDs.add(R.drawable.led_ico_android);
        listBitmapIDs.add(R.drawable.led_ico_happy);
        listBitmapIDs.add(R.drawable.led_ico_mail);
        listBitmapIDs.add(R.drawable.led_ico_heart);
        listBitmapIDs.add(R.drawable.led_ico_sos);
        listBitmapIDs.add(R.drawable.led_ico_beer);
        listBitmapIDs.add(R.drawable.led_ico_bean);
        listBitmapIDs.add(0);


        led_recycleview = (RecyclerView) findViewById(R.id.led_recycleview);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        led_recycleview.setLayoutManager(layoutManager);

        myLedAdapter = new MyLedAdapter(listBitmapIDs);
        led_recycleview.setAdapter(myLedAdapter);
        led_recycleview.scrollToPosition(4);
    }
}
