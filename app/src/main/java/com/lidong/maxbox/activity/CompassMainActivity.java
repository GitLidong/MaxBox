package com.lidong.maxbox.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidong.maxbox.MainActivity;
import com.lidong.maxbox.R;
import com.lidong.maxbox.views.CompassView;

/**
 * Created by ubuntu on 17-8-23.
 */

public class CompassMainActivity extends Activity implements View.OnClickListener{

    private TextView compass_textview;
    private Button back;
    private CompassView compassView;
    private RelativeLayout compass_alert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        init();
    }

    private void init() {
        compass_textview = (TextView) findViewById(R.id.compass_direction);
        back = (Button) findViewById(R.id.back);
        compassView = (CompassView) findViewById(R.id.compassPointer);
        compass_alert = (RelativeLayout) getLayoutInflater().inflate(R.layout.compass_alert,null);
        back.setOnClickListener(this);
        compassView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                 break;
            case R.id.compassPointer:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.compass_text_calibration_title);
                builder.setView(compass_alert).create().show();


                break;
        }
    }
}
