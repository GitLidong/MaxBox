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

public class ContactActivity extends MyActivity implements View.OnClickListener{
    private Button delete;
    private Button add;
    private TextView type;
    private EditText edit_name;
    private EditText edit_company;
    private EditText edit_phone1;
    private EditText edit_phone2;
    private EditText edit_phone3;
    private EditText edit_email1;
    private EditText edit_email2;
    private EditText edit_email3;
    private EditText edit_url;
    private EditText edit_address;
    private EditText edit_note;
    private String name;
    private String company;
    private String phone1;
    private String phone2;
    private String phone3;
    private String phone = new String();
    private String email1;
    private String email2;
    private String email3;
    private String email;
    private String url;
    private String address;
    private String note;
    private String add_content;
    private String content;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_encode);
        initView();
    }

    private void initData() {
        name = edit_name.getText().toString().trim();
        company = edit_company.getText().toString().trim();
        phone1 = edit_phone1.getText().toString().trim();
        phone2 = edit_phone2.getText().toString().trim();
        phone3 = edit_phone3.getText().toString().trim();
        email1 = edit_email1.getText().toString().trim();
        email2 = edit_email2.getText().toString().trim();
        email3 = edit_email3.getText().toString().trim();
        url = edit_url.getText().toString().trim();
        address = edit_address.getText().toString().trim();
        note = edit_note.getText().toString().trim();
        if (!phone1.equals("")) {
            phone += phone1;
        }
        if (!phone2.equals("")) {
            if ("".equals(phone)){
                phone+=phone2;
            }else {
                phone+= (","+phone2);
            }
        }
        if (!"".equals(phone3)){
            if ("".equals(phone)){
                phone+=phone2;
            }else {
                phone+= (","+phone3);
            }
        }

        if (!email1.equals("")) {
            email += email1;
        }
        if (!email2.equals("")) {
            if ("".equals(email)){
                email+=email2;
            }else {
                email+= (","+email2);
            }
        }
        if (!"".equals(email3)){
            if ("".equals(email)){
                email+=email2;
            }else {
                email+= (","+email3);
            }
        }

        content = "BEGIN:VCARD\nVERSION:3.0\n" + "N:" + name
                + "\nORG:" + company + "\nTEL:" + phone
                + "\nEMAIL:" + email + "\nURL:" + url
                + "\nADR:" + address + "\nNOTE:" + note + "\nEND:VCARD";
        add_content = "Name:"+name;
        if (!company.equals("")) {
            add_content += ("\nCompany:"+company);
        }
        add_content += ("\nPhone:" + phone);
        if (!email.equals("")) {
            add_content += ("\nEmail:"+email);
        }
        if (!url.equals("")) {
            add_content += ("\nUrl:"+url);
        }
        if (!address.equals("")) {
            add_content += ("\nAddress:"+address);
        }
        if (!note.equals("")) {
            add_content += ("\nNote:"+note);
        }

    }

    private void initView() {
        type = (TextView) findViewById(R.id.textoftype);
        type.setText("Contact");
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_company = (EditText) findViewById(R.id.edit_company);
        edit_phone1 = (EditText) findViewById(R.id.edit_phone1);
        edit_phone2 = (EditText) findViewById(R.id.edit_phone2);
        edit_phone3 = (EditText) findViewById(R.id.edit_phone3);
        edit_email1 = (EditText) findViewById(R.id.edit_email1);
        edit_email2 = (EditText) findViewById(R.id.edit_email2);
        edit_email3 = (EditText) findViewById(R.id.edit_email3);
        edit_url = (EditText) findViewById(R.id.edit_url);
        edit_address = (EditText) findViewById(R.id.edit_address);
        edit_note = (EditText) findViewById(R.id.edit_note);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                finish();
                break;
            case R.id.add:
                initData();
                if ("".equals(name) || name == null) {
                    Toast.makeText(ContactActivity.this,"The input  of name is empty",Toast.LENGTH_SHORT).show();
                    break;
                } else if (phone1.equals("") && phone2.equals("") && phone3.equals("")) {
                    Toast.makeText(ContactActivity.this,"The input  of number is empty",Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (!email.equals("")) {
                        if (!email1.equals("")) {
                            if (EncodingUtils.checkEmail(email1) == false) {
                                Toast.makeText(ContactActivity.this,"The format of email is invalid",Toast.LENGTH_SHORT).show();
                                break;
                            }
                        } else if (!email2.equals("")) {
                            if (EncodingUtils.checkEmail(email2) == false) {
                                Toast.makeText(ContactActivity.this,"The format of email is invalid",Toast.LENGTH_SHORT).show();
                                break;
                            }
                        } else if (!email3.equals("")) {
                            if (EncodingUtils.checkEmail(email3) == false) {
                                Toast.makeText(ContactActivity.this,"The format of email is invalid",Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                    bitmap = EncodingUtils.createQRCode_text(content);
                    String uri = EncodingUtils.createImageFromBitmap(bitmap);

                    QrcodeData qrcodeData = new QrcodeData();
                    qrcodeData.setQrName("Contact");
                    qrcodeData.setQrContent(add_content);
                    qrcodeData.setImageFile(uri);
                    MyDatabaseHelper.addData(qrcodeData);

                    Intent intent = new Intent(ContactActivity.this,DisplayQrcodeActivity.class);
                    ImageInfoBean dto = new ImageInfoBean();
                    dto.setDescription(add_content);
                    dto.setUri(uri);
                    intent.putExtra("encode_text", dto);
                    intent.putExtra("type","Contact");
                    intent.putExtra("jump",0);
                    startActivity(intent);
                }
                break;
        }
    }
}
