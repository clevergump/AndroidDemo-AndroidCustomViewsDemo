package com.example.drag_and_move_view.widget.draft;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 可随手指的拖拽一起移动的 Button (草稿2, 仅供演示该控件功能的一步步完善, bug的一步步修复, 扩展性的一步步增强的过程)
 *
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/4/2 22:43
 */
public class DragAndMoveButton2 extends Button {

    private static final int TOUCH_SLOP_INIT_VALUE = -1;
    private float mLastY;
    private float mLastX;
    private ViewGroup.MarginLayoutParams mMarginLayoutParams;
    // 系统能够识别的最小滑动距离
    private int mScaledTouchSlop = TOUCH_SLOP_INIT_VALUE;

    public DragAndMoveButton2(Context context) {
        super(context);
        init();
    }

    public DragAndMoveButton2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragAndMoveButton2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initTouchSlopIfNecessary();
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
                // 如果手指滑动的距离太小, 小于系统能够识别的最小滑动距离 touchSlop, 那么就不认为发生了滑动事件.
                if (!isScroll(dx, dy)) {
                    break;
                }

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

    /**
     * 判断手指是否发生了滑动事件.
     * @param dx 手指在水平方向上滑动的终点x坐标减去起点x坐标的数值
     * @param dy 手指在水平方向上滑动的终点y坐标减去起点y坐标的数值
     * @return 返回true表示手指发生了滑动事件. 返回false表示未发生滑动事件或者手指滑动距离太小,
     *         小于系统能够识别的最小滑动距离.
     */
    private boolean isScroll(float dx, float dy) {
        initTouchSlopIfNecessary();
        return Math.pow(dx, 2) + Math.pow(dy, 2) > Math.pow(mScaledTouchSlop, 2);
    }

    private void initTouchSlopIfNecessary() {
        if (mScaledTouchSlop == TOUCH_SLOP_INIT_VALUE) {
            mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }
    }
}