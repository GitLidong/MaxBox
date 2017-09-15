package com.lidong.maxbox.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidong.maxbox.R;
import com.lidong.maxbox.util.EncodingUtils;
import com.lidong.maxbox.util.ImageInfoBean;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ubuntu on 17-9-14.
 */

public class TextActivity extends Activity implements View.OnClickListener
        ,View.OnFocusChangeListener{

    private Button delete;
    private Button add;
    private EditText text;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textencode);
        initView();
    }

    private void initView() {
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        text = (EditText) findViewById(R.id.edit_text);
        text.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                Intent intent1 = new Intent (TextActivity.this,QrcodePickActivity.class);
                startActivity(intent1);
                break;
            case R.id.add:
                bitmap = EncodingUtils.createQRCode(text.getText().toString().trim());
                Intent intent = new Intent(TextActivity.this,DisplayQrcodeActivity.class);
                ImageInfoBean dto = new ImageInfoBean();
                String uri = createImageFromBitmap(bitmap);
                dto.setDescription(text.getText().toString());
                dto.setUri(uri);
                intent.putExtra("encode_text", dto);
                startActivity(intent);
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
