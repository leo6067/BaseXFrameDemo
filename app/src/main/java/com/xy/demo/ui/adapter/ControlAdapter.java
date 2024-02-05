package com.xy.demo.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;

public class ControlAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ControlAdapter() {
        super(R.layout.item_control);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {

        baseViewHolder.setText(R.id.itemTitle,s);
    }
}
