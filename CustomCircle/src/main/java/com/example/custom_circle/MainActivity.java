package com.example.custom_circle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.custom_circle.widget.CustomCircle3;

public class MainActivity extends AppCompatActivity {

    private CustomCircle3 mCircle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mCircle1 = (CustomCircle3) findViewById(R.id.circle5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = mCircle1.getProgress();
                int maxProgress = mCircle1.getMaxProgress();
                while (progress < maxProgress) {
                    try {
                        progress += 1;
                        Thread.sleep(100);
                        mCircle1.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        }).start();
    }
}