package com.xy.xframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.xy.xframework.R;


public class BaseDialog extends Dialog {
    public static BaseDialog mBaseDialog;

    //位置   上方 下方 中间
    public static int seat = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

    //距离屏幕边沿  边距
    public static int margins = 150;


    public static void showBaseDialog(Context context, View view) {
        try {
            if (mBaseDialog != null) {
                mBaseDialog.cancel();
                mBaseDialog = null;
            }
            mBaseDialog = new BaseDialog(context, view);
            mBaseDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void dismissBaseDialog() {
        try {
            if (mBaseDialog != null) {
                mBaseDialog.cancel();
                mBaseDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public BaseDialog(@NonNull Context context, View view) {
        super(context);
        initView(context, view);
    }


    public void initView(Context context, View childView) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.dialog_base, null);
        LinearLayout rootView = view.findViewById(R.id.linear_layout);
        rootView.removeAllViews();
        rootView.addView(childView);
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(view);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();

        lp.width = display.getWidth() - margins; //设置宽度
        lp.gravity = seat;
        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


    public static void setOutTouch(Boolean outsizeTouch) {
        mBaseDialog.setCanceledOnTouchOutside(outsizeTouch);
    }

    public static void setSeat(int location) {
        seat = location;
    }

    public static void setMargins(int marginValue) {
        margins = marginValue;
    }


    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        return true;

    }
}
