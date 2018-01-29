package com.binzo.secondaryservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by liubingzhao on 2018/1/26.
 */

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}