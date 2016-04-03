package com.example.drag_with_finger_view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 这是一个反面例子. 为了说明调用一个控件的 scrollTo(), scrollBy()方法, 只能让控件的内容移动, 而不能让移动控件.
 * 如果想让某个控件移动, 可以在该控件的外面嵌套一层 ViewGroup, 然后调用该 ViewGroup的 scrollTo(), scrollBy()方法.
 *
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/4/3 11:15
 * @projectName DragWithHandView
 */
public class IncorrectDragWithFingerButton extends Button {

    private float mLastY;
    private float mLastX;

    public IncorrectDragWithFingerButton(Context context) {
        super(context);
    }

    public IncorrectDragWithFingerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IncorrectDragWithFingerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getRawX();
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float currX = event.getRawX();
                float currY = event.getRawY();
                float dx = currX - mLastX;
                float dy = currY - mLastY;
                scrollBy((int)-dx, (int)-dy);
                mLastX = currX;
                mLastY = currY;
                break;
        }
        super.onTouchEvent(event);
        return true;
    }
}
