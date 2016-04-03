package com.example.drag_with_finger_view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * 可随手指的拖拽一起移动的 EditText
 *
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/4/3 10:11
 * @projectName DragWithHandView
 */
public class DragWithFingerEditText extends EditText {

    private float mLastY;
    private float mLastX;
    private ViewGroup.MarginLayoutParams mMarginLayoutParams;

//    private static final int MIN_LEFT_MARGIN_IN_DP = 50;
//    private static final int MIN_TOP_MARGIN_IN_DP = 50;
//    private static final int MIN_RIGHT_MARGIN_IN_DP = 50;
//    private static final int MIN_BOTTOM_MARGIN_IN_DP = 50;
//
//    private int mMinLeftMarginInPx;
//    private int mMinTopMarginInPx;
//    private int mMinRightMarginInPx;
//    private int mMinBottomMarginInPx;

    public DragWithFingerEditText(Context context) {
        this(context, null);
    }

    public DragWithFingerEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragWithFingerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
//        mMinLeftMarginInPx = DensityUtils.dip2px(context, MIN_LEFT_MARGIN_IN_DP);
//        mMinTopMarginInPx = DensityUtils.dip2px(context, MIN_TOP_MARGIN_IN_DP);
//        mMinRightMarginInPx = DensityUtils.dip2px(context, MIN_RIGHT_MARGIN_IN_DP);
//        mMinBottomMarginInPx = DensityUtils.dip2px(context, MIN_BOTTOM_MARGIN_IN_DP);

        if (attrs == null) {
           return;
        }

        // initialize custom attributes
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