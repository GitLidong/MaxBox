package com.lidong.maxbox.activity.QrcodeSonAcitvity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidong.maxbox.R;
import com.lidong.maxbox.database.MyDatabaseHelper;
import com.lidong.maxbox.database.QrcodeData;
import com.lidong.maxbox.manager.ActivityCollector;
import com.lidong.maxbox.manager.MyActivity;
import com.lidong.maxbox.util.EncodingUtils;
import com.lidong.maxbox.util.ImageInfoBean;


/**
 * Created by ubuntu on 17-9-14.
 */

public class UrlActivity extends MyActivity implements View.OnClickListener{
    private Button delete;
    private Button add;
    private EditText text;
    private Bitmap bitmap;
    private TextView text_info;
    private String texts;
    private TextView type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textencode);
        initView();
    }

    private void initView() {
        type = (TextView) findViewById(R.id.textoftype);
        type.setText("Url");
        text_info = (TextView) findViewById(R.id.textinfo);
        text_info.setText("Input url");
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        text = (EditText) findViewById(R.id.edit_text);
        text.setText("http://");
        text.setMaxLines(50);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                ActivityCollector.removeActivity(this);
                break;
            case R.id.add:
                texts = text.getText().toString();
                if (texts.equals("")) {
                    Toast.makeText(UrlActivity.this,"The input is empty",Toast.LENGTH_SHORT).show();
                } else {
                    bitmap = EncodingUtils.createQRCode_text(texts.trim());
                    String uri = EncodingUtils.createImageFromBitmap(bitmap);

                    QrcodeData qrcodeData = new QrcodeData();
                    qrcodeData.setQrName("Url");
                    qrcodeData.setQrContent(text.getText().toString());
                    qrcodeData.setImageFile(uri);
                    MyDatabaseHelper.addData(qrcodeData);

                    Intent intent = new Intent(UrlActivity.this,DisplayQrcodeActivity.class);
                    ImageInfoBean dto = new ImageInfoBean();
                    dto.setDescription("Content:"+texts);
                    dto.setUri(uri);
                    intent.putExtra("encode_text", dto);
                    intent.putExtra("type","Url");
                    intent.putExtra("jump",0);
                    startActivity(intent);
                }
                break;
        }
    }
}
