package com.example.drag_with_finger_view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 可随手指的拖拽一起移动的 Button
 *
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/4/2 22:43
 * @projectName DragWithHandView
 */
public class DragWithFingerButton extends Button {

    // TouchSlop的初始值
    private static final int TOUCH_SLOP_INIT_VALUE = -1;
    // 坐标值的初始值(例如: 该控件父容器的四条边坐标值的初始值)
    private static final int COORDINATE_INIT_VALUE = -1;

    // 左, 上, 右, 下四条边各自 margin的默认最小值.
    private static final int DEF_MIN_LEFT_MARGIN = 0;
    private static final int DEF_MIN_TOP_MARGIN = 0;
    private static final int DEF_MIN_RIGHT_MARGIN = 0;
    private static final int DEF_MIN_BOTTOM_MARGIN = 0;

    // 发生滑动时, 上一次记录的手指的 x,y 坐标
    private float mLastX;
    private float mLastY;

    // 手指分别在 x, y 方向上滑动的距离
    private float mScrolledDeltaX;
    private float mScrolledDeltaY;

    private ViewGroup.MarginLayoutParams mMarginLayoutParams;
    // 系统能够识别的最小滑动距离
    private int mScaledTouchSlop = TOUCH_SLOP_INIT_VALUE;

    // 左, 上, 右, 下四条边各自 margin的实际最小值.
    private int mMinLeftMargin = DEF_MIN_LEFT_MARGIN;
    private int mMinTopMargin = DEF_MIN_TOP_MARGIN;
    private int mMinRightMargin = DEF_MIN_RIGHT_MARGIN;
    private int mMinBottomMargin = DEF_MIN_BOTTOM_MARGIN;

    // 该控件的父容器的左, 上, 右, 下四条边的坐标.
    private int mParentViewLeft = COORDINATE_INIT_VALUE;
    private int mParentViewTop = COORDINATE_INIT_VALUE;
    private int mParentViewRight = COORDINATE_INIT_VALUE;
    private int mParentViewBottom = COORDINATE_INIT_VALUE;

    public DragWithFingerButton(Context context) {
        super(context);
        init();
    }

    public DragWithFingerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragWithFingerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initTouchSlop();
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
                mScrolledDeltaX = currX - mLastX;
                mScrolledDeltaY = currY - mLastY;
                // 如果手指滑动的距离太小, 小于系统能够识别的最小滑动距离 touchSlop, 那么就不认为发生了滑动事件.
                if (!isScroll()) {
                    break;
                }
                // 更新该控件的 MarginLayoutParams 数值, 然后重新摆放.
                invalidateMarginLayoutParams();
                mLastX = currX;
                mLastY = currY;
                break;
        }
        super.onTouchEvent(event);
        return true;
    }

    /**
     * 重新设定 MarginLayoutParams 的数值, 但必须保证该控件不能超出其父容器的四条边框包围的范围, 然后
     * 重新布局该控件 (即: 让该控件的父容器对他进行重新layout).
     */
    private void invalidateMarginLayoutParams() {
        // 获取父容器的四条边的坐标.
        getParentViewBorderCoordinates();

        // 向左移动, 超出了屏幕左侧
        if (mScrolledDeltaX < 0 && getLeft() + mScrolledDeltaX < mParentViewLeft) {
            mMarginLayoutParams.leftMargin = 0;
        }
        // 向右移动, 超出了屏幕右侧
        else if(mScrolledDeltaX > 0 && getRight() + mScrolledDeltaX > mParentViewRight) {
            mMarginLayoutParams.rightMargin = 0;
        }
        // 在水平方向移动后, 左右两个边都仍在屏幕内
        else if (getLeft() + mScrolledDeltaX > mParentViewLeft && getRight() + mScrolledDeltaX < mParentViewRight) {
            mMarginLayoutParams.leftMargin += (int) mScrolledDeltaX;
        }

        // 向上移动, 超出了屏幕顶部
        if (mScrolledDeltaY < 0 && getTop() + mScrolledDeltaY < mParentViewTop) {
            mMarginLayoutParams.topMargin = 0;
        }
        // 向下移动, 超出了屏幕底部
        else if(mScrolledDeltaY > 0 && getBottom() + mScrolledDeltaY > mParentViewBottom) {
            mMarginLayoutParams.bottomMargin = 0;
        }
        // 在竖直方向移动后, 上下两个边都仍在屏幕内
        else if (getTop() + mScrolledDeltaY > mParentViewTop && getBottom() + mScrolledDeltaY < mParentViewBottom) {
            mMarginLayoutParams.topMargin += (int) mScrolledDeltaY;
        }

        setLayoutParams(mMarginLayoutParams);
    }

    /**
     * 获取父容器的四条边的坐标.
     */
    private void getParentViewBorderCoordinates() {
        ViewGroup parentView = (ViewGroup) getParent();

        if (mParentViewLeft == COORDINATE_INIT_VALUE) {
            mParentViewLeft = parentView.getLeft();
        }
        if (mParentViewTop == COORDINATE_INIT_VALUE) {
            mParentViewTop = parentView.getTop();
        }
        if (mParentViewRight == COORDINATE_INIT_VALUE) {
            mParentViewRight = parentView.getRight();
        }
        if (mParentViewBottom == COORDINATE_INIT_VALUE) {
            mParentViewBottom = parentView.getBottom();
        }
    }

    /**
     * 判断手指是否发生了滑动事件.
     * @return 返回true表示手指发生了滑动事件. 返回false表示未发生滑动事件或者手指滑动距离太小,
     *         小于系统能够识别的最小滑动距离.
     */
    private boolean isScroll() {
        initTouchSlop();
        return Math.pow(mScrolledDeltaX, 2) + Math.pow(mScrolledDeltaY, 2) > Math.pow(mScaledTouchSlop, 2);
    }

    private void initTouchSlop() {
        if (mScaledTouchSlop == TOUCH_SLOP_INIT_VALUE) {
            mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }
    }
}