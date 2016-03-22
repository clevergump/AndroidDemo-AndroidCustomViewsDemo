package com.example.custom_circle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.clevergump.my_common_library.utils.DensityUtils;

/**
 * 圆+圆内的扇形区块
 *
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/3/22 15:22
 * @projectName AndroidDemo-CustomViewsDemo
 */
public class CustomCircle2 extends View {

    // 绘制圆的画笔
    private Paint mBorderPaint;
    // 绘制圆内扇形的画笔
    private Paint mContentPaint;
    // 默认宽高
    private float mDefSize;
    // 边框的默认宽度
    private float mDefBorderWidth;
    // 圆半径的默认值
    private float mDefCircleRadius;
    // 圆内画弧线时的矩形外框.
    private RectF mInnerArcRectF;

    public CustomCircle2(Context context) {
        super(context);
        init(context, null);
    }

    public CustomCircle2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomCircle2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initDefValues(context);
        initPaint();
    }

    /**
     * 初始化各个默认值
     * @param context
     */
    private void initDefValues(Context context) {
        mDefSize = DensityUtils.dip2px(context, 100);
        mDefBorderWidth = DensityUtils.dip2px(getContext(), 20);
        // 注意: 圆的半径等于圆心到圆的内外两个边框之间的中心线的距离
        mDefCircleRadius = mDefSize / 2 - mDefBorderWidth / 2;
    }

    private void initPaint() {
        initBorderPaint();
        initContentPaint();
    }

    private void initBorderPaint() {
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(Color.parseColor("#55ff0000"));
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mDefBorderWidth);
    }

    private void initContentPaint() {
        mContentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContentPaint.setColor(Color.parseColor("#550000ff"));
        mContentPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cx = mDefSize / 2;
        float cy = cx;
        canvas.drawCircle(cx, cy, mDefCircleRadius, mBorderPaint);
        if (mInnerArcRectF == null) {
            mInnerArcRectF = new RectF(mDefBorderWidth, mDefBorderWidth, getWidth() - mDefBorderWidth, getHeight() - mDefBorderWidth);
        }
        // 画圆内的扇形
        canvas.drawArc(mInnerArcRectF, 0, 135, true, mContentPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int measuredWidth = widthSpecSize;
        int measuredHeight = heightSpecSize;

        // 如果layout文件中设置layout_width 为 wrap_content, 那么就使用默认的宽度
        if (widthSpecMode == MeasureSpec.AT_MOST) {
            measuredWidth = (int) (mDefSize + 0.5f);
        }
        // 如果layout文件中设置layout_height 为 wrap_content, 那么就使用默认的高度
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            measuredHeight = (int) (mDefSize + 0.5f);
        }

        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}