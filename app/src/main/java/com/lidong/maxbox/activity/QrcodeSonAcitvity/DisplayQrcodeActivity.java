package com.lidong.maxbox.activity.QrcodeSonAcitvity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.manager.MyActivity;
import com.lidong.maxbox.util.ImageInfoBean;

import java.io.FileNotFoundException;

/**
 * Created by ubuntu on 17-9-14.
 * 这个界面用于查看具体的某个 二维码
 */

public class DisplayQrcodeActivity extends MyActivity implements View.OnClickListener{
    private Button back;
    private TextView type;
    private String type_name;
    private TextView content;
    private ImageView encode;
    private int flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_dispaly);
        initView();
        backFillData();
    }

    private void initView() {
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        type = (TextView) findViewById(R.id.type_textview);
        type_name = (String) this.getIntent().getExtras().get("type");
        type.setText(type_name);
        content = (TextView) findViewById(R.id.content);
        encode = (ImageView) findViewById(R.id.image_code);
        flag = (int) this.getIntent().getExtras().get("jump");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                backToWhereFrom();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToWhereFrom();
    }

    private void backFillData() {
        Object obj = this.getIntent().getExtras().get("encode_text");
        try {
            if(obj != null && obj instanceof ImageInfoBean)
            {
                ImageInfoBean dto = (ImageInfoBean)obj;
                Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput(dto.getUri()));
                encode.setImageBitmap(bitmap);
                encode.invalidate(); // refresh

                content.setText(dto.getDescription());
                return;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * 从哪来回到哪去
     */
    private void backToWhereFrom() {
        if (flag == 0) {
            Intent intent1 = new Intent(DisplayQrcodeActivity.this,QrcodePickActivity.class);
            startActivity(intent1);
        }
        if (flag == 1) {
            Intent intent2 = new Intent(DisplayQrcodeActivity.this,QrCodeCreateActivity.class);
            startActivity(intent2);
        }
    }
}
