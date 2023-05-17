package com.xy.xframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.xy.xframework.R;


public class WarnDialog extends Dialog {

    public WarnDialog(@NonNull Context context, View view) {
        super(context);
        initView(context,view);
    }

    public void initView(Context context,View childView) {
        LayoutInflater  mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.dialog_base, null);
        LinearLayout rootView = view.findViewById(R.id.linear_layout);
        rootView.removeAllViews();
        rootView.addView(childView);
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(view);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = display.getWidth() - 150; //设置宽度
        this.getWindow().setAttributes(lp);
    }
}
