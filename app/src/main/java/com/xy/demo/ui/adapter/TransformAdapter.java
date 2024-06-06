package com.xy.demo.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;
import com.xy.demo.model.ToolsModel;

public class TransformAdapter extends BaseQuickAdapter<ToolsModel, BaseViewHolder> {
    public TransformAdapter() {
        super(R.layout.item_transform);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ToolsModel bean) {
        ImageView itemIcon = baseViewHolder.getView(R.id.itemIcon);
        itemIcon.setBackgroundResource(bean.itemSrc);
        baseViewHolder.setText(R.id.itemName,bean.itemName);
    }
}
