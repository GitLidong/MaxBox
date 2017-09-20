package com.lidong.maxbox.manager;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by ubuntu on 17-9-20.
 */

public class MyActivity extends Activity {

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.removeActivity(this);
    }
}
