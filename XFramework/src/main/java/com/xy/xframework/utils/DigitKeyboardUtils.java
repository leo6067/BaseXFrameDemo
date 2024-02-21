package com.xy.xframework.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


//首先EditText所在的父布局或最外层的布局需要添加属性：
//android:clickable="true"
//android:focusableInTouchMode="true"

public class DigitKeyboardUtils {
    public void setHideKeyBoard(final Context context, EditText etView) {
        etView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboardViewCommon(context,view);
                }
            }
        });
    }

    public void hideKeyboardViewCommon(Context context,View view) {
        InputMethodManager manager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null)
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
