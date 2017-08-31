package com.lidong.maxbox;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.lidong.maxbox.adapter.MyMainFragmentAdapter;
import com.lidong.maxbox.fragments.MainFragment1;
import com.lidong.maxbox.fragments.MainFragment2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager main_viewpager;
    private List<Fragment> listFragments;
    private MyMainFragmentAdapter myAdapter;
    private RadioButton main_radio1,main_radio2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSize();
        init();

    }

    private void getSize(){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Log.i("MainActivity","W H"+wm.getDefaultDisplay().getWidth()+" "+wm.getDefaultDisplay().getHeight());
    }

    private void init(){

        main_radio1 = (RadioButton) findViewById(R.id.main_radio1);
        main_radio2 = (RadioButton) findViewById(R.id.main_radio2);

        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
        main_viewpager.setOnPageChangeListener(onPageChangeListener);


        listFragments = new ArrayList<>();

        listFragments.add(new MainFragment1());
        listFragments.add(new MainFragment2());

        myAdapter = new MyMainFragmentAdapter(getSupportFragmentManager(),listFragments);
        main_viewpager.setAdapter(myAdapter);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            switch (position){
                case 0:
                    main_radio1.setChecked(true);
                    main_radio2.setChecked(false);
                    break;
                case 1:
                    main_radio1.setChecked(false);
                    main_radio2.setChecked(true);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
