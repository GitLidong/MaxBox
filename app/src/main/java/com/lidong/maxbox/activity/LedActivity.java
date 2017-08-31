package com.lidong.maxbox.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;


import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.MyLedAdapter;
import com.lidong.maxbox.manager.CenterLayoutManager;
import com.lidong.maxbox.manager.FragmentFactory;
import com.lidong.maxbox.myinterface.LedCallback;

import java.util.ArrayList;
import java.util.List;

public class LedActivity extends AppCompatActivity {

    private List<Integer> listBitmapIDs;
    private RecyclerView led_recycleview;
    private MyLedAdapter myLedAdapter;
    private LinearLayoutManager layoutManager;

    private Fragment myFragment;

    private FrameLayout frame_layout;
    private int frame_layout_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);
        init();
    }

    private void init(){

        listBitmapIDs = new ArrayList<>();
        listBitmapIDs.add(R.drawable.led_ico_android);
        listBitmapIDs.add(R.drawable.led_ico_happy);
        listBitmapIDs.add(R.drawable.led_ico_mail);
        listBitmapIDs.add(R.drawable.led_ico_heart);
        listBitmapIDs.add(R.drawable.led_ico_sos);
        listBitmapIDs.add(R.drawable.led_ico_beer);
        listBitmapIDs.add(R.drawable.led_ico_bean);

        led_recycleview = (RecyclerView) findViewById(R.id.led_recycleview);

        layoutManager=new CenterLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        led_recycleview.setLayoutManager(layoutManager);

        myLedAdapter = new MyLedAdapter(listBitmapIDs,ledCallback);
        led_recycleview.setAdapter(myLedAdapter);

        frame_layout_id = R.id.frame_layout;
        frame_layout = (FrameLayout) findViewById(frame_layout_id);
        frame_layout.setOnClickListener(onClick);

        switchFragments(0);

    }

    private LedCallback ledCallback = new LedCallback() {
        @Override
        public void setPostion(int position) {
            layoutManager.smoothScrollToPosition(led_recycleview,null,position);
        }

        @Override
        public void setView(int position) {
            switchFragments(position);
        }

    };

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.led_recycleview) {

            }else{
                if(led_recycleview.getVisibility() == View.VISIBLE){
                    led_recycleview.setVisibility(View.INVISIBLE);
                }else {
                    led_recycleview.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private void switchFragments(int position){
        FragmentManager fragmentManager= getSupportFragmentManager();  //获得Fragment管理器
        FragmentTransaction transaction = fragmentManager.beginTransaction(); //开启一个事务

        myFragment = new FragmentFactory(position);

        transaction.replace(frame_layout_id,myFragment);
        transaction.commit();
    }

}
