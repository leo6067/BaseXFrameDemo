package com.xy.demo.ui.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.BuildConfig;
import com.xy.demo.R;
import com.xy.demo.model.ToolsModel;

public class SettingAdapter extends BaseQuickAdapter<ToolsModel, BaseViewHolder> {
    public SettingAdapter() {
        super(R.layout.item_setting);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ToolsModel bean) {
        ImageView itemIcon = viewHolder.getView(R.id.itemIcon);
        ImageView itemNext = viewHolder.getView(R.id.itemNext);
        itemIcon.setBackgroundResource(bean.itemSrc);
        TextView itemContent = viewHolder.getView(R.id.itemContent);
        viewHolder.setText(R.id.itemName,bean.itemName);
        if (getData().size() == getItemPosition(bean)+1){
            itemContent.setVisibility(View.VISIBLE);
            itemNext.setVisibility(View.GONE);
        }else {
            itemContent.setVisibility(View.GONE);
            itemNext.setVisibility(View.VISIBLE);
        }

        String versionName = BuildConfig.VERSION_NAME;
        int versionCode = BuildConfig.VERSION_CODE;

        itemContent.setText(versionName);

// 打印或使用版本号
        Log.d("Version", "Version Name: " + versionName + ", Version Code: " + versionCode);
    }
}
