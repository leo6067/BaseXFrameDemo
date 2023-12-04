package com.xy.demo.ui.main.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xy.demo.R;
import com.xy.demo.model.VideoStoreModel;

/**
 * author: Leo
 * createDate: 2023/11/21 16:16
 */
public class HomeAdapter extends BaseQuickAdapter<VideoStoreModel.LabelDTO, BaseViewHolder> {


    public HomeAdapter() {
        super(R.layout.include_home_tab);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, VideoStoreModel.LabelDTO bean) {

        baseViewHolder.setText(R.id.tab_tv, bean.getLabel());
        TextView moreTv = baseViewHolder.getView(R.id.more_tv);
        RecyclerView recyclerView = baseViewHolder.getView(R.id.recyclerView);

        setStyle(recyclerView,bean);
    }




    public void setStyle(RecyclerView recyclerView,VideoStoreModel.LabelDTO bean){




    }

}
