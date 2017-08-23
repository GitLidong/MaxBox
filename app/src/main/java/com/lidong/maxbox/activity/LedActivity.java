package com.lidong.maxbox.activity;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.MyLedAdapter;
import com.lidong.maxbox.myinterface.ViewCallback;

import java.util.ArrayList;
import java.util.List;

public class LedActivity extends AppCompatActivity {

    private List<Integer> listBitmapIDs;
    private RecyclerView led_recycleview;
    private MyLedAdapter myLedAdapter;
    private ImageView led_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);
        init();
    }

    private void init(){
        led_image = (ImageView) findViewById(R.id.led_image);

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

        myLedAdapter = new MyLedAdapter(listBitmapIDs,viewCallback);
        led_recycleview.setAdapter(myLedAdapter);

    }

    private ViewCallback viewCallback = new ViewCallback() {
        @Override
        public void setPostion(int start,int end) {
            Log.i("lidong","position "+(end-start));
        }

        @Override
        public void setView(int position) {
            //led_image.setImageBitmap(BitmapFactory.decodeResource(getResources(),listBitmapIDs.get(position)));

        }
    };
}
