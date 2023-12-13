package com.xy.demo.ui.main.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;
import com.xy.demo.model.MineModel;
import com.xy.demo.network.Globals;
import com.xy.xframework.utils.GlideUtil;

/**
 * author: Leo
 * createDate: 2023/12/6 11:10
 */
public class MineAdapter extends BaseQuickAdapter<MineModel.PanelList, BaseViewHolder> {
    public MineAdapter() {
        super(R.layout.item_mine);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, MineModel.PanelList panelList) {
        Globals.log("xxxxxxxxxmineModel baseViewHolder"+getItemPosition(panelList));
        GlideUtil.loadImage(panelList.getIcon(),baseViewHolder.getView(R.id.item_icon));
        baseViewHolder.setText(R.id.item_title,panelList.getTitle());
    }
}
