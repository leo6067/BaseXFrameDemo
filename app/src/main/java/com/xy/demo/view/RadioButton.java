package com.xy.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.xy.demo.R;


public class RadioButton extends androidx.appcompat.widget.AppCompatRadioButton {
    private final int DRAWABLE_LEFT = 0;
    private final int DRAWABLE_TOP = 1;
    private final int DRAWABLE_RIGHT = 2;
    private final int DRAWABLE_BOTTOM = 3;
    private int leftDrawableWidth;
    private int leftDrawableHeight;
    private int rightDrawableWidth;
    private int rightDrawableHeight;
    private int topDrawableWidth;
    private int topDrawableHeight;
    private int bottomDrawableWidth;
    private int bottomDrawableHeight;
    private int leftWidth, rightWidth;//左右图片宽度

    public RadioButton(Context context) {
        super(context, null);
    }

    public RadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadioButton, 0, 0);
        leftDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.RadioButton_rleftDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        leftDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.RadioButton_rleftDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        rightDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.RadioButton_rrightDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        rightDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.RadioButton_rrightDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        topDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.RadioButton_rtopDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        topDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.RadioButton_rtopDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        bottomDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.RadioButton_rbottomDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        bottomDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.RadioButton_rbottomDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources().getDisplayMetrics()));
        typedArray.recycle();
        drawable();
    }

    public RadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.attr.radioButtonStyle);

    }

    private void drawable() {
        Drawable[] drawables = getCompoundDrawables();
        for (int i = 0; i < drawables.length; i++) {
            setDrawableSize(drawables[i], i);
        }
        //放置图片
        setCompoundDrawables(drawables[DRAWABLE_LEFT], drawables[DRAWABLE_TOP], drawables[DRAWABLE_RIGHT], drawables[DRAWABLE_BOTTOM]);
    }

    //设置图片的高度和宽度
    private void setDrawableSize(Drawable drawable, int index) {
        if (drawable == null) {
            return;
        }
        //左上右下
        int width = 0, height = 0;

        switch (index) {

            case DRAWABLE_LEFT:

                width = leftDrawableWidth;
                height = leftDrawableHeight;

                break;

            case DRAWABLE_TOP:

                width = topDrawableWidth;
                height = topDrawableHeight;
                break;

            case DRAWABLE_RIGHT:

                width = rightDrawableWidth;
                height = rightDrawableHeight;

                break;

            case DRAWABLE_BOTTOM:

                width = bottomDrawableWidth;
                height = bottomDrawableHeight;

                break;


        }

        //如果没有设置图片的高度和宽度具使用默认的图片高度和宽度
        if (width < 0) {

            width = drawable.getIntrinsicWidth();

        }

        if (height < 0) {

            height = drawable.getIntrinsicHeight();
        }

        if (index == 0) {

            leftWidth = width;

        } else if (index == 2) {

            rightWidth = width;
        }

        drawable.setBounds(0, 0, width, height);


    }

}