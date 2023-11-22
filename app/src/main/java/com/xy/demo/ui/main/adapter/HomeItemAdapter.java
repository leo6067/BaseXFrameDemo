package com.xy.demo.ui.main.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;
import com.xy.demo.model.HomeItemModel;

/**
 * author: Leo
 * createDate: 2023/11/21 17:41
 */
public class HomeItemAdapter extends BaseMultiItemQuickAdapter<HomeItemModel, BaseViewHolder> {

    public HomeItemAdapter() {
        addItemType(1, R.layout.layout_tab);
        addItemType(1, R.layout.layout_tab);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, HomeItemModel homeItemModel) {

    }
}
