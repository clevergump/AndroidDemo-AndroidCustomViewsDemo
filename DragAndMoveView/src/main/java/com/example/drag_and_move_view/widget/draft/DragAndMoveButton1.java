package com.example.drag_and_move_view.widget.draft;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 可随手指的拖拽一起移动的 Button (草稿1, 仅供演示该控件功能的一步步完善, bug的一步步修复, 扩展性的一步步增强的过程)
 *
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/4/2 22:43
 */
public class DragAndMoveButton1 extends Button {

    private float mLastY;
    private float mLastX;
    private ViewGroup.MarginLayoutParams mMarginLayoutParams;

    public DragAndMoveButton1(Context context) {
        super(context);
        init();
    }

    public DragAndMoveButton1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragAndMoveButton1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getRawX();
                mLastY = event.getRawY();
                mMarginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float currX = event.getRawX();
                float currY = event.getRawY();
                float dx = currX - mLastX;
                float dy = currY - mLastY;
                mMarginLayoutParams.leftMargin += (int)dx;
                mMarginLayoutParams.topMargin += (int)dy;
                setLayoutParams(mMarginLayoutParams);
                mLastX = currX;
                mLastY = currY;
                break;
        }
        super.onTouchEvent(event);
        return true;
    }
}
