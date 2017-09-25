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
 * Created by ubuntu on 17-9-17.
 */

public class SmsActivity extends MyActivity implements View.OnClickListener{

    private Button delete;
    private Button add;
    private EditText number;
    private EditText content;
    private Bitmap bitmap;
    private TextView text_info;
    private String texts;
    private TextView type;
    private String numbers;
    private String contents;
    private String reciver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_encode);
        initView();
    }

    private void initView() {
        type = (TextView) findViewById(R.id.textoftype);
        type.setText("SMS");
        text_info = (TextView) findViewById(R.id.textinfo);
        text_info.setText("Input url");
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        number = (EditText) findViewById(R.id.edit_number);
        content = (EditText) findViewById(R.id.edit_content);
        number.setMaxLines(18);
        content.setMaxLines(200);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                finish();
                break;
            case R.id.add:
                numbers = number.getText().toString();
                contents = content.getText().toString();
                texts = "SMSTO:" + numbers + ":" + contents;
                reciver = "Receiver number:" + numbers + "\n" +"SMS content:" + contents;
                if (texts.equals("")) {
                    Toast.makeText(SmsActivity.this,"The input is empty",Toast.LENGTH_SHORT).show();
                } else {
                    bitmap = EncodingUtils.createQRCode_text(texts.trim());
                    String uri = EncodingUtils.createImageFromBitmap(bitmap);

                    QrcodeData qrcodeData = new QrcodeData();
                    qrcodeData.setQrName("Sms");
                    qrcodeData.setQrContent(reciver);
                    qrcodeData.setImageFile(uri);
                    MyDatabaseHelper.addData(qrcodeData);

                    Intent intent = new Intent(SmsActivity.this,DisplayQrcodeActivity.class);
                    ImageInfoBean dto = new ImageInfoBean();
                    dto.setDescription(reciver);
                    dto.setUri(uri);
                    intent.putExtra("encode_text", dto);
                    intent.putExtra("type","SMS");
                    intent.putExtra("jump",0);
                    startActivity(intent);
                }
                break;
        }
    }
}
