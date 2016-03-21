package com.example.custom_rectangle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.clevergump.my_common_library.utils.DensityUtils;

/**
 * 自定义长方形. 最基本的自定义控件. 主要练习自定义一个继承自View类的控件时, 要在onMeasure()方法中
 * 处理wrap_content的情况.
 *
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/3/21 15:08
 * @projectName AndroidCustomViewsDemo
 */
public class CustomRectangle1 extends View {
    private Paint mPaint;
    // 默认宽度
    private int mDefWidth;
    // 默认高度
    private int mDefHeight;


    public CustomRectangle1(Context context) {
        super(context);
        init(context, null);
    }

    public CustomRectangle1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomRectangle1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 默认宽度是屏幕宽度的1/4
        mDefWidth = DensityUtils.getScreenWidthPixels(context) >> 2;
        // 默认高度是屏幕高度的1/4
        mDefHeight = DensityUtils.getScreenHeightPixels(context) >> 2;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
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
            measuredWidth = mDefWidth;
        }
        // 如果layout文件中设置layout_height 为 wrap_content, 那么就使用默认的高度
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            measuredHeight = mDefHeight;
        }

        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}