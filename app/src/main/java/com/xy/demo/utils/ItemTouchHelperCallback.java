package com.xy.demo.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.xy.demo.model.ImgModel;

import java.util.Collections;
import java.util.List;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    List<ImgModel> dataList;

    public ItemTouchHelperCallback(List<ImgModel> datas) {
        dataList = datas;
    }

    // 设置支持的拖动和滑动的方向
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT; // 支持上下拖动
        int swipeFlags = 0; // 不支持滑动
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    // 在拖动过程中不断调用，用于刷新RecyclerView的显示
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = source.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        // 更新数据集中的位置
        Collections.swap(dataList, fromPosition, toPosition);
        // 更新RecyclerView的显示
        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

        //因为存在角标 全部更新 数字排序
//        recyclerView.getAdapter().notifyDataSetChanged();
        return true;
    }

    // 在滑动过程中调用，可以用于实现滑动删除等功能
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 不做任何操作
    }
}