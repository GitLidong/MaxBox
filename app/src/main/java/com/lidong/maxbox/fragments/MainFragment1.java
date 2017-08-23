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
import com.lidong.maxbox.activity.LedActivity;


/**
 * Created by ubuntu on 17-8-22.
 */

public class MainFragment1 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment1,container,false);

        Button btn_led = (Button) view.findViewById(R.id.btn_led);
        LinearLayout layout_led = (LinearLayout) view.findViewById(R.id.lauout_led);

        layout_led.setOnClickListener(onViewClicked);
        btn_led.setOnClickListener(onViewClicked);
        layout_led.setOnTouchListener(onViewTouch);

        return view;
    }

    private View.OnClickListener onViewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("MainFragment1","btnClicked");
            Intent intent = new Intent(getActivity(), LedActivity.class);
            startActivity(intent);

        }
    };

    private View.OnTouchListener onViewTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundColor(Color.GREEN);
                    break;
                case MotionEvent.ACTION_UP:
                    v.setBackgroundColor(Color.parseColor("#50FFFFFF"));
                    break;
            }
            return false;
        }
    };

}
