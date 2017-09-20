package com.lidong.maxbox.activity.QrcodeSonAcitvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lidong.maxbox.R;
import com.lidong.maxbox.manager.ActivityCollector;
import com.lidong.maxbox.manager.MyActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ubuntu on 17-9-13.
 */

public class QrcodePickActivity extends MyActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener{
    private Button back;
    private String[] type_texts;
    private int[] type_image;
    private ListView list;
    private static DemoInfo[] demos = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcodepick);
        initData();
        initView();
    }

    private void initData() {
        type_texts =new String[] {"Text","Url","SMS","Phone Number","Contact","Email"};
        type_image = new int[] { R.drawable.text, R.drawable.url,
                R.drawable.message, R.drawable.phone,
                R.drawable.contact, R.drawable.email };
        demos = new DemoInfo[]{
                new DemoInfo(TextActivity.class),
                new DemoInfo(UrlActivity.class),
                new DemoInfo(SmsActivity.class),
                new DemoInfo(PhoneNumberActivity.class),
                new DemoInfo(ContactActivity.class),
                new DemoInfo(EmailActivity.class)
        };
    }

    private void initView() {
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        list = (ListView) findViewById(R.id.list);
        list.addFooterView(new ViewStub(this));
        list.setOnItemClickListener(this);
        List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
        for (int i=0;i<type_texts.length;i++) {
            Map<String,Object> listItem = new HashMap<String, Object>();
            listItem.put("images",type_image[i]);
            listItem.put("texts",type_texts[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleadapter = new SimpleAdapter(this,listItems, R.layout.qrtype,
                new String[] {"images","texts"},
                new int[] {R.id.type_image, R.id.type_text});



        list.setAdapter(simpleadapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent = new Intent(QrcodePickActivity.this,QrCodeCreateActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QrcodePickActivity.this,QrCodeCreateActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(QrcodePickActivity.this, demos[i].demoClass);
            startActivity(intent);
    }

    //把每个activity转成class
    private static class DemoInfo {

        private final Class<? extends Activity> demoClass;

        public DemoInfo(Class<? extends Activity> demoClass) {
            this.demoClass = demoClass;
        }
    }
}

