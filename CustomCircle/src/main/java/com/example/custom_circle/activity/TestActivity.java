package com.example.custom_circle.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.custom_circle.R;

public class TestActivity extends Activity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toast.makeText(this, TAG, Toast.LENGTH_LONG).show();
    }
}