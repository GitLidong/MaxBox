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

public class TextActivity extends MyActivity implements View.OnClickListener
        ,View.OnFocusChangeListener{

    private Button delete;
    private Button add;
    private EditText text;
    private Bitmap bitmap;
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
        type.setText("Text");
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        text = (EditText) findViewById(R.id.edit_text);
        //text.setOnFocusChangeListener(this);
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
                    Toast.makeText(TextActivity.this,"The input is empty",Toast.LENGTH_SHORT).show();
                } else {
                    bitmap = EncodingUtils.createQRCode_text(text.getText().toString().trim());
                    String uri = EncodingUtils.createImageFromBitmap(bitmap);

                    QrcodeData qrcodeData = new QrcodeData();
                    qrcodeData.setQrName("Text");
                    qrcodeData.setQrContent(text.getText().toString());
                    qrcodeData.setImageFile(uri);
                    MyDatabaseHelper.addData(qrcodeData);

                    Intent intent = new Intent(TextActivity.this,DisplayQrcodeActivity.class);
                    ImageInfoBean dto = new ImageInfoBean();
                    dto.setDescription("Content:"+text.getText().toString());
                    dto.setUri(uri);
                    intent.putExtra("encode_text", dto);
                    intent.putExtra("type","Text");
                    intent.putExtra("jump",0);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.edit_text:
                setHintEt(text,b);
                break;
        }
    }

    private void setHintEt(EditText et,boolean hasFocus){
        String hint;
        if(hasFocus){
            hint = et.getHint().toString();
            et.setTag(hint);
            et.setHint("");
        }else{
            hint = et.getTag().toString();
            et.setHint(hint);
        }
    }
}
