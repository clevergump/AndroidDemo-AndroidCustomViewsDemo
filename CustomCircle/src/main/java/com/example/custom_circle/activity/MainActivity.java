package com.example.custom_circle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.custom_circle.R;
import com.example.custom_circle.widget.ProgressCircle;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String DEF_URL_IMAGE = "http://www.sinaimg.cn/dy/slidenews/2_img/2016_11/730_1735528_995003.jpg";
    private static final int CIRCLE_1 = 1;
    private static final int CIRCLE_2 = 2;
    private static final int CIRCLE_3 = 3;

    private ProgressCircle mCircle1;
    private ProgressCircle mCircle2;
    private ProgressCircle mCircle3;
    private ProgressCircle mCircle4;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        mCircle1.resetProgress();
    }

    private void initView() {
        mCircle1 = (ProgressCircle) findViewById(R.id.circle1);
        mCircle2 = (ProgressCircle) findViewById(R.id.circle2);
        mCircle3 = (ProgressCircle) findViewById(R.id.circle3);
        mCircle4 = (ProgressCircle) findViewById(R.id.circle4);
        mIv = (ImageView) findViewById(R.id.iv);
    }

    private void loadImage(ImageView mIv, String uri) {
        DisplayImageOptions.Builder optionsBuilder = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.mipmap.bg_image_loading)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error);
        DisplayImageOptions options = optionsBuilder.build();

        // TODO: 匿名内部类, 可能会造成该Activity泄露, 待重构
        ImageLoader.getInstance().displayImage(uri, mIv, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mCircle4.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mCircle4.setVisibility(View.GONE);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                mCircle4.setProgress(current, total);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Executor threadPool = Executors.newCachedThreadPool();
        threadPool.execute(new RunnableImpl(MainActivity.this, 1, CIRCLE_1));
        threadPool.execute(new RunnableImpl(MainActivity.this, 1, CIRCLE_2));
        threadPool.execute(new RunnableImpl(MainActivity.this, 2, CIRCLE_3));
        loadImage(mIv, DEF_URL_IMAGE);
    }

    private static class RunnableImpl implements Runnable {

        // 每次增加的进度值.
        private int deltaProgress;
        private WeakReference<MainActivity> weakRef;
        private int whichCircle;

        public RunnableImpl(MainActivity activity, int deltaProgress, int whichCircle) {
            weakRef = new WeakReference<MainActivity>(activity);
            this.deltaProgress = deltaProgress;
            this.whichCircle = whichCircle;
        }

        @Override
        public void run() {
            MainActivity activity = weakRef.get();
            if (activity == null) {
                return;
            }
            ProgressCircle circle = null;
            switch (whichCircle) {
                case CIRCLE_1:
                    circle = activity.mCircle1;
                    break;
                case CIRCLE_2:
                    circle = activity.mCircle2;
                    break;
                case CIRCLE_3:
                    circle = activity.mCircle3;
                    break;
            }

            int progress = circle.getProgress();
            int maxProgress = circle.getMaxProgress();
            while (progress < maxProgress) {
                try {
                    progress += deltaProgress;
                    Thread.sleep(50);
                    circle.setProgress(progress);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
    }
}