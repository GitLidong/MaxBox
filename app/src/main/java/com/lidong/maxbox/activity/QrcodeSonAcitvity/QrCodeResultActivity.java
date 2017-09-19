package com.lidong.maxbox.activity.QrcodeSonAcitvity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidong.maxbox.R;
import com.lidong.maxbox.activity.QRcodeActivity;

/**
 * Created by ubuntu on 17-9-12.
 */

public class QrCodeResultActivity extends Activity implements View.OnClickListener{

    private TextView result;
    private Button back;
    private ImageView copy;
    private String qr_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_result);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        qr_result = intent.getStringExtra("result");
        result = (TextView) findViewById(R.id.qr_result);
        result.setText(qr_result);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        copy = (ImageView) findViewById(R.id.copy);
        copy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.copy:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", qr_result);
                // 将ClipData内容放到系统剪贴板里
                cm.setPrimaryClip(mClipData);
                Toast.makeText(QrCodeResultActivity.this,
                        getApplicationContext().getResources()
                                .getString(R.string.qrcode_result_copy_hint),
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}
