package com.example.drag_and_move_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.drag_and_move_view.widget.DragAndMoveButton;
import com.example.drag_and_move_view.widget.IncorrectDragAndMoveButton;

public class MainActivity extends AppCompatActivity {

    private DragAndMoveButton mDragBtn;
    private IncorrectDragAndMoveButton mIncorrectDragBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initView();
        initSetting();
    }

    private void initView() {
        mDragBtn = (DragAndMoveButton) findViewById(R.id.drag_btn);
        mIncorrectDragBtn = (IncorrectDragAndMoveButton) findViewById(R.id.incorrect_drag_btn);
    }

    private void initSetting() {
//        mDragBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "onClick_btn", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mIncorrectDragBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "onClick_scrollBy_btn", Toast.LENGTH_SHORT).show();
//            }
//        });

        // 手机屏幕内部嵌有三个按键, 导致屏幕的可视高度小于屏幕的总高度. 通过如下的log打印可知.
//        mIncorrectDragBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mIncorrectDragBtn.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//
//                int screenHeightPixels = DensityUtils.getScreenHeightPixels(MainActivity.this);
//                int btnBottom = mIncorrectDragBtn.getBottom();
//                Toast.makeText(MainActivity.this, "screenHeight = " + screenHeightPixels
//                        + ", btnBottom = " + btnBottom, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}