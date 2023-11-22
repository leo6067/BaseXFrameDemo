package com.xy.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomScrollViewPager extends ViewPager {

    private boolean scrollable = false;

    public boolean isScrollable() {
        return scrollable;
    }

    public CustomScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!scrollable) {
            return false;
        } else {
            return super.onTouchEvent(event);
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!scrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(event);
        }
    }
}
