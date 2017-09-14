package com.lidong.maxbox.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.lidong.maxbox.R;

/**
 * Created by ubuntu on 17-9-13.
 */

public class QrCodeCreateActivity extends Activity implements View.OnClickListener{
    private Button back;
    private Button add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enqrcode);
        initView();
    }

    private void initView() {
        back = (Button) findViewById(R.id.back);
        add = (Button) findViewById(R.id.add);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
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
