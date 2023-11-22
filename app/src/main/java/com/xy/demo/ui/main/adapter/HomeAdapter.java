package com.xy.demo.ui.main.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;
import com.xy.demo.model.HomeModel;

/**
 * author: Leo
 * createDate: 2023/11/21 16:16
 */
public class HomeAdapter extends BaseQuickAdapter<HomeModel, BaseViewHolder> {


    public HomeAdapter() {
        super(R.layout.include_home_tab);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, HomeModel bean) {

    }
}
