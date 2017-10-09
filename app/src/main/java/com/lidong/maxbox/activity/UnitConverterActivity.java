package com.lidong.maxbox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.UnitBottomSelectedAdapter;
import com.lidong.maxbox.adapter.UnitSelectedShowAdapter;
import com.lidong.maxbox.adapter.UnitSpinnerAdapter;
import com.lidong.maxbox.myinterface.UnitBottomClickCallback;
import com.lidong.maxbox.MyApplication;


public class UnitConverterActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner unit_spinner;
    private UnitSpinnerAdapter unitSpinnerAdapter;

    private RecyclerView unit_recylcerview_select;
    private UnitBottomSelectedAdapter unitBottomSelectedAdapter;

    private RecyclerView unit_recyclerview_show;
    private UnitSelectedShowAdapter unitSelectedShowAdapter;

    private Button back;

    private EditText editText;

    private String str;

    static public int bottomUnit = 0;

    static public float inputNum = 1;

    static public int unitSelectedPosition;

    static public int bottonLastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);

        initView();
    }

    private void initView() {

        bottonLastPosition = 0;
        inputNum = 1;
        bottomUnit = 0;

        //底部选择那个方面的单位
        unit_recylcerview_select = (RecyclerView) findViewById(R.id.unit_recylcerview_select);
        unitBottomSelectedAdapter = new UnitBottomSelectedAdapter(callback);
        unit_recylcerview_select.setAdapter(unitBottomSelectedAdapter);
        if (unit_recylcerview_select==null){
            Log.d("gongwei", "initView: null point");
        }

        //选择单位
        unit_spinner = (Spinner) findViewById(R.id.unit_spinner);
        unit_spinner.setOnItemSelectedListener(spinnerItemSelected);
        unitSpinnerAdapter = new UnitSpinnerAdapter(this,android.R.layout.simple_spinner_item,0,bottomUnit,null);
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        unit_spinner.setAdapter(unitSpinnerAdapter);
        unitSelectedPosition = 0;

        //展示换算
        unit_recyclerview_show = (RecyclerView) findViewById(R.id.unit_recyclerview_show);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        unit_recyclerview_show.setLayoutManager(linearLayoutManager);
        unitSelectedShowAdapter = new UnitSelectedShowAdapter(bottomUnit,0,1);
        unit_recyclerview_show.setAdapter(unitSelectedShowAdapter);

        //返回键
        back = (Button) findViewById(R.id.unit_converter_title_back);
        back.setOnClickListener(this);

        //EditText设置
        editText = (EditText) findViewById(R.id.unit_edittext);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
                    inputNum = Float.parseFloat(editText.getText().toString());
                    //结果展示框更新,unitSelectedShowAdapter的三个参数分为为，底部选择的单位，spinner选定的单位位置，输入框输入数据;
                    unitSelectedShowAdapter = new UnitSelectedShowAdapter(bottomUnit,unitSelectedPosition,inputNum);
                    unit_recyclerview_show.setAdapter(unitSelectedShowAdapter);
                }
                return false;
            }
        });

       // UnitBottomSelectedAdapter.ViewHolder viewHolder = (UnitBottomSelectedAdapter.ViewHolder)unit_recylcerview_select.getChildViewHolder(unit_recylcerview_select.getChildAt(0));
       // viewHolder.cell_unit_select_back.setBackground(MyApplication.getContext().getDrawable(R.drawable.size_tool_tab_selected));

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private UnitBottomClickCallback callback = new UnitBottomClickCallback() {
        @Override
        public void changeView(int position) {

            //底部框背景更新
            UnitBottomSelectedAdapter.ViewHolder lastviewHolder = (UnitBottomSelectedAdapter.ViewHolder)unit_recylcerview_select.getChildViewHolder(unit_recylcerview_select.getChildAt(bottonLastPosition));
            lastviewHolder.cell_unit_select_back.setBackground(MyApplication.getContext().getDrawable(R.drawable.size_tool_tab_unselected));

            bottonLastPosition = position;

            UnitBottomSelectedAdapter.ViewHolder viewHolder = (UnitBottomSelectedAdapter.ViewHolder)unit_recylcerview_select.getChildViewHolder(unit_recylcerview_select.getChildAt(position));
            viewHolder.cell_unit_select_back.setBackground(MyApplication.getContext().getDrawable(R.drawable.size_tool_tab_selected));

            //初始化底部单位及文本框显示
            bottomUnit=position;
            unitSelectedPosition = 0;
            editText.setText("1");
            inputNum = Float.parseFloat(editText.getText().toString());

            //spinner框更新
            unitSpinnerAdapter = new UnitSpinnerAdapter(UnitConverterActivity.this,android.R.layout.simple_spinner_item,0,position,null);
            unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            unit_spinner.setAdapter(unitSpinnerAdapter);

            //结果展示框更新
            unitSelectedShowAdapter = new UnitSelectedShowAdapter(position,0,inputNum);
            unit_recyclerview_show.setAdapter(unitSelectedShowAdapter);

        }
    };

    //对spinner框内数值的选定进行监听
    private AdapterView.OnItemSelectedListener spinnerItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            unitSelectedPosition = position;
            inputNum = Float.parseFloat(editText.getText().toString());
            str = (String) unit_spinner.getSelectedItem();
            Log.d("gongwei", "changeView: str "+str+"; position: "+Integer.toString(position));
            //结果展示框更新,unitSelectedShowAdapter的三个参数分为为，底部选择的单位，spinner选定的单位位置，输入框输入数据;
            unitSelectedShowAdapter = new UnitSelectedShowAdapter(bottomUnit,position,inputNum);
            unit_recyclerview_show.setAdapter(unitSelectedShowAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
