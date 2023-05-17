package com.xy.xframework.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xy.xframework.R;
import com.xy.xframework.base.XBaseApplication;


/**
 * Toast统一管理类
 */
public class ToastUtils {

    private static Toast toast;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (!TextUtils.isEmpty(message) && XBaseApplication.application != null ) {
            toast = new Toast(XBaseApplication.application);
            View view = LayoutInflater.from(XBaseApplication.application).inflate(R.layout.layout_toast, null);
            TextView tvContent = view.findViewById(R.id.toast_text);
            tvContent.setText(message);
            //根据自己需要对view设置text或其他样式
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 300);
            toast.show();
        }
    }

    /**
     * 短时间显示Toast
     * int 类型
     *
     * @param message
     */
    public static void showShort(int message) {
        if (XBaseApplication.application != null ) {
            toast = new Toast(XBaseApplication.application);
            View view = LayoutInflater.from(XBaseApplication.application).inflate(R.layout.layout_toast, null);
            TextView tvContent = view.findViewById(R.id.toast_text);
            tvContent.setText(message);
            //根据自己需要对view设置text或其他样式
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 300);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (XBaseApplication.application != null ) {
            toast = new Toast(XBaseApplication.application);
            View view = LayoutInflater.from(XBaseApplication.application).inflate(R.layout.layout_toast, null);
            TextView tvContent = view.findViewById(R.id.toast_text);
            tvContent.setText(message);
            //根据自己需要对view设置text或其他样式
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 300);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(int message) {
        if (XBaseApplication.application != null ) {
            toast = new Toast(XBaseApplication.application);
            View view = LayoutInflater.from(XBaseApplication.application).inflate(R.layout.layout_toast, null);
            TextView tvContent = view.findViewById(R.id.toast_text);
            tvContent.setText(message);
            //根据自己需要对view设置text或其他样式
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 300);
            toast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (XBaseApplication.application != null ) {
            toast = new Toast(XBaseApplication.application);
            View view = LayoutInflater.from(XBaseApplication.application).inflate(R.layout.layout_toast, null);
            TextView tvContent = view.findViewById(R.id.toast_text);
            tvContent.setText(message);
            //根据自己需要对view设置text或其他样式
            toast.setView(view);
            toast.setDuration(duration);
            toast.setGravity(Gravity.BOTTOM, 0, 300);
            toast.show();
        }
    }


}