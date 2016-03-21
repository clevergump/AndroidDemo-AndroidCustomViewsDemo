package com.example.custom_rectangle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.clevergump.my_common_library.utils.DensityUtils;
import com.example.custom_rectangle.R;

/**
 * 自定义长方形. 练习自定义属性
 *
 * @author zhangzhiyi
 * @version 1.0
 * @createTime 2016/3/21 15:08
 * @projectName AndroidCustomViewsDemo
 */
public class CustomRectangle2 extends View {
    // 边框的默认颜色
    private static final int DEF_BORDER_COLOR = Color.RED;
    // 内容的默认颜色
    private static final int DEF_CONTENT_COLOR = Color.RED;

    private OnCreatedCallback mCallback = new OnCreatedCallback() {
        @Override
        public void onCreated(Context context) {
            mDefBorderWidth = DensityUtils.dip2px(context, 10);
//            Toast.makeText(context, "mDefBorderWidth = "+mDefBorderWidth, Toast.LENGTH_LONG).show();
        }
    };

    // 绘制边框的画笔
    private Paint mBorderPaint;
    // 绘制内容的画笔
    private Paint mContentPaint;
    // 默认宽度
    private float mDefWidth;
    // 默认高度
    private float mDefHeight;
    // 默认的边框宽度
    private float mDefBorderWidth;

    /************************自定义的属性***********************************/
    // 边框颜色
    // Color.TRANSPARENT = 0; 所以如果不在此赋值, 那么该颜色将默认是透明色, 所以在屏幕上是看不到的.
    private int mBorderColor = DEF_BORDER_COLOR;
    // 内容颜色
    // Color.TRANSPARENT = 0; 所以如果不在此赋值, 那么该颜色将默认是透明色, 所以在屏幕上是看不到的.
    private int mContentColor = DEF_CONTENT_COLOR;
    // 边框的宽度
    private float mBorderWidth;
    // 是否显示图形的内容. 默认是true, 即:默认是显示. 如果为false, 则只显示边框不显示内容.
    private boolean mShowContent = true;
    // 是否需要展示用错误绘制逻辑绘制出来的控件. 如果为true, 就表示使用错误的绘制逻辑来绘制一个View,
    // 相当于用反面例子来告诉我们什么样的绘制逻辑是错误的.
    private boolean mShowIncorrectDrawing;


    public CustomRectangle2(Context context) {
        super(context);
        init(context, null);
    }

    public CustomRectangle2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomRectangle2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mCallback.onCreated(context);
        initAttrs(context, attrs);
        // 默认宽度是屏幕宽度的1/4
        mDefWidth = DensityUtils.getScreenWidthPixels(context) >> 2;
        // 默认高度是屏幕高度的1/4
        mDefHeight = DensityUtils.getScreenHeightPixels(context) >> 2;
        initPaint();
    }

    /**
     * 获取自定义属性并赋值
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomRectangle2, 0, 0);
        int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = a.getIndex(i);
            switch (index) {
                case R.styleable.CustomRectangle2_borderColor:
                    mBorderColor = a.getColor(index, DEF_BORDER_COLOR);
                    break;
                case R.styleable.CustomRectangle2_contentColor:
                    mContentColor = a.getColor(index, DEF_CONTENT_COLOR);
                    break;
                case R.styleable.CustomRectangle2_borderWidth:
                    mBorderWidth = a.getDimension(index, mDefBorderWidth);
                    break;
                case R.styleable.CustomRectangle2_showContent:
                    mShowContent = a.getBoolean(index, true);
                    break;
                case R.styleable.CustomRectangle2_showIncorrectDrawing:
                    mShowIncorrectDrawing = a.getBoolean(index, false);
                default:
                    break;
            }
        }
        // 回收以便重复利用
        a.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        initBorderPaint();
        initContentPaint();
    }

    /**
     * 初始化绘制边框的画笔
     */
    private void initBorderPaint() {
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    /**
     * 初始化绘制内容的画笔
     */
    private void initContentPaint() {
        mContentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContentPaint.setColor(mContentColor);
        mContentPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Toast.makeText(getContext(), "right = " + getRight() + ", width = " + getWidth(), Toast.LENGTH_LONG).show();
        // 边框宽度的一半.
        float halfBorderWidth = mBorderWidth / 2;

        // 注意:
        // canvas.drawRect()绘制有stroke的矩形时, 需要绘制的四条边的坐标其实是相对于四条边内部的中心线所
        // 组成的矩形的左上角那个点的距离, 而不是相对于父控件或屏幕的坐标.
        canvas.drawRect(halfBorderWidth, halfBorderWidth, getWidth() - halfBorderWidth,
            getHeight() - halfBorderWidth, mBorderPaint);
        if (mShowContent) {

            // 注意:
            // canvas.drawRect()绘制 Fill 的矩形时, 四个坐标是相对于该矩形左上角那个点的坐标, 而不是相对于父控件或屏幕的坐标.
            // 对比:
            // View.getLeft(), getRight(), getTop(), getBottom()得到的坐标是四条边相对于父控件的坐标,
            // 而不是相对于该控件左上角那个点的坐标. 与canvas.drawRect()方法的四个坐标的含义不同.

            if (mShowIncorrectDrawing) {
                // 下面是canvas.drawRect()错误的用法
                canvas.drawRect(mBorderWidth, mBorderWidth, getRight() - mBorderWidth,
                        getBottom() - mBorderWidth, mContentPaint);
            } else {
                // 下面是canvas.drawRect()正确的用法
                canvas.drawRect(mBorderWidth, mBorderWidth, getWidth() - mBorderWidth,
                        getHeight() - mBorderWidth, mContentPaint);
            }

        }
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
            measuredWidth = (int)(mDefWidth + 0.5f);
        }
        // 如果layout文件中设置layout_height 为 wrap_content, 那么就使用默认的高度
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            measuredHeight = (int)(mDefHeight + 0.5f);
        }

        setMeasuredDimension(measuredWidth, measuredHeight);
//        int px = DensityUtils.dip2px(getContext(), 120);
//        setMeasuredDimension(px, px);
    }

    private interface OnCreatedCallback {
        void onCreated(Context context);
    }
}