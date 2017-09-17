package com.lidong.maxbox.activity;

import android.app.Activity;
import android.content.Context;
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
import com.lidong.maxbox.util.EncodingUtils;
import com.lidong.maxbox.util.ImageInfoBean;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * Created by ubuntu on 17-9-17.
 */

public class PhoneNumberActivity extends Activity implements View.OnClickListener{

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
        type.setText("Phone Number");
        text_info = (TextView) findViewById(R.id.textinfo);
        text_info.setText("Input number");
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        text = (EditText) findViewById(R.id.edit_text);
        text.setHint(R.string.inputPhoneNumHint);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                Intent intent1 = new Intent (PhoneNumberActivity.this,QrcodePickActivity.class);
                startActivity(intent1);
                break;
            case R.id.add:
                texts = text.getText().toString();
                if (texts.equals("")) {
                    Toast.makeText(PhoneNumberActivity.this,"The input is empty",Toast.LENGTH_SHORT).show();
                } else if(texts.length() > 18) {
                    Toast.makeText(PhoneNumberActivity.this,"The input is too long",Toast.LENGTH_SHORT).show();

                } else {
                    bitmap = EncodingUtils.createQRCode_text("tel:"+texts.trim());
                    Intent intent = new Intent(PhoneNumberActivity.this,DisplayQrcodeActivity.class);
                    ImageInfoBean dto = new ImageInfoBean();
                    String uri = createImageFromBitmap(bitmap);
                    dto.setDescription("Phone number:"+texts);
                    dto.setUri(uri);
                    intent.putExtra("encode_text", dto);
                    intent.putExtra("type","Phone Number");
                    startActivity(intent);
                }
                break;
        }
    }

    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }
}
