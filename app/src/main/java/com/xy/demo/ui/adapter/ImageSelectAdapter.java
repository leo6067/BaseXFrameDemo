package com.xy.demo.ui.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;

public class ImageSelectAdapter extends BaseQuickAdapter<ParamModel, BaseViewHolder> {

    int selectNumber = 0;
    int selectAll = 0; //0:多选 1：全选 2：全不选
    public ImageSelectAdapter() {
        super(R.layout.item_img_select);
    }

    public void selectAll(int selectAllA) {
        this.selectAll = selectAllA;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ParamModel bean) {
        ImageView imageView = viewHolder.getView(R.id.imageView);
        ImageView itemSelectIV = viewHolder.getView(R.id.itemSelectIV);
        imageView.setImageBitmap(bean.bitmap);
        if (selectAll == 1) {
            itemSelectIV.setSelected(true);
        }
        if (selectAll == 2) {
            itemSelectIV.setSelected(false);
        }

        itemSelectIV.setBackgroundResource( bean.selectStatus  ? R.drawable.icon_file_select : R.drawable.icon_file_select_un);
    }
}
