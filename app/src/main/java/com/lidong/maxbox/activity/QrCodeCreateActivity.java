package com.lidong.maxbox.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.QrcodeShowAdapter;
import com.lidong.maxbox.database.MyDatabaseHelper;
import com.lidong.maxbox.database.QrcodeData;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by ubuntu on 17-9-13.
 */

public class QrCodeCreateActivity extends Activity implements View.OnClickListener{
    private String TAG = "QrCodeCreateActivity";
    private Button back;
    private Button add;

    private RecyclerView recyclerViewShowQrcode;
    private QrcodeShowAdapter qrcodeShowAdapter;

    private List<QrcodeData> qrcodeDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enqrcode);
        initView();
        initDatabase();
    }

    private void initView() {
        back = (Button) findViewById(R.id.back);
        add = (Button) findViewById(R.id.add);
        back.setOnClickListener(this);
        add.setOnClickListener(this);

        recyclerViewShowQrcode = (RecyclerView) findViewById(R.id.recycleview_showdata);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewShowQrcode.setLayoutManager(linearLayoutManager);

        qrcodeDataList = MyDatabaseHelper.getAllData();
        if (qrcodeDataList.size() >0) {
            qrcodeShowAdapter = new QrcodeShowAdapter(qrcodeDataList);
            recyclerViewShowQrcode.setAdapter(qrcodeShowAdapter);
        }
    }

    private void initDatabase() {
        /*
        * 使用第三方库 litepal，具体使用参照
        *  https://github.com/LitePalFramework/LitePal
         */
        LitePal.getDatabase();
        Log.i(TAG,"LitaPal create database success!!");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                Intent intent1 =new Intent(QrCodeCreateActivity.this,QRcodeActivity.class);
                startActivity(intent1);
                break;
            case R.id.add:
                Intent intent2 = new Intent(QrCodeCreateActivity.this,QrcodePickActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
