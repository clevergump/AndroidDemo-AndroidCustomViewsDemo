package com.example.custom_circle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.clevergump.my_common_library.utils.DensityUtils;

/**
 * 自定义圆
 *
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/3/21 19:14
 * @projectName AndroidDemo-CustomViewsDemo
 */
public class CustomCircle1 extends View {

    private Paint mBorderPaint;
    private Paint mContentPaint;
    // 默认宽高
    private float mDefSize;
    private float mDefBorderWidth;
    private float mDefBorderCircleRadius;

    public CustomCircle1(Context context) {
        super(context);
        init(context, null);
    }

    public CustomCircle1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomCircle1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mDefSize = DensityUtils.dip2px(context, 100);
        mDefBorderWidth = DensityUtils.dip2px(getContext(), 10);
        // 圆的半径等于圆心到圆边框中心线(圆的外边框和内边框二者之间的那条中心线)的距离
        mDefBorderCircleRadius = mDefSize / 2 - mDefBorderWidth / 2;
        initPaint();
    }

    private void initPaint() {
        initBorderPaint();
        initContentPaint();
    }

    private void initBorderPaint() {
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(Color.BLACK);
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
        canvas.drawCircle(cx, cy, mDefBorderCircleRadius, mBorderPaint);
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
            measuredWidth = (int)(mDefSize + 0.5f);
        }
        // 如果layout文件中设置layout_height 为 wrap_content, 那么就使用默认的高度
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            measuredHeight = (int)(mDefSize + 0.5f);
        }

        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
