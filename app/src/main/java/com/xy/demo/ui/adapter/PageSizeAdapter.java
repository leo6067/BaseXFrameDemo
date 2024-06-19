package com.xy.demo.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;
import com.xy.demo.model.PageSizeModel;

public class PageSizeAdapter extends BaseQuickAdapter<PageSizeModel, BaseViewHolder> {

    int checkPosition = 0;

    public PageSizeAdapter() {
        super(R.layout.item_page_size);
    }

    public void setCheck(int checkIndex){
        checkPosition= checkIndex;
        notifyDataSetChanged();
    }




    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, PageSizeModel bean) {

        LinearLayout itemLin = viewHolder.getView(R.id.itemLin);
        TextView itemName = viewHolder.getView(R.id.itemName);
        TextView itemContent = viewHolder.getView(R.id.itemContent);
        itemName.setText(bean.name);
        itemContent.setText(bean.content);
        if (checkPosition == getItemPosition(bean)){
            itemLin.setBackgroundResource(R.drawable.icon_check_bg_a);
            itemName.setEnabled(true);
            itemContent.setEnabled(true);
        }else {
            itemLin.setBackgroundResource(R.drawable.icon_check_bg_b);
            itemName.setEnabled(false);
            itemContent.setEnabled(false);
        }

    }
}
