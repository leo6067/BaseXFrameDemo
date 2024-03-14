package com.xy.xframework.utils;

public class ViewUtil {
    private static final int MIN_CLICK_DELAY_TIME = 300;
    private static long lastClickTime;

    public static boolean isFastClick() {//点击间隔时间判断
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= MIN_CLICK_DELAY_TIME) {//点击间隔时间
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

}
