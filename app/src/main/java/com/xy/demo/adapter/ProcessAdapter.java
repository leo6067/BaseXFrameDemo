package com.xy.demo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;
import com.xy.demo.model.AppProcessInfo;
import com.xy.xframework.utils.PackageUtils;

public class ProcessAdapter extends BaseQuickAdapter<AppProcessInfo, BaseViewHolder> {
    public ProcessAdapter() {
        super(R.layout.item_process);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, AppProcessInfo bean) {

        ImageView itemIcon = baseViewHolder.getView(R.id.itemIcon);
        TextView itemTitle = baseViewHolder.getView(R.id.itemTitle);
        TextView stopTV = baseViewHolder.getView(R.id.stopTV);


        itemTitle.setText(bean.getAppName());

        Glide.with(itemIcon.getContext()).load(bean.getIcon()).into(itemIcon);

        stopTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageUtils.getInstance().appInfo(stopTV.getContext(), bean.getProcessName());
            }
        });

    }
}
