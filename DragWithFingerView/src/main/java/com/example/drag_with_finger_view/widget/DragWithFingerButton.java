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
    private static final int DEF_MIN_LEFT_MARGIN_IN_PX = 100;
    private static final int DEF_MIN_TOP_MARGIN_IN_PX = 100;
    private static final int DEF_MIN_RIGHT_MARGIN_IN_PX = 100;
    private static final int DEF_MIN_BOTTOM_MARGIN_IN_PX = 100;

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
    private int mMinLeftMarginInPx = DEF_MIN_LEFT_MARGIN_IN_PX;
    private int mMinTopMarginInPx = DEF_MIN_TOP_MARGIN_IN_PX;
    private int mMinRightMarginInPx = DEF_MIN_RIGHT_MARGIN_IN_PX;
    private int mMinBottomMarginInPx = DEF_MIN_BOTTOM_MARGIN_IN_PX;

    // 该控件的父容器的左, 上, 右, 下四条边的坐标.
    private int mParentViewLeft = COORDINATE_INIT_VALUE;
    private int mParentViewTop = COORDINATE_INIT_VALUE;
    private int mParentViewRight = COORDINATE_INIT_VALUE;
    private int mParentViewBottom = COORDINATE_INIT_VALUE;
    private int mMinLeft = COORDINATE_INIT_VALUE;
    private int mMaxRight = COORDINATE_INIT_VALUE;
    private int mMinTop = COORDINATE_INIT_VALUE;
    private int mMaxBottom = COORDINATE_INIT_VALUE;

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
     * 判断手指是否发生了滑动事件.
     * @return 返回true表示手指发生了滑动事件. 返回false表示未发生滑动事件或者手指滑动距离太小,
     *         小于系统能够识别的最小滑动距离.
     */
    private boolean isScroll() {
        initTouchSlop();
        return Math.pow(mScrolledDeltaX, 2) + Math.pow(mScrolledDeltaY, 2) > Math.pow(mScaledTouchSlop, 2);
    }

    /**
     * 重新设定 MarginLayoutParams 的数值, 但必须保证该控件不能超出其父容器的四条边框包围的范围, 然后
     * 重新布局该控件 (即: 让该控件的父容器对他进行重新layout).
     */
    private void invalidateMarginLayoutParams() {
        // 计算该控件能够在其中滑动的最大区域的四条边的坐标值.
        calcBorderCoordinatesOfMaxAreaAvailable();

        // 如果向左移动, 并且超出了该控件能够在其中移动的最大区域的左边框
        if (moveLeftward() && moveOutOfLeftBorderLimit()) {
            mMarginLayoutParams.leftMargin = mMinLeftMarginInPx;
        }
        // 如果向右移动, 并且超出了该控件能够在其中移动的最大区域的右边框
        else if(moveRightward() && moveOutOfRightBorderLimit()) {
            mMarginLayoutParams.rightMargin = mMinRightMarginInPx;
        }
        // 如果在水平方向移动后, 左右两个边都仍在该控件能够在其中移动的最大区域的左右边框范围内
        else if (moveWithinLeftAndRightBorderLimits()) {
            mMarginLayoutParams.leftMargin += (int) mScrolledDeltaX;
        }

        // 如果向上移动, 并且超出了该控件能够在其中移动的最大区域的上边框
        if (moveUpward() && moveOutOfTopBorderLimit()) {
            mMarginLayoutParams.topMargin = mMinTopMarginInPx;
        }
        // 如果向下移动, 并且超出了该控件能够在其中移动的最大区域的下边框
        else if(moveDownward() && moveOutOfBottomBorderLimit()) {
            mMarginLayoutParams.bottomMargin = mMinBottomMarginInPx;
        }
        // 如果在竖直方向移动后, 上下两个边都仍在该控件能够在其中移动的最大区域的上下边框范围内
        else if (moveWithinTopAndBottomBorderLimits()) {
            mMarginLayoutParams.topMargin += (int) mScrolledDeltaY;
        }

        setLayoutParams(mMarginLayoutParams);
    }

    /**
     * 计算该控件能够在其中滑动的最大区域的四条边的坐标值.
     */
    private void calcBorderCoordinatesOfMaxAreaAvailable() {
        ViewGroup parentView = (ViewGroup) getParent();

        if (mMinLeft == COORDINATE_INIT_VALUE) {
            if (mParentViewLeft == COORDINATE_INIT_VALUE) {
                mParentViewLeft = parentView.getLeft();
            }
            mMinLeft = mParentViewLeft + mMinLeftMarginInPx;
        }

        if (mMinTop == COORDINATE_INIT_VALUE) {
            if (mParentViewTop == COORDINATE_INIT_VALUE) {
                mParentViewTop = parentView.getTop();
            }
            mMinTop = mParentViewTop + mMinTopMarginInPx;
        }

        if (mMaxRight == COORDINATE_INIT_VALUE) {
            if (mParentViewRight == COORDINATE_INIT_VALUE) {
                mParentViewRight = parentView.getRight();
            }
            mMaxRight = mParentViewRight - mMinRightMarginInPx;
        }

        if (mMaxBottom == COORDINATE_INIT_VALUE) {
            if (mParentViewBottom == COORDINATE_INIT_VALUE) {
                mParentViewBottom = parentView.getBottom();
            }
            mMaxBottom = mParentViewBottom - mMinBottomMarginInPx;
        }
    }

    /**
     * 判断该控件向上移动后, 是否已超出了他能够在其中移动的最大区域的上边框.
     * @return
     */
    private boolean moveOutOfTopBorderLimit() {
        return getTop() + mScrolledDeltaY < mMinTop;
    }

    /**
     * 判断该控件向下移动后, 是否已超出了他能够在其中移动的最大区域的下边框.
     * @return
     */
    private boolean moveOutOfBottomBorderLimit() {
        return getBottom() + mScrolledDeltaY > mMaxBottom;
    }

    /**
     * 判断该控件在垂直方向移动后, 是否仍然在他能够在其中移动的最大区域的上下两边框范围内.
     * @return
     */
    private boolean moveWithinTopAndBottomBorderLimits() {
        return getTop() + mScrolledDeltaY > mMinTop && getBottom() + mScrolledDeltaY < mMaxBottom;
    }

    /**
     * 判断该控件向左移动后, 是否已超出了他能够在其中移动的最大区域的左边框.
     * @return
     */
    private boolean moveOutOfLeftBorderLimit() {
        return getLeft() + mScrolledDeltaX < mMinLeft;
    }

    /**
     * 判断该控件向右移动后, 是否已超出了他能够在其中移动的最大区域的右边框.
     * @return
     */
    private boolean moveOutOfRightBorderLimit() {
        return getRight() + mScrolledDeltaX > mMaxRight;
    }

    /**
     * 判断该控件在水平方向移动后, 是否仍然在该控件能够在其中移动的最大区域的左右两边框范围内.
     * @return
     */
    private boolean moveWithinLeftAndRightBorderLimits() {
        return getLeft() + mScrolledDeltaX > mMinLeft && getRight() + mScrolledDeltaX < mMaxRight;
    }

    /**
     * 是否向左移动
     * @return
     */
    private boolean moveLeftward() {
        return mScrolledDeltaX < 0;
    }

    /**
     * 是否向上移动
     * @return
     */
    private boolean moveUpward() {
        return mScrolledDeltaY < 0;
    }

    /**
     * 是否向右移动
     * @return
     */
    private boolean moveRightward() {
        return mScrolledDeltaX > 0;
    }

    /**
     * 是否向下移动
     * @return
     */
    private boolean moveDownward() {
        return mScrolledDeltaY > 0;
    }

    private void initTouchSlop() {
        if (mScaledTouchSlop == TOUCH_SLOP_INIT_VALUE) {
            mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }
    }
}