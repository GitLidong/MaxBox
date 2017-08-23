package com.lidong.maxbox.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lidong.maxbox.R;
import com.lidong.maxbox.activity.CompassMainActivity;
import com.lidong.maxbox.activity.LedActivity;


/**
 * Created by ubuntu on 17-8-22.
 */

public class MainFragment1 extends Fragment implements View.OnClickListener,
        View.OnTouchListener{

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment1,container,false);

        Button btn_led = (Button) view.findViewById(R.id.btn_led);
        LinearLayout layout_led = (LinearLayout) view.findViewById(R.id.lauout_led);
        LinearLayout layout_compass = (LinearLayout) view.findViewById(R.id.lauout_compass);
        layout_compass.setOnClickListener((View.OnClickListener) this);
        layout_led.setOnClickListener((View.OnClickListener) this);
        layout_led.setOnTouchListener(this);
        layout_compass.setOnTouchListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lauout_compass:
                Intent intent1 = new Intent(getActivity(), CompassMainActivity.class);
                startActivity(intent1);
                break;
            case R.id.lauout_led:
                Intent intent2 = new Intent(getActivity(), LedActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(Color.parseColor("#FFC125"));
                break;
            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(Color.parseColor("#FF8C00"));
                break;
        }
        return false;
    }
}
