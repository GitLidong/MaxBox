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

/*
 * 主界面的切换由 ViewPager通过 FragmentPagerAdapter 加载 两个 Fragment 展示
 *
 * 这两个 Fragment 的界面 由 RecyclerView 组成，网格布局，每行 三个；
 * 通过 继承自RecyclerView.Adapter 的自定义的 MyToolsMenuAdapter 加载不同的功能，
 * MyToolsMenuAdapter 里面传入 一个自定义的接口 ToolsClickCallback，用于监听 点击事件是在哪个菜单的哪个位置
 *
 * ToolsClickCallback 实现是在该主界面中，通过回调点击事件的位置，
 * 使用 ActivityFactory.createActivityByMenuAndPosition（） 方法来创建对应的跳转界面。
 *
 * 这样实现的目的 是为了增加 可拓展性，
 *
 * 如果需要增加更多的展示界面，或者是 每个展示界面上 增加更多的功能。
 * 只需要修改相应的 Adapter 即可。
 *
 * 而相应的界面跳转 只需要在 ActivityFactory 中注册一下即可。
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager main_viewpager;
    private CircleIndicator indicator;

    private List<Fragment> listFragments;

    private MyMainFragmentAdapter myMainFragmentAdapterAdapter;

    private Activity activity;

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

    /*
     * 自定义接口并实现，将它传入 MainFragment 中，用于回调点击事件的位置
     */
    private ToolsClickCallback toolsClickCallback = new ToolsClickCallback() {

        @Override
        public void switchActivity(int whichMenu, int position) {
            activity = ActivityFactory.createActivityByMenuAndPosition(whichMenu,position);
            if(activity != null){
                Intent intent = new Intent(MainActivity.this,activity.getClass());
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"activity尚未创建",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void makeShortcut(String name,int icon) {
            String toast = name + getApplicationContext().getString(R.string.long_click_toast);
            Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_SHORT).show();
        }
    };
    /*
    public void createShortCut(String name,int micon, Activity activity){
        //创建快捷方式的Intent
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        shortcutintent.putExtra("duplicate", false);
        //需要现实的名称
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), micon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //点击快捷图片，运行的程序主入口
        //shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext() , activity.class));
        //发送广播。OK
        sendBroadcast(shortcutintent);
    }
    */
}
