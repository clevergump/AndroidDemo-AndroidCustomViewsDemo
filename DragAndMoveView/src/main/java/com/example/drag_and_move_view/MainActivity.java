package com.example.drag_and_move_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.clevergump.my_common_library.utils.DensityUtils;
import com.example.drag_and_move_view.widget.DragAndMoveButton;
import com.example.drag_and_move_view.widget.IncorrectDragAndMoveButton;

public class MainActivity extends AppCompatActivity {

    private DragAndMoveButton mDragBtn;
    private IncorrectDragAndMoveButton mIncorrectDragBtn;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initView();
        initSetting();

        // 下面的写法都是错误的, 因为此时该View可能还没有加载完毕, 所以获取的宽高以及四条边的坐标值都为0.

//        int width = mIncorrectDragBtn.getMeasuredWidth();
//        int height = mIncorrectDragBtn.getMeasuredHeight();
//        int left = mIncorrectDragBtn.getLeft();
//        int top = mIncorrectDragBtn.getTop();
//        int right = mIncorrectDragBtn.getRight();
//        int bottom = mIncorrectDragBtn.getBottom();
//
//        Toast.makeText(MainActivity.this, "width = " + width + ", height = " + height + ", left = " + left
//                + ", top = " + top + ", right = " + right +", bottom = " + bottom, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        mDragBtn = (DragAndMoveButton) findViewById(R.id.drag_btn);
        mIncorrectDragBtn = (IncorrectDragAndMoveButton) findViewById(R.id.incorrect_drag_btn);
        mBtn = (Button) findViewById(R.id.btn);
    }

    private void initSetting() {
        mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float rawX = event.getRawX();
                Toast.makeText(MainActivity.this, "rawX = " + rawX+", rawX 表示触摸点到屏幕左上角的水平距离, 不是到父容器左上角的水平距离", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // 手机屏幕内部嵌有三个按键, 导致屏幕的可视高度小于屏幕的总高度. 通过如下的log打印可知.
        mIncorrectDragBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIncorrectDragBtn.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int screenHeightPixels = DensityUtils.getScreenHeightPixels(MainActivity.this);
                int btnBottom = mIncorrectDragBtn.getBottom();
                Toast.makeText(MainActivity.this, "屏幕高度 = " + screenHeightPixels
                        + ", 紫色按钮底边 = " + btnBottom, Toast.LENGTH_SHORT).show();
            }
        });
    }
}