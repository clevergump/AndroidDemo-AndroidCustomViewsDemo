package com.example.custom_circle;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/3/24 16:11
 * @projectName AndroidDemo-CustomViewsDemo
 */
public class MyApplication extends Application {

    public static final String TAG = "CustomCircle";

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化图片加载框架UIL(Universal-Image-Loader).
        initUIL();
    }

    private void initUIL() {
        ImageLoaderConfiguration.Builder builder = null;
//        try {
//            builder = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                    .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
//                    .denyCacheImageMultipleSizesInMemory()
//                    .tasksProcessingOrder(QueueProcessingType.LIFO)
//                    .threadPoolSize(3)
//                    .writeDebugLogs();
//            ImageLoader.getInstance().init(builder.build());
//        } catch (IOException e) {
//            Log.w(TAG, "UIL - can't initialize disk cache");
//        }

        builder = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPoolSize(3)
                .writeDebugLogs();
        ImageLoader.getInstance().init(builder.build());
    }
}