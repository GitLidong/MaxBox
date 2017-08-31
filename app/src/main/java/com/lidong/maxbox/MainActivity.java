package com.lidong.maxbox;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lidong.maxbox.adapter.MyMainFragmentAdapter;
import com.lidong.maxbox.fragments.MainFragment;
import com.lidong.maxbox.manager.ActivityFactory;
import com.lidong.maxbox.myinterface.ToolsClickCallback;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private ViewPager main_viewpager;
    private CircleIndicator indicator;

    private List<Fragment> listFragments;

    private MyMainFragmentAdapter myMainFragmentAdapterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){

        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
        indicator = (CircleIndicator)findViewById(R.id.indicator);

        listFragments = new ArrayList<>();
        listFragments.add(new MainFragment(0,toolsClickCallback));
        listFragments.add(new MainFragment(1,toolsClickCallback));

        myMainFragmentAdapterAdapter = new MyMainFragmentAdapter(getSupportFragmentManager(),listFragments);
        main_viewpager.setAdapter(myMainFragmentAdapterAdapter);
        indicator.setViewPager(main_viewpager);
    }

    private ToolsClickCallback toolsClickCallback = new ToolsClickCallback() {
        @Override
        public void switchActivity(int whichMenu, int position) {
            Activity activity = ActivityFactory.createActivityByMenuAndPosition(whichMenu,position);
            if(activity != null){
                Intent intent = new Intent(MainActivity.this,activity.getClass());
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"activity尚未创建",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void makeShortcut(String name) {
            String toast = name + getApplicationContext().getString(R.string.long_click_toast);
            Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_SHORT).show();
        }
    };
}
