package com.lidong.maxbox.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.lidong.maxbox.MyApplication;
import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.MySizeAdapter;
import com.lidong.maxbox.manager.FullyLinearLayoutManager;
import com.lidong.maxbox.util.DividerItemDecoration;

/**
 * Created by ubuntu on 17-9-4.
 */

public class SizeActivity extends Activity implements TabHost.OnTabChangeListener,View.OnClickListener {

    private TabHost tabHost;
    private String Tab1 = "tab1";
    private String Tab2 = "tab2";
    private String Tab3 = "tab3";
    private String Tab4 = "tab4";
    private String Tab5 = "tab5";

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private MySizeAdapter mAdapter,mAdapter_jeans;
    private static String[][] shoe_woman = null;
    private static String[][] shoe_man = null;
    private static String[][] hat_woman = null;
    private static String[][] hat_man = null;
    private static String[][] clothes_woman = null;
    private static String[][] clothes_man = null;
    private static String[][] jeans_woman = null;
    private static String[][] jeans_man = null;
    private static String[][] underwear = null;
    private static String[][] ring = null;
    private String Tab = null;

    private Button woman;
    private Button man;
    private LinearLayout size_button;
    private ScrollView scrollingView;
    private View view = null;
    private View view_jeans = null;
    private LinearLayout size_title;
    private LinearLayout jeans;
    private LinearLayout.LayoutParams params;

    private View mRoot,tabIndicator1,tabIndicator2,tabIndicator3,tabIndicator4,tabIndicator5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        initData();
        initview();
        init();
        tabHost.setOnTabChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabHost.setCurrentTab(1);
        tabHost.setCurrentTab(0);
    }

    public void init() {
        FullyLinearLayoutManager mLayoutManager = new FullyLinearLayoutManager(this);
        mLayoutManager.setSmoothScrollbarEnabled(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL, 2,
                ContextCompat.getColor(this, R.color.divide_gray_color)));
        //创建默认的线性LayoutManager
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);


        FullyLinearLayoutManager mLayoutManager2 = new FullyLinearLayoutManager(this);
        mLayoutManager2.setSmoothScrollbarEnabled(true);
        jeans = (LinearLayout) findViewById(R.id.add_jeans);
        view_jeans = LayoutInflater.from(this).inflate(R.layout.jeans,null);
        jeans.addView(view_jeans);
        mRecyclerView2 = (RecyclerView)view_jeans.findViewById(R.id.my_recycler_view2);
        mRecyclerView2.addItemDecoration(new DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL, 2,
                ContextCompat.getColor(this, R.color.divide_gray_color)));
        //创建默认的线性LayoutManager
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setNestedScrollingEnabled(false);
        mAdapter_jeans = new MySizeAdapter(2,jeans_woman);
        mRecyclerView2.setAdapter(mAdapter_jeans);

        scrollingView = (ScrollView) findViewById(R.id.scrollView);
        scrollingView.smoothScrollTo(0,0);
        params= (LinearLayout.LayoutParams) scrollingView.getLayoutParams();
        size_button = (LinearLayout) findViewById(R.id.size_button);
        size_title = (LinearLayout) findViewById(R.id.size_title);
        woman = (Button) findViewById(R.id.woman);
        woman.setOnClickListener(this);
        man = (Button) findViewById(R.id.man);
        man.setOnClickListener(this);
        view = getLayoutInflater().inflate(R.layout.shoes_size,null);
        size_title.addView(view);
        mAdapter = new MySizeAdapter(1,shoe_woman);
        mRecyclerView.setAdapter(mAdapter);


        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec(Tab1).setIndicator(tabIndicator1)
                .setContent(R.id.tab));
        tabHost.addTab(tabHost.newTabSpec(Tab2).setIndicator(tabIndicator2)
                .setContent(R.id.tab));
        tabHost.addTab(tabHost.newTabSpec(Tab3).setIndicator(tabIndicator3)
                .setContent(R.id.tab));
        tabHost.addTab(tabHost.newTabSpec(Tab4).setIndicator(tabIndicator4)
                .setContent(R.id.tab));
        tabHost.addTab(tabHost.newTabSpec(Tab5).setIndicator(tabIndicator5)
                .setContent(R.id.tab));
        tabHost.setCurrentTab(0);   //防止重复点击 产生错误
    }

    @Override
    public void onTabChanged(String s) {
        size_title.removeAllViews();
        jeans.removeAllViews();
        switch (s){
            case "tab1":
                Tab = "tab1";
                view = getLayoutInflater().inflate(R.layout.shoes_size,null);
                size_title.addView(view);
                mAdapter = new MySizeAdapter(1,shoe_woman);
                mRecyclerView.setAdapter(mAdapter);
                size_button.setVisibility(View.VISIBLE);
                params.height = dip2px(MyApplication.getContext(),375);
                scrollingView.setLayoutParams(params);
                break;
            case "tab2":
                Tab = "tab2";
                view = getLayoutInflater().inflate(R.layout.hat_size,null);
                size_title.addView(view);
                mAdapter = new MySizeAdapter(0,hat_woman);
                mRecyclerView.setAdapter(mAdapter);
                size_button.setVisibility(View.VISIBLE);
                params.height = dip2px(MyApplication.getContext(),375);
                scrollingView.setLayoutParams(params);
                break;
            case "tab3":
                view = getLayoutInflater().inflate(R.layout.underwear_size,null);
                size_title.addView(view);
                mAdapter = new MySizeAdapter(0,underwear);
                mRecyclerView.setAdapter(mAdapter);
                size_button.setVisibility(View.GONE);
                params.height = dip2px(MyApplication.getContext(),432);
                scrollingView.setLayoutParams(params);

                break;
            case "tab4":
                Tab = "tab4";
                view = getLayoutInflater().inflate(R.layout.clothes_size,null);
                size_title.addView(view);
                jeans.addView(view_jeans);
                mAdapter = new MySizeAdapter(1,clothes_woman);
                mRecyclerView.setAdapter(mAdapter);
                size_button.setVisibility(View.VISIBLE);
                params.height = dip2px(MyApplication.getContext(),375);
                scrollingView.setLayoutParams(params);
                break;
            case "tab5":
                view = getLayoutInflater().inflate(R.layout.ring_size,null);
                size_title.addView(view);
                mAdapter = new MySizeAdapter(0,ring);
                mRecyclerView.setAdapter(mAdapter);
                size_button.setVisibility(View.GONE);
                params.height = dip2px(MyApplication.getContext(),432);
                scrollingView.setLayoutParams(params);
                break;
        }
    }

    private void initData() {
        shoe_woman = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_shoes_woman));
        shoe_man = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_shoes_man));
        hat_woman = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_hat_woman));
        hat_man = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_hat_man));
        underwear = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_underwear));
        clothes_woman = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_clothes_woman));
        clothes_man = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_clothes_man));
        jeans_woman = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_clothes_jeans_woman));
        jeans_man = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_clothes_jeans_man));
        ring = getArrayData(MyApplication.getContext().getResources()
                .getStringArray(R.array.tools_size_ring));
    }

    private void initview() {
        mRoot = getLayoutInflater().inflate(R.layout.activity_size, null);
        tabIndicator1 = LayoutInflater.from(this).inflate(R.layout.tab1,
                (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
        tabIndicator2 = LayoutInflater.from(this).inflate(R.layout.tab2,
                (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
        tabIndicator3 = LayoutInflater.from(this).inflate(R.layout.tab3,
                (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
        tabIndicator4 = LayoutInflater.from(this).inflate(R.layout.tab4,
                (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
        tabIndicator5 = LayoutInflater.from(this).inflate(R.layout.tab5,
                (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
    }

    public static String[][] getArrayData(String[] paramArrayOfString)
    {
        String[][] arrayOfString = new String[paramArrayOfString.length][paramArrayOfString[0]
                .split(":").length];
        for(int i = 0; i < paramArrayOfString.length; i++) {
            String[] itemArray = paramArrayOfString[i].split(":");
            for (int j = 0; j < itemArray.length; j++){
                arrayOfString[i][j] = itemArray[j];
            }
        }
        return  arrayOfString;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.woman:
                if (Tab.equals("tab1")) {
                    mAdapter = new MySizeAdapter(1,shoe_woman);
                    mRecyclerView.setAdapter(mAdapter);
                } else if (Tab.equals("tab2")) {
                    mAdapter = new MySizeAdapter(0,hat_woman);
                    mRecyclerView.setAdapter(mAdapter);
                } else if (Tab.equals("tab4")) {
                    mAdapter = new MySizeAdapter(1,clothes_woman);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter_jeans = new MySizeAdapter(2,jeans_woman);
                    mRecyclerView2.setAdapter(mAdapter_jeans);
                }
                break;
            case R.id.man:
                if (Tab.equals("tab1")) {
                    mAdapter = new MySizeAdapter(1,shoe_man);
                    mRecyclerView.setAdapter(mAdapter);
                } else if (Tab.equals("tab2")) {
                    mAdapter = new MySizeAdapter(0,hat_man);
                    mRecyclerView.setAdapter(mAdapter);
                } else if (Tab.equals("tab4")) {
                    mAdapter = new MySizeAdapter(1,clothes_man);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter_jeans = new MySizeAdapter(2,jeans_man);
                    mRecyclerView2.setAdapter(mAdapter_jeans);
                }
                break;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
