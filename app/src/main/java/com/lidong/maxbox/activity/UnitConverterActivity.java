package com.lidong.maxbox.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;

import com.lidong.maxbox.MainActivity;
import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.UnitBottomSelectedAdapter;
import com.lidong.maxbox.adapter.UnitSelectedShowAdapter;
import com.lidong.maxbox.adapter.UnitSpinnerAdapter;
import com.lidong.maxbox.myinterface.UnitBottomClickCallback;

import java.util.Calendar;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class UnitConverterActivity extends AppCompatActivity {

    private EditText unit_num_input;

    private Spinner unit_spinner;
    private UnitSpinnerAdapter unitSpinnerAdapter;

    private RecyclerView unit_recylcerview_select;
    private UnitBottomSelectedAdapter unitBottomSelectedAdapter;

    private RecyclerView unit_recyclerview_show;
    private UnitSelectedShowAdapter unitSelectedShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);

        initView();
    }

    private void initView() {

        unit_num_input = (EditText) findViewById(R.id.unit_num_input);

        //底部选择那个方面的单位
        unit_recylcerview_select = (RecyclerView) findViewById(R.id.unit_recylcerview_select);
        unitBottomSelectedAdapter = new UnitBottomSelectedAdapter(callback);
        unit_recylcerview_select.setAdapter(unitBottomSelectedAdapter);

        //选择单位
        unit_spinner = (Spinner) findViewById(R.id.unit_spinner);
        unitSpinnerAdapter = new UnitSpinnerAdapter(UnitConverterActivity.this,0);
        unit_spinner.setAdapter(unitSpinnerAdapter);

        //展示换算
        unit_recyclerview_show = (RecyclerView) findViewById(R.id.unit_recyclerview_show);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        unit_recyclerview_show.setLayoutManager(linearLayoutManager);
        unitSelectedShowAdapter = new UnitSelectedShowAdapter(0);
        unit_recyclerview_show.setAdapter(unitSelectedShowAdapter);

    }

    private UnitBottomClickCallback callback = new UnitBottomClickCallback() {
        @Override
        public void changeView(int position) {
            unitSpinnerAdapter = new UnitSpinnerAdapter(UnitConverterActivity.this,position);
            unit_spinner.setAdapter(unitSpinnerAdapter);

            unitSelectedShowAdapter = new UnitSelectedShowAdapter(position);
            unit_recyclerview_show.setAdapter(unitSelectedShowAdapter);


        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
			/*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }

            unit_num_input.setText("success");
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
