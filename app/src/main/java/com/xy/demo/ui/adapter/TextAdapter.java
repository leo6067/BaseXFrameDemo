package com.xy.demo.ui.adapter;



import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ruffian.library.widget.REditText;
import com.xy.demo.R;
import com.xy.demo.base.MyApplication;
import com.xy.demo.model.StringModel;

import java.lang.reflect.Method;

public class TextAdapter extends BaseQuickAdapter<StringModel, BaseViewHolder> {
    public TextAdapter() {
        super(R.layout.item_edit);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, StringModel bean) {
        REditText editTT = viewHolder.getView(R.id.editTT);

      editTT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 告诉父视图不拦截触摸事件
                v.getParent().requestDisallowInterceptTouchEvent(true);

                // 判断触摸事件类型
                    // 触摸时获取焦点
                    editTT.requestFocus();
                    // 显示软键盘
                    editTT.post(new Runnable() {
                        @Override
                        public void run() {
                            // 使用反射隐藏软键盘
                            try {
                                Method setShowSoftInputOnFocus = EditText.class.getMethod("setShowSoftInputOnFocus", boolean.class);
                                setShowSoftInputOnFocus.setAccessible(true);
                                setShowSoftInputOnFocus.invoke(editTT, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                return false;
            }
        });


        editTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(editTT, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });


        if (bean.editContent.equals(MyApplication.instance.getString(R.string.no_text_recognized_on_this_page))) {
            editTT.setHint(MyApplication.instance.getString(R.string.no_text_recognized_on_this_page));
        } else {
            editTT.setText(bean.editContent);
        }


        editTT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                bean.setEditContent(editable.toString());
            }
        });


    }
}
