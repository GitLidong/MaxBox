package com.lidong.maxbox.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.lidong.maxbox.MainActivity;
import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.UnitBottomSelectedAdapter;
import com.lidong.maxbox.adapter.UnitSelectedShowAdapter;
import com.lidong.maxbox.adapter.UnitSpinnerAdapter;
import com.lidong.maxbox.myinterface.UnitBottomClickCallback;

import java.util.Calendar;

public class UnitConverterActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner unit_spinner;
    private UnitSpinnerAdapter unitSpinnerAdapter;

    private RecyclerView unit_recylcerview_select;
    private UnitBottomSelectedAdapter unitBottomSelectedAdapter;

    private RecyclerView unit_recyclerview_show;
    private UnitSelectedShowAdapter unitSelectedShowAdapter;

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);

        Calendar calendar = Calendar.getInstance();


        initView();
    }

    private void initView() {

        //底部选择那个方面的单位
        unit_recylcerview_select = (RecyclerView) findViewById(R.id.unit_recylcerview_select);
        unitBottomSelectedAdapter = new UnitBottomSelectedAdapter(callback);
        unit_recylcerview_select.setAdapter(unitBottomSelectedAdapter);

        //选择单位
        unit_spinner = (Spinner) findViewById(R.id.unit_spinner);
        unitSpinnerAdapter = new UnitSpinnerAdapter(this,android.R.layout.simple_spinner_item,0,0,null);
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        unit_spinner.setAdapter(unitSpinnerAdapter);

        //展示换算
        unit_recyclerview_show = (RecyclerView) findViewById(R.id.unit_recyclerview_show);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        unit_recyclerview_show.setLayoutManager(linearLayoutManager);
        unitSelectedShowAdapter = new UnitSelectedShowAdapter(0);
        unit_recyclerview_show.setAdapter(unitSelectedShowAdapter);

        //返回键
        back = (Button) findViewById(R.id.unit_converter_title_back);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.unit_converter_title_back:
                finish();
                break;
            default:
                break;
        }
    }

    private UnitBottomClickCallback callback = new UnitBottomClickCallback() {
        @Override
        public void changeView(int position) {
            unitSpinnerAdapter = new UnitSpinnerAdapter(UnitConverterActivity.this,android.R.layout.simple_spinner_item,0,position,null);
            unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            unit_spinner.setAdapter(unitSpinnerAdapter);

            unitSelectedShowAdapter = new UnitSelectedShowAdapter(position);
            unit_recyclerview_show.setAdapter(unitSelectedShowAdapter);


        }
    };



}
